package com.cachedcloud.aoc23.day4;

import com.cachedcloud.aoc.FileReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayFourPartTwo {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day4.txt").getInputAsStrings();
        int size = input.size();

        Map<Integer, Integer> scratchCards = new HashMap<>();

        for (int i = 1; i <= size; i++) {
            int repetitions = scratchCards.getOrDefault(i, 1);
            if (repetitions == 1) scratchCards.put(i, 1);

            String line = input.get(i - 1).substring(10);
            String[] parts = line.split(" \\| ");

            List<Integer> winningNumbers = Arrays.stream(parts[0].split(" "))
                    .filter(str -> !str.equals(""))
                    .map(Integer::parseInt)
                    .toList();

            List<Integer> myNumbers = Arrays.stream(parts[1].split(" "))
                    .filter(str -> !str.equals(""))
                    .map(Integer::parseInt)
                    .toList();

            long matches = winningNumbers.stream().filter(myNumbers::contains).count();
            for (int rep = 0; rep < repetitions; rep++) {
                for (int j = 0; j < matches; j++) {
                    int scratchCardAt = i + j + 1;
                    if (scratchCardAt > size) continue;

                    scratchCards.put(scratchCardAt, scratchCards.getOrDefault(scratchCardAt, 1) + 1);
                }
            }
        }

        int totalScore = scratchCards.values().stream().mapToInt(Integer::intValue).sum();
        scratchCards.forEach((card, amount) -> {
            System.out.println("Card " + card + " has " + amount + " repetitions");
        });

        System.out.println("Result (2023 D4P2): " + totalScore);
    }
}