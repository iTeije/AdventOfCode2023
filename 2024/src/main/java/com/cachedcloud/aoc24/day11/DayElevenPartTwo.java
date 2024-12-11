package com.cachedcloud.aoc24.day11;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.Timer;

import java.util.*;

public class DayElevenPartTwo {

    public static void main(String[] args) {
        Timer.start();
        FileReader reader = new FileReader("input-day11.txt");
//        FileReader reader = new FileReader("example-input-day11.txt");

        Map<Long, Long> stones = new HashMap<>(); // map stone numbers (k) to occurrences (v)

        // Read input
        Arrays.stream(reader.getInputAsStrings().get(0).split(" ")).map(Long::parseLong).forEach(stone -> {
            stones.compute(stone, (k, v) -> v == null ? 1 : v + 1);
        });

        for (int iteration = 0; iteration < 75; iteration++) {
            Map<Long, Long> modifications = new HashMap<>();

            // Loop through stones from previous iteration
            for (Map.Entry<Long, Long> entry : stones.entrySet()) {
                long number = entry.getKey();
                long occurrences = entry.getValue();

                // Rule 1: replace zeroes with ones
                if (number == 0) {
                    applyModifications(modifications,
                            number, -occurrences,
                            1L, occurrences);
                    continue;
                }

                // Rule 2: split numbers that have an even number of digits
                String numberStr = String.valueOf(number);
                if (numberStr.length() % 2 == 0) {
                    int middleIndex = numberStr.length() / 2;
                    Long firstNumber = Long.parseLong(numberStr.substring(0, middleIndex));
                    Long secondNumber = Long.parseLong(numberStr.substring(middleIndex));

                    applyModifications(modifications,
                            number, -occurrences,
                            firstNumber, occurrences,
                            secondNumber, occurrences);
                    continue;
                }

                // Rule 3: multiply old value by 2024
                applyModifications(modifications,
                        number, -occurrences,
                        number * 2024, occurrences);
            }

            // Apply modifications
            for (Map.Entry<Long, Long> modification : modifications.entrySet()) {
                stones.compute(modification.getKey(), (k, v) -> {
                    if (v == null) {
                        return modification.getValue();
                    } else {
                        return v + modification.getValue();
                    }
                });
            }

            // Remove 0 values to avoid unnecessary compute power when map grows larger
            stones.values().removeIf(val -> val == 0);
        }

        System.out.println("D11P2: " + stones.values().stream().mapToLong(Long::longValue).sum() + " (unique numbers: " + stones.size() + ")");
        Timer.finish();
    }

    // Bundle all modifications in a single call instead of duplicating and scattering 10 identical compute calls throughout the code
    private static void applyModifications(Map<Long, Long> mods, Long... longs) {
        for (int i = 0; i < longs.length - 1; i+=2) {
            int finalI = i;
            mods.compute(longs[i], (key, value) -> value == null ? longs[finalI + 1] : value + longs[finalI + 1]);
        }
    }
}