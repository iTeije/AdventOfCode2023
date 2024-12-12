package com.cachedcloud.aoc23.day3;

import com.cachedcloud.aoc.common.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DayThreePartTwo {

    private final static Map<Integer, Map<Integer, Value>> table = new HashMap<>();
    private final static List<OffsetPair> pairs = List.of(
            new OffsetPair(-1, -1),
            new OffsetPair(0, -1),
            new OffsetPair(1, -1),
            new OffsetPair(-1, 0),
            new OffsetPair(1, 0),
            new OffsetPair(-1, 1),
            new OffsetPair(0, 1),
            new OffsetPair(1, 1)
    );

    public static void main(String[] unusedArgs) {
        FileReader reader = new FileReader("input-day3.txt");
        List<String> input = reader.getInputAsStrings();

        // Parse puzzle input and turn it into a table
        int yIndex = 0;
        for (String line : input) {
            Map<Integer, Value> row = new HashMap<>();
            int xIndex = 0;
            for (char c : line.toCharArray()) {
                row.put(xIndex, new Value(c));
                xIndex++;
            }
            table.put(yIndex, row);
            yIndex++;
        }

        AtomicInteger total = new AtomicInteger(0);

        table.forEach((y, rows) -> {
            rows.forEach((x, value) -> {
                // Check if value is symbol
                if (!value.symbol) return;
                if (!(value.character == '*')) return;

                // Get surrounding numbers
                total.getAndAdd(getValidGearRatios(x, y));
            });
        });

        System.out.println("Result (2023 D3P1): " + total.get());
    }

    public static int getValidGearRatios(int x, int y) {
        List<Integer> foundValues = new ArrayList<>();

        List<OffsetPair> restrictedPairs = new ArrayList<>();
        for (OffsetPair pair : pairs) {
            if (restrictedPairs.contains(pair)) continue;

            int checkX = x + pair.x;
            int checkY = y + pair.y;
            if (checkY >= 140 || checkX >= 140 || checkX < 0 || checkY < 0) continue;

            Value val = table.get(checkY).get(checkX);
            // Check if value is number
            if (!val.number) continue;
            StringBuilder builder = new StringBuilder(String.valueOf(val.character));

            boolean characterSuffixFound = true;
            int xOffset = 1;
            while (characterSuffixFound) {
                if (checkX + xOffset >= 140) {
                    characterSuffixFound = false;
                } else {
                    Value valAt = table.get(checkY).get(checkX + xOffset);
                    if (valAt.number) {
                        builder.append(valAt.character);
                        restrictedPairs.add(new OffsetPair(pair.x + xOffset, pair.y));
                    } else {
                        characterSuffixFound = false;
                    }
                }
                xOffset++;
            }

            boolean characterPrefixFound = true;
            xOffset = -1;
            while (characterPrefixFound) {
                if (checkX + xOffset < 0) {
                    characterPrefixFound = false;
                } else {
                    Value valAt = table.get(checkY).get(checkX + xOffset);
                    if (valAt.number) {
                        builder.insert(0, valAt.character);
                        restrictedPairs.add(new OffsetPair(pair.x + xOffset, pair.y));
                    } else {
                        characterPrefixFound = false;
                    }
                }
                xOffset--;
            }

            foundValues.add(Integer.parseInt(builder.toString()));
        }

        if (foundValues.size() != 2) return 0;
        return foundValues.get(0) * foundValues.get(1);
    }

    static class Value {
        public final boolean symbol;
        public final boolean number;
        public final char character;

        public Value(char character) {
            this.number = Character.isDigit(character);
            this.symbol = !number && character != '.';
            this.character = character;
        }
    }

    static class OffsetPair {
        public final int x;
        public final int y;

        public OffsetPair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof OffsetPair other)) return false;
            return other.x == this.x && other.y == this.y;
        }
    }
}
