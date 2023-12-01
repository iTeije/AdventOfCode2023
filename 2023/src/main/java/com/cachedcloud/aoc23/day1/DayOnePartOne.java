package com.cachedcloud.aoc23.day1;

import com.cachedcloud.aoc.FileReader;

import java.util.List;

public class DayOnePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
        List<String> input = reader.getInputAsStrings();

        int sum = 0;

        // Loop through all lines
        for (String str : input) {
            String extractedNumbers = str.replaceAll("[^0-9]", "");

            // Get numbers
            String twoDigitNumber = String.valueOf(extractedNumbers.charAt(0)) + extractedNumbers.charAt(extractedNumbers.length() - 1);

            // Add to sum
            sum += Integer.parseInt(twoDigitNumber);
        }

        // Print result
        System.out.println("Result D1P1: " + sum);
    }

}
