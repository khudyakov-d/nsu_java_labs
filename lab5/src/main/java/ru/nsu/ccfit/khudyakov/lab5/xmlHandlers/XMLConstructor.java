package ru.nsu.ccfit.khudyakov.lab5.xmlHandlers;

import java.util.LinkedList;

public class XMLConstructor {

    public XMLConstructor() {
    }

    private String screenMessageContent(String textMessage) {
        return textMessage.replaceAll("\\n", "\\\\n");
    }

    public String createLoginMessage(String userName) {
        return "<message>" + "<type>login</type>" + "<name>" + userName + "</name>" + "</message>";
    }

    public String createRefuseMessage() {
        return "<message>" + "<type>refuse</type>" + "<session>" + "</session>" + "</message>";
    }

    public String createClientMessage(String textMessage, String sessionID) {
        return "<message>" + "<type>client_message</type>" + "<content>" + screenMessageContent(textMessage) +
                "</content>" + "<session>" + sessionID + "</session>" + "</message>";
    }

    public String createServerMessage(String textMessage, String name) {
        return "<message>" + "<type>server_message</type>" + "<content>" + screenMessageContent(textMessage) +
                "</content>" + "<name>" + name + "</name>" + "</message>";
    }

    public String createUserLogoutMessage(String sessionId) {
        return "<message>" + "<type>logout</type>" + "<session>" + sessionId + "</session>" + "</message>";
    }

    public String createErrorMessage(String refuseReason) {
        return "<message>" + "<type>error</type>" + "<reason>" + refuseReason + "</reason>" + "</message>";
    }

    public String createConfirmMessage(String sessionId) {
        return "<message>" + "<type>connection</type>" + "<session>" + sessionId + "</session>" + "</message>";
    }

    public String createGetUsersMessage(String sessionId) {
        return "<message>" + "<type>getlist</type>" + "<session>" + sessionId + "</session>" + "</message>";
    }

    public String createUsersListMessage(LinkedList<String> users) {
        StringBuilder message = new StringBuilder("<message>" + "<type>list</type>" + "<listusers>");
        for (String user:
             users) {
            message.append("<user>").append(user).append("</user>");
        }

        message.append("</listusers></message>");

        return message.toString();
    }

    public String createNewUserMessage(String name) {
        return "<message>" + "<type>userlogin</type>" + "<name>" + name + "</name>" + "</message>";
    }

    public String createUserExitMessage(String name) {
        return "<message>" + "<type>userlogout</type>" + "<name>" + name + "</name>" + "</message>";
    }

}
