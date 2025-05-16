package org.example;

public record Transfer(
        String from,
        String to,
        int amount
) {
    @Override
    public String toString() {
        return from + " " + amount + " -> " + to;
    }
}
