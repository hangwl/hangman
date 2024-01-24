package com.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class FileWordRetriever implements WordRetriever {

    private String filePath;

    public FileWordRetriever(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getRandomWord() {
        try {
            List<String> words = Files.readAllLines(Paths.get(filePath));
            if (words.isEmpty()) {
                return null;
            }
            Random random = new Random();
            return words.get(random.nextInt(words.size()));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
