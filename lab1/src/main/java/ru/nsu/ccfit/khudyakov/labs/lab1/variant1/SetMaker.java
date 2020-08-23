package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

public class SetMaker {
    private TreeSet<WordCounter> wordCounters;

    public SetMaker(){
        wordCounters = new TreeSet<>();
    }

    public TreeSet<WordCounter> makeSet(HashMap<String,Long> map){
        for (Map.Entry<String,Long> entry : map.entrySet()) {
                wordCounters.add(new WordCounter(entry.getKey(),entry.getValue()));
                WordCounter.increaseCount(entry.getValue());
        }

        return wordCounters;
    }
}


