package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import java.io.IOException;
import java.io.Writer;
import java.util.TreeSet;
import java.util.logging.Logger;

public class FileWriter {
    private final static Logger LOGGER = Logger.getLogger(FileReader.class.getName());
    private Writer writer;

    FileWriter(Writer writer) {
        this.writer = writer;
    }

    void printTree(TreeSet<WordCounter> wordCounters){
        try {
            for(WordCounter wordCounter : wordCounters){
                writer.write(wordCounter.getWord() + "," + wordCounter.getCount() + ","
                        +Math.round((double)wordCounter.getCount()/WordCounter.getCountAll()*100)+"%"+"\n" );
            }
        }
        catch (IOException e){
            LOGGER.severe("Writing file error");
        }
    }
}
