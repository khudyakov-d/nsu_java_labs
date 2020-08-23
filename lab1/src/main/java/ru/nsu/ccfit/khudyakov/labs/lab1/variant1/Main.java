package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import java.io.*;
import java.util.HashMap;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws IOException {
        try (FileInputStream file = new FileInputStream(args[0]);
             InputStreamReader reader = new InputStreamReader(file);
             FileOutputStream file1 = new FileOutputStream(args[1]);
             OutputStreamWriter writer = new OutputStreamWriter(file1)) {

            FileReader fileReader = new FileReader(reader);
            HashMap<String, Long> map = fileReader.readFile();
            SetMaker setMaker = new SetMaker();
            TreeSet<WordCounter> treeSet = setMaker.makeSet(map);

            FileWriter fileWriter = new FileWriter(writer);
            fileWriter.printTree(treeSet);
        }
    }
}
