package ru.nsu.ccfit.khudyakov.lab5.xmlMessages;

import java.util.LinkedList;

public class ServerListMessage extends Message {
    private LinkedList<String> users;

    public ServerListMessage(LinkedList<String> users) {
        super("list");
        this.users = users;
    }

    public LinkedList<String> getUsers() {
        return users;
    }
}
