package ru.nsu.ccfit.khudyakov.lab5.xmlMessages;

public class ServerUserLogoutMessage extends Message {
    private String userName;

    public ServerUserLogoutMessage(String userName) {
        super("userlogout");
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
