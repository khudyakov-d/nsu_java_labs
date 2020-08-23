package ru.nsu.ccfit.khudyakov.labs.lab2;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FactoryTest extends Assert {
    Factory factory;
    @Before
    public void setUp() throws Exception {
        factory = Factory.getInstance();
    }

    @Test
    public void testEquation(){

        factory.makeCommand("PUSH 2".split(" ")).execute();
        factory.makeCommand("PUSH 3".split(" ")).execute();
        factory.makeCommand("MUL".split(" ")).execute();

        factory.makeCommand("PUSH -14".split(" ")).execute();
        factory.makeCommand("PUSH -1".split(" ")).execute();
        factory.makeCommand("MUL".split(" ")).execute();

        factory.makeCommand("PUSH 3".split(" ")).execute();
        factory.makeCommand("PUSH -5".split(" ")).execute();
        factory.makeCommand("PUSH -4".split(" ")).execute();
        factory.makeCommand("MUL".split(" ")).execute();
        factory.makeCommand("MUL".split(" ")).execute();
        factory.makeCommand("PUSH -14".split(" ")).execute();
        factory.makeCommand("PUSH -14".split(" ")).execute();
        factory.makeCommand("MUL".split(" ")).execute();
        factory.makeCommand("PLUS".split(" ")).execute();
        factory.makeCommand("SQRT".split(" ")).execute();

        factory.makeCommand("PLUS".split(" ")).execute();

        factory.makeCommand("DIVIDE".split(" ")).execute();

        assertEquals(5, factory.getStack().peek(), 0.01);

    }
}