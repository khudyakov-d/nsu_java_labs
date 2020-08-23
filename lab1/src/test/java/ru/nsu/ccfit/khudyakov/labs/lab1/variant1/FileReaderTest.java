package ru.nsu.ccfit.khudyakov.labs.lab1.variant1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class FileReaderTest extends Assert {
    private static FileReader fileReader;
    private static HashMap<String, Long> map;

    @BeforeClass
    public static void setFileReader() throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream("test.txt");
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream)) {
            fileReader = new FileReader(inputStreamReader);
            map = fileReader.readFile();
        }
    }

    @Test
    public void testKey1() {
        assertTrue(map.containsKey("sun"));
    }

    @Test
    public void testKey2() {
        assertFalse(map.containsKey("river"));
    }

    @Test
    public void testSize() {
        assertEquals(map.size(),8);
    }
}