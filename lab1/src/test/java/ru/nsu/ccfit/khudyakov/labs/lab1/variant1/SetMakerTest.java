package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.TreeSet;

public class SetMakerTest extends Assert {
    private TreeSet<WordCounter> tree;
    private HashMap<String,Long> map;

    public SetMakerTest(){
        tree = new TreeSet<>();
        map = new HashMap<>();
    }

    @Before
    public void setUp() {
        map.put("sun",100L);
        map.put("home",10L);
        map.put("river", 1L);
        map.put("bird", 1L);
    }

    @Test
    public void testSetSize(){
        SetMaker setMaker = new SetMaker();
        tree = setMaker.makeSet(map);
        assertEquals(tree.size(),4);
    }

    @Test
    public void testSet(){
        SetMaker setMaker = new SetMaker();
        tree = setMaker.makeSet(map);
        assertEquals(tree.first(), new WordCounter("sun",100));
    }

}