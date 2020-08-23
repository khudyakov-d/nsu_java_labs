package ru.nsu.ccfit.khudyakov.lab5.client;

import ru.nsu.ccfit.khudyakov.lab5.xmlMessages.ErrorMessage;
import ru.nsu.ccfit.khudyakov.lab5.xmlMessages.Message;
import ru.nsu.ccfit.khudyakov.lab5.xmlMessages.ServerConfirmMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientEntryDialog extends JDialog {

    public ClientEntryDialog( String title, boolean modal, Client client) {
        super(client, title, modal);

        setBounds(600, 200, 600, 130);
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JLabel nameLabel = new JLabel("Enter your name");
        JTextField nameField = new JTextField();
        JButton loginButton = new JButton("Connect");
        JLabel infoLabel = new JLabel();

        nameLabel.setPreferredSize(new Dimension(120, 30));
        nameField.setPreferredSize(new Dimension(150, 30));
        loginButton.setPreferredSize(new Dimension(100, 30));
        infoLabel.setPreferredSize(new Dimension(150, 60));

        add(nameLabel);
        add(nameField);
        add(loginButton);
        add(infoLabel);

        loginButton.addActionListener(e -> {
            if (!nameField.getText().trim().isEmpty()) {

                client.setUserName(nameField.getText());
                client.setTitle(nameField.getText()+"'s chat");

                client.sendMessage(client.getConstructor().createLoginMessage(client.getUserName()));

                while (true) {
                    if (client.getInputData().hasNext()) {
                        Message message = client.getParser().parseCommand(client.getInputData().nextLine());

                        if (message.getType().equals("error")) {
                            ErrorMessage typedMessage = (ErrorMessage) message;
                            infoLabel.setText(typedMessage.getReason());
                            break;

                        } else if (message.getType().equals("connection")) {
                            ServerConfirmMessage typedMessage = (ServerConfirmMessage) message;
                            client.setSessionID(typedMessage.getSessionId());
                            dispose();
                        }

                        break;
                    }
                }
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                    client.sendMessage(client.getConstructor().createRefuseMessage());
                    System.exit(1);
            }
        });

        setVisible(true);
    }
}
