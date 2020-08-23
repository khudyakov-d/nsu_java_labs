package ru.nsu.ccfit.khudyakov.labs.lab2;

import ru.nsu.ccfit.khudyakov.labs.lab2.commands.Command;
import ru.nsu.ccfit.khudyakov.labs.lab2.source.Injection;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Logger;

public class Factory {
    private static Factory factory;
    private Stack<Double> stack;
    private Map<String, Double> map;
    private Class factoryClass;
    private static final Properties p;
    private final static Logger LOGGER = Logger.getLogger(Factory.class.getName());

    static {
        try {
            InputStream resourceAsStream = Factory.class.getClassLoader().getResourceAsStream("config.properties");
            p = new Properties();
            p.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private Factory() {
        this.stack = new Stack<>();
        this.map = new HashMap<>();
    }

    public  static Factory getInstance(){
        if(factory == null){
            factory = new Factory();
        }
        return factory;
    }

    private void fillCommand(Command command,String[] arguments) {
        Injection injection;

        Field[] fields = factoryClass.getDeclaredFields();
        try {
            for (Field field : fields) {
                injection = field.getDeclaredAnnotation(Injection.class);
                field.setAccessible(true);
                switch (injection.arg()) {
                    case STACK:
                        field.set(command, stack);
                        break;
                    case ARGUMENTS:
                        field.set(command, arguments);
                        break;
                    case VARIABLES:
                        field.set(command, map);
                        break;
                }
            }
        } catch (IllegalAccessException ex) {
            LOGGER.severe("No access to filed");
            command = null;
        }
    }

    public Command makeCommand(String[] arguments) {
        Command command = null;
        String className;
        if (arguments.length >3){
            LOGGER.severe("Invalid arguments");
            return null;
        }

        try {
            className = p.getProperty(arguments[0]);

            if (className == null) {
                LOGGER.warning("Command not found");
            } else {
                factoryClass = Class.forName(className);
                if (factoryClass.newInstance() instanceof Command){
                    command = (Command) factoryClass.newInstance();
                    fillCommand(command,arguments);
                } else {
                    LOGGER.severe("Object can't be cast to command");
                }
            }
        } catch (ClassNotFoundException ex) {
            LOGGER.severe("Class not found");
        } catch (IllegalAccessException ex) {
            LOGGER.severe("No access to class");
        } catch (InstantiationException ex) {
            LOGGER.severe("Object can't be instantiated");
        }

        return command;
    }

    public Stack<Double> getStack() {
        return stack;
    }
}
