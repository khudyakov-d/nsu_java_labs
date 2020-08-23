package ru.nsu.ccfit.khudyakov.lab5.xmlMessages;

public class ErrorMessage extends Message {
    private String reason;

    public ErrorMessage(String reason) {
        super("error");
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
