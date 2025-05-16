package org.example;

import java.util.HashMap;

public record ParsedData(
        HashMap<String, Integer> accounts,
        int threshold
) {
}
