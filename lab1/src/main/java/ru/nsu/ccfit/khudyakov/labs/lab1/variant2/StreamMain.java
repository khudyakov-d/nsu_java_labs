package ru.nsu.ccfit.khudyakov.labs.lab1.variant2;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamMain {
    private final static Logger LOGGER = Logger.getLogger(StreamMain.class.getName());

    public static void main(String[] args) throws IOException {
        try (FileInputStream fileInputStream = new FileInputStream(args[0]);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
             FileOutputStream file1 = new FileOutputStream(args[1]);
             OutputStreamWriter writer = new OutputStreamWriter(file1)) {

            long[] counter = new long[]{0};
            bufferedReader.lines()
                    .flatMap((s) -> Stream.of(s.split("[^\\pL]")))
                    .filter((s) -> s.length() > 0)
                    .peek(w -> counter[0]++)
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet()
                    .stream()
                    .sorted(Comparator
                            .comparing(Map.Entry<String, Long>::getValue)
                            .reversed())
                    .forEach(w -> {
                                try {
                                    writer.write(w.getKey() + "," + w.getValue() + ","
                                            + Math.round((double) w.getValue()/ counter[0] * 100) + "%" + "\n");
                                } catch (IOException e) {
                                    LOGGER.severe("Writing file error");
                                }
                            }
                    );
        }
    }
}
