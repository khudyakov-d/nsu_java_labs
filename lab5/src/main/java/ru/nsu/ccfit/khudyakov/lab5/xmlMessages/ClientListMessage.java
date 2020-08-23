package ru.nsu.ccfit.khudyakov.lab5.xmlMessages;

public class ClientListMessage extends Message {
    private String sessionId;

    public ClientListMessage(String sesionId) {
        super("getlist");
        this.sessionId = sesionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
