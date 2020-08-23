package ru.nsu.ccfit.khudyakov.lab5.xmlMessages;

public class ClientLogoutMessage extends Message {
    private String sessionId;

    public ClientLogoutMessage(String sessionId) {
        super("logout");
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
