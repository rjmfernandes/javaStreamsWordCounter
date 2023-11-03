package com.confluent.wordcounter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class JavaWordCounter {


    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/main/resources/sample.txt");
        Stream<String> lines=Files.lines(path);
        try(lines) {
            Map<String, Integer> wordCount = lines
                    .flatMap(line -> Arrays.stream(
                                    line.toLowerCase().split("\\W+")
                            )
                    )
                    .filter(word -> !word.isEmpty())
                    .map(word -> new AbstractMap.SimpleEntry<>(word, 1))
                    .collect(toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue, Integer::sum));
            wordCount.forEach((k, v) -> System.out.printf("%s ==>> %d%n", k, v));
        }
    }
}
