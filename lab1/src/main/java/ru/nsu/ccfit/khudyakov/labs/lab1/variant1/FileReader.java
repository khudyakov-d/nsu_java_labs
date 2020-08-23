package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;


import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.logging.Logger;

public class FileReader {
    private final static Logger LOGGER = Logger.getLogger(FileReader.class.getName());

    private HashMap<String, Long> map;
    private Reader reader;

    public FileReader(Reader reader) {
        this.reader = reader;
        map = new HashMap<>();
    }

    private void addMap(String string){
        if (string.length() > 0) {
            if (map.containsKey(string)) {
                map.put(string, map.get(string) + 1 );
            }
            else {
                map.put(string, (long) 1);
            }
        }
        //map.merge()
    }

    public HashMap<String,Long> readFile() {
        int symbol = 0;
        StringBuilder string = new StringBuilder();

        try {
            while (true) {
                symbol = reader.read();

                if (symbol == -1) {
                    if (string.length() > 0) {
                        String res = string.toString();
                        addMap(res);
                    }
                    break;
                }

                if (Character.isLetter((char) symbol)) {
                    string.append((char) symbol);
                } else {
                    String res = string.toString();
                    addMap(res);
                    if (string.length()>0)
                        string = new StringBuilder();
                }
            }
        }
        catch (IOException e) {
            LOGGER.severe("Reading file error");
        }

        return map;
    }
}

