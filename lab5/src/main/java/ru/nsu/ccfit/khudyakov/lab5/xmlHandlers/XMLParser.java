package ru.nsu.ccfit.khudyakov.lab5.xmlHandlers;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.khudyakov.lab5.xmlMessages.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

public class XMLParser {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public Message parseCommand(String xml) {
        Message message = null;

        try {
            Document xmlInputMessage = factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            String tag = xmlInputMessage.getDocumentElement().getTagName();

            if (tag.equals("message")) {

                String type = xmlInputMessage.getDocumentElement().getFirstChild().getTextContent();
                NodeList nodeList;
                String content;

                switch (type) {

                    //Standard server error answer
                    case "error":
                        nodeList = xmlInputMessage.getElementsByTagName("reason");
                        message = new ErrorMessage(nodeList.item(0).getTextContent());
                        break;

                    //Standard server success answer
                    case "success":
                        message = new SuccessMessage();
                        break;

                    //Registration
                    case "login":
                        nodeList = xmlInputMessage.getElementsByTagName("name");
                        message = new ClientLoginMessage(nodeList.item(0).getTextContent());
                        break;

                    case "connection":
                        nodeList = xmlInputMessage.getElementsByTagName("session");
                        message = new ServerConfirmMessage(nodeList.item(0).getTextContent());
                        break;

                    //Chat user list request
                    case "getlist":
                        nodeList = xmlInputMessage.getElementsByTagName("session");
                        message = new ClientListMessage(nodeList.item(0).getTextContent());
                        break;

                    case "list":
                        LinkedList<String> users = new LinkedList<>();
                        nodeList = xmlInputMessage.getElementsByTagName("user");

                        for (int i = 0; i < nodeList.getLength(); i++) {
                            users.add(nodeList.item(i).getTextContent());
                        }
                        message = new ServerListMessage(users);
                        break;

                    //Disconnection
                    case "logout":
                        nodeList = xmlInputMessage.getElementsByTagName("session");
                        message = new ClientLogoutMessage(nodeList.item(0).getTextContent());
                        break;

                    //Client message
                    case "client_message":
                        nodeList = xmlInputMessage.getElementsByTagName("content");
                        content = nodeList.item(0).getTextContent().replaceAll("\\\\n", "\\\n");
                        nodeList = xmlInputMessage.getElementsByTagName("session");
                        message = new ClientMessage(content, nodeList.item(0).getTextContent());
                        break;

                    //Server message
                    case "server_message":
                        nodeList = xmlInputMessage.getElementsByTagName("content");
                        content = nodeList.item(0).getTextContent().replaceAll("\\\\n", "\\\n");
                        nodeList = xmlInputMessage.getElementsByTagName("name");
                        message = new ServerMessage(content, nodeList.item(0).getTextContent());
                        break;

                    //New client
                    case "userlogin":
                        nodeList = xmlInputMessage.getElementsByTagName("name");
                        message = new ServerNewUserMessage(nodeList.item(0).getTextContent());
                        break;

                    //Client exit
                    case "userlogout":
                        nodeList = xmlInputMessage.getElementsByTagName("name");
                        message = new ServerUserLogoutMessage(nodeList.item(0).getTextContent());
                        break;

                    //Client refuse
                    case "refuse":
                        nodeList = xmlInputMessage.getElementsByTagName("refuse");
                        message = new ClientRefuseMessage();
                        break;
                }
            }
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }
}
