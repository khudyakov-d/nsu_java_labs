package ru.nsu.ccfit.khudyakov.labs.lab2;

import ru.nsu.ccfit.khudyakov.labs.lab2.commands.Command;

import java.io.*;
import java.util.logging.Logger;

public class Main {
    private final static Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        Factory factory = Factory.getInstance();
        String arguments;
        Command command;


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                try {

                    if ((arguments = reader.readLine()) == null) {
                        break;
                    } else {
                        command = factory.makeCommand(arguments.split(" "));
                        if (command!= null)
                            command.execute();
                    }
                } catch (IOException ex) {
                    LOGGER.severe( "Reading error");
                }
            }
        }
        catch (IOException ex){
            LOGGER.severe("File can't be open");
        }
    }

}

