package ru.nsu.ccfit.khudyakov.lab5.server;

import ru.nsu.ccfit.khudyakov.lab5.xmlHandlers.XMLConstructor;
import ru.nsu.ccfit.khudyakov.lab5.xmlHandlers.XMLParser;
import ru.nsu.ccfit.khudyakov.lab5.xmlMessages.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

public class ClientHandler implements Runnable {

    private XMLParser parser = new XMLParser();
    private XMLConstructor constructor = new XMLConstructor();

    private static LinkedList<String> names = new LinkedList<>();
    private final String uniqueSessionID = String.valueOf(new Random().nextInt());
    private String curName;

    private Scanner inputData;
    private PrintWriter outputData;
    private Server server;
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket, Server server) {
        try {
            this.clientSocket = clientSocket;
            this.server = server;
            inputData = new Scanner(clientSocket.getInputStream());
            outputData = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {

            setConnection();

            while (!Thread.currentThread().isInterrupted()) {
                if (inputData.hasNext()) {

                    Message message = parser.parseCommand(inputData.nextLine());

                    System.out.println(message.getType());

                    if (message.getType().equals("client_message")) {
                        ClientMessage typedMessage = (ClientMessage) message;

                        if (uniqueSessionID.equals(typedMessage.getSessionId())) {
                            server.sendMessageToAllClients(constructor.createServerMessage(typedMessage.getContent(), curName));
                        }
                    }

                    if (message.getType().equals("logout")) {
                        ClientLogoutMessage typedMessage = (ClientLogoutMessage) message;
                        if (uniqueSessionID.equals(typedMessage.getSessionId())) {

                            synchronized (names){
                                names.remove(curName);
                            }

                            server.removeClientHandler(this);

                            Thread.currentThread().interrupt();

                            server.sendMessageToAllClients(constructor.createUserExitMessage(curName));

                            inputData.close();
                            outputData.close();
                            clientSocket.close();
                        }
                    }

                    if (message.getType().equals("getlist")) {
                        ClientListMessage typedMessage = (ClientListMessage) message;
                        if (uniqueSessionID.equals(typedMessage.getSessionId())) {
                            sendMessage(constructor.createUsersListMessage(names));
                        }
                    }
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void setConnection() throws IOException {
        while (!Thread.currentThread().isInterrupted()) {
            if (inputData.hasNext()) {
                Message message = parser.parseCommand(inputData.nextLine());

                System.out.println(message.getType());

                if (message.getType().equals("login")) {
                    ClientLoginMessage typedMessage = (ClientLoginMessage) message;
                    curName = typedMessage.getUserName();

                    if (names.contains(curName)) {
                        System.out.println(curName);
                        sendMessage(constructor.createErrorMessage("User already exists"));
                    } else {

                        synchronized (names) {
                            names.add(curName);
                        }

                        sendMessage(constructor.createConfirmMessage(uniqueSessionID));
                        server.sendMessageToAllClients(constructor.createNewUserMessage(curName));
                        break;
                    }
                } else if (message.getType().equals("refuse")) {

                    inputData.close();
                    outputData.close();
                    clientSocket.close();

                    server.getClientHandlers().remove(this);
                    Thread.currentThread().interrupt();
                } else {
                    sendMessage(constructor.createErrorMessage("Please repeat again"));
                }
            }
        }
    }

    synchronized public void sendMessage(String msg) {
        try {
            outputData.println(msg);
            outputData.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
