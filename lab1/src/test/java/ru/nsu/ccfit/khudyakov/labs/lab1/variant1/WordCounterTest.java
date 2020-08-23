package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import org.junit.Before;
import org.junit.Test;
import ru.nsu.ccfit.khudyakov.labs.lab1.variant1.WordCounter;

import static org.junit.Assert.*;

public class WordCounterTest {
    private WordCounter[] wordCounters;

    public WordCounterTest(){
        wordCounters = new WordCounter[3];
    }

    @Before
    public void setUp(){
        wordCounters[0] = new WordCounter("home",10);
        wordCounters[1] = new WordCounter("home",10);
        wordCounters[2] = new WordCounter("sun",20);
    }

    @Test
    public void equalsTest() {
        assertTrue(wordCounters[0].equals(wordCounters[1]));
    }

    @Test
    public void hashcodeTest() {
        assertEquals(wordCounters[0].hashCode(),wordCounters[1].hashCode());
    }

    @Test
    public void compareTest() {
        assertEquals(wordCounters[0].compareTo(wordCounters[1]),-1 );
    }

    @Test
    public void compareTest2() {
        assertEquals(wordCounters[0].compareTo(wordCounters[2]),1 );
    }


}