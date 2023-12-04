package com.cachedcloud.aoc23.day4;

import com.cachedcloud.aoc.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DayFourPartTwo {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day4.txt").getInputAsStrings();
        int size = input.size();

        // Generate a map with all scratch cards and the default amount of repetitions (which is 1)
        Map<Integer, Integer> scratchCards = IntStream.rangeClosed(1, size).boxed()
                .collect(Collectors.toMap(cardNumber -> cardNumber, reps -> 1));

        for (int i = 1; i <= size; i++) {
            // Remove string prefix and separate the winning numbers and the player card numbers
            String[] parts = input.get(i - 1).substring(10).split(" \\| ");

            List<Integer> winningNumbers = parse(parts[0]);
            List<Integer> myNumbers = parse(parts[1]);

            long matches = winningNumbers.stream().filter(myNumbers::contains).count();
            for (int rep = 0; rep < scratchCards.get(i); rep++) {
                for (int j = 0; j < matches; j++) {
                    int scratchCardAt = i + j + 1;
                    if (scratchCardAt > size) continue;

                    scratchCards.put(scratchCardAt, scratchCards.getOrDefault(scratchCardAt, 1) + 1);
                }
            }
        }

        // Log reps for debugging purposes
        scratchCards.forEach((card, amount) -> System.out.println("Card " + card + " has " + amount + " repetitions"));

        int totalScore = scratchCards.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Result (2023 D4P2): " + totalScore);
    }

    private static List<Integer> parse(String input) {
        return Arrays.stream(input.split(" "))
                .filter(str -> !str.equals(""))
                .map(Integer::parseInt)
                .toList();
    }
}