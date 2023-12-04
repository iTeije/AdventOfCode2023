package com.cachedcloud.aoc23.day4;

import com.cachedcloud.aoc.FileReader;

import java.util.Arrays;
import java.util.List;

public class DayFourPartOne {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day4.txt").getInputAsStrings();

        int totalScore = 0;

        for (String line : input) {
            line = line.substring(10);
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
            if (matches == 0) continue;

            totalScore += Math.pow(2, matches - 1);
        }

        System.out.println("Result (2023 D4P1): " + totalScore);
    }
}
