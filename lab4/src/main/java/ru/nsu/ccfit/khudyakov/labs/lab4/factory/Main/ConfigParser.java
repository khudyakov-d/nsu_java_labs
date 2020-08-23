package ru.nsu.ccfit.khudyakov.labs.lab4.factory.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigParser {
    private static final Properties p;
    private final static Logger LOGGER = Logger.getLogger(ConfigParser.class.getName());


    private int bodyStorageSize;
    private int engineStorageSize;
    private int accessoryStorageSize;
    private int carStorageSize;

    private int bodySupplierMinDelay;
    private int engineSupplierMinDelay;
    private int accessorySupplierMinDelay;
    private int dealerMinDelay;

    private int accessorySuppliers;
    private int workers;
    private int dealers;

    private boolean logFile;

    static {
        try {
            InputStream resourceAsStream = ConfigParser.class.getClassLoader().getResourceAsStream("config.properties");
            p = new Properties();
            p.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    public ConfigParser() {
        String value = null;

        value = p.getProperty("BodyStorageSize");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            bodyStorageSize = Integer.valueOf(value);
        }

        value = p.getProperty("EngineStorageSize");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            engineStorageSize = Integer.valueOf(value);
        }

        value = p.getProperty("AccessoryStorageSize");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            accessoryStorageSize = Integer.valueOf(value);
        }

        value = p.getProperty("CarStorageSize");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            carStorageSize = Integer.valueOf(value);
        }

        value = p.getProperty("BodySupplierMinDelay");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            bodySupplierMinDelay = Integer.valueOf(value);
        }


        value = p.getProperty("EngineSupplierMinDelay");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            engineSupplierMinDelay = Integer.valueOf(value);
        }

        value = p.getProperty("AccessorySupplierMinDelay");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            accessorySupplierMinDelay = Integer.valueOf(value);
        }

        value = p.getProperty("DealerMinDelay");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            dealerMinDelay = Integer.valueOf(value);
        }

        value = p.getProperty("AccessorySuppliers");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            accessorySuppliers = Integer.valueOf(value);
        }

        value = p.getProperty("Workers");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            workers = Integer.valueOf(value);
        }

        value = p.getProperty("Dealers");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            dealers = Integer.valueOf(value);
        }

        value = p.getProperty("LogFile");
        if (value == null) {
            LOGGER.severe("Couldn't read value from properties file");
        } else {
            logFile = Boolean.valueOf(value);
        }
    }

    public int getBodyStorageSize() {
        return bodyStorageSize;
    }

    public int getEngineStorageSize() {
        return engineStorageSize;
    }

    public int getAccessoryStorageSize() {
        return accessoryStorageSize;
    }

    public int getCarStorageSize() {
        return carStorageSize;
    }

    public int getBodySupplierMinDelay() {
        return bodySupplierMinDelay;
    }

    public int getEngineSupplierMinDelay() {
        return engineSupplierMinDelay;
    }

    public int getAccessorySupplierMinDelay() {
        return accessorySupplierMinDelay;
    }

    public int getDealerMinDelay() {
        return dealerMinDelay;
    }

    public int getAccessorySuppliers() {
        return accessorySuppliers;
    }

    public int getWorkers() {
        return workers;
    }

    public int getDealers() {
        return dealers;
    }

    public boolean isLogFile() {
        return logFile;
    }
}


