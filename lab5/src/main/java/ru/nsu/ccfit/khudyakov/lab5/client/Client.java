package ru.nsu.ccfit.khudyakov.lab5.client;

import ru.nsu.ccfit.khudyakov.lab5.xmlHandlers.XMLConstructor;
import ru.nsu.ccfit.khudyakov.lab5.xmlHandlers.XMLParser;
import ru.nsu.ccfit.khudyakov.lab5.xmlMessages.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class Client extends JFrame {
    private XMLParser parser = new XMLParser();
    private XMLConstructor constructor = new XMLConstructor();

    private LinkedList<String> usersList;
    private static final String hostName = "localhost";
    private static final int hostPort = 3000;
    private String sessionID;
    private String userName;

    private Socket clientSocket;
    private Scanner inputData;
    private PrintWriter outputData;
    private Thread messageHandler;

    public Client() {

        try {
            clientSocket = new Socket(hostName, hostPort);
            inputData = new Scanner(clientSocket.getInputStream());
            outputData = new PrintWriter(clientSocket.getOutputStream());
            usersList = new LinkedList<>();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        setBounds(400, 200, 800, 500);

        setLayout(new BorderLayout());

        /*chat setting*/
        JTextArea chat = new JTextArea();
        chat.setLineWrap(true);
        chat.setEditable(false);

        JScrollPane chatScrollPane = new JScrollPane(chat);
        chatScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        chatScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setPreferredSize(new Dimension(600, 400));

        add(chatScrollPane, BorderLayout.CENTER);

        /*users setting*/
        JTextArea users = new JTextArea();
        JScrollPane usersScrollPane = new JScrollPane(users);
        users.setEditable(false);

        usersScrollPane.setPreferredSize(new Dimension(200, 400));
        usersScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        usersScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        add(usersScrollPane, BorderLayout.EAST);

        /*send area setting*/
        Container sender = new Container();
        sender.setLayout(new BorderLayout());

        /*text area*/
        JTextArea textMessage = new JTextArea();
        JScrollPane textMessageScrollPane = new JScrollPane(textMessage);

        textMessageScrollPane.setPreferredSize(new Dimension(600, 50));
        textMessageScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        textMessageScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        sender.add(textMessageScrollPane, BorderLayout.CENTER);

        /*send button*/
        JButton sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(200, 50));
        sender.add(sendButton, BorderLayout.EAST);

        add(sender, BorderLayout.SOUTH);

        ClientEntryDialog entryDialog = new ClientEntryDialog("Login", true, this);

        usersList = getUsersList();
        refreshUserList(users);

        sendButton.addActionListener(e -> {
            if (!textMessage.getText().trim().isEmpty()) {
                String message = constructor.createClientMessage(textMessage.getText(), sessionID);
                sendMessage(message);
                textMessage.selectAll();
                textMessage.replaceSelection("");
            }
        });


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    messageHandler.interrupt();

                    sendMessage(constructor.createUserLogoutMessage(sessionID));
                    inputData.close();
                    outputData.close();

                    System.exit(1);
            }
        });

        startWorking(chat, users);
        setVisible(true);
    }

    private void startWorking(JTextArea chat, JTextArea users){
        messageHandler = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (inputData.hasNext()) {
                    Message message = parser.parseCommand(inputData.nextLine());
                    System.out.println(message.getType());

                    if (message.getType().equals("server_message")) {
                        ServerMessage typedMessage = (ServerMessage) message;
                        chat.append(typedMessage.getName() + ":" + "\n" + typedMessage.getContent());
                        chat.append("\n");
                    }

                    if (message.getType().equals("userlogin")) {
                        ServerNewUserMessage typedMessage = (ServerNewUserMessage) message;
                        usersList.add(typedMessage.getUserName());
                        refreshUserList(users);
                    }

                    if (message.getType().equals("userlogout")) {
                        ServerUserLogoutMessage typedMessage = (ServerUserLogoutMessage) message;
                        usersList.remove(typedMessage.getUserName());
                        refreshUserList(users);
                    }
                }
            }
        });

        messageHandler.start();
    }

    synchronized private LinkedList<String> getUsersList() {
        sendMessage(constructor.createGetUsersMessage(sessionID));

        while (true) {
            if (inputData.hasNext()) {
                Message message = parser.parseCommand(inputData.nextLine());

                if (message.getType().equals("list")) {
                    ServerListMessage typedMessage = (ServerListMessage) message;
                    return typedMessage.getUsers();
                }
            }
        }
    }

    synchronized private void refreshUserList(JTextArea users){
        users.selectAll();
        users.replaceSelection("");

        users.append("Users online: ");
        users.append("\n");

        for (String user :
                usersList) {
            users.append(user);
            users.append("\n");
        }
    }

    public void sendMessage(String msg) {
        try {
            outputData.println(msg);
            outputData.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public XMLParser getParser() {
        return parser;
    }

    public XMLConstructor getConstructor() {
        return constructor;
    }

    public String getUserName() {
        return userName;
    }

    public Scanner getInputData() {
        return inputData;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}


