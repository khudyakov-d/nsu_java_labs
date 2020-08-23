package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import java.util.Objects;

public class WordCounter implements Comparable<WordCounter> {
    private String word;
    private long count;
    private static long countAll;

    public WordCounter(String word, long count){
        this.word = word;
        this.count = count;
    }

    public int compareTo(WordCounter o) {
        if (this.count >= o.count) return -1;
        else return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof WordCounter)) return false;

        WordCounter wordCounter = (WordCounter)o;
        return (this.word.equals(wordCounter.getWord()));
    }

    @Override
    public int hashCode() {
        long h = 37;
        char[] charArray = word.toCharArray();

        for (int i = 0; i < word.length(); i++) {
            h = (h * 54059) ^ (charArray[i] * 76963);
        }

        return (int)(h % 86969);
    }

    public long getCount() {
        return count;
    }

    public String getWord() {
        return word;
    }

    public static void increaseCount(long value){
           countAll += value;
    }

    public static long getCountAll() {
        return countAll;
    }
}
