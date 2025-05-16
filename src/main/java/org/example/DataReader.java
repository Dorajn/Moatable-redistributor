package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class DataReader {

    public static ParsedData readThresholdAndAccounts(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        int threshold = Integer.parseInt(lines.getFirst().split(":")[1].trim());
        HashMap<String, Integer> accounts = new HashMap<>();

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(":");
            accounts.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }

        return new ParsedData(accounts, threshold);
    }
}
