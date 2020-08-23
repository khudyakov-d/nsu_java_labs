package ru.nsu.ccfit.khudyakov.lab5.xmlMessages;

public class Message {
    private final String type;

    public Message(String type) {
        this.type = type;
    }
    public String getType(){
        return type;
    }
}
