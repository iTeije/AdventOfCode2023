package com.cachedcloud.aoc25.day2;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;

import java.util.HashMap;
import java.util.Map;

public class DayTwoPartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day2.txt");
//        FileReader reader = new FileReader("example-input-day2.txt");
        String input = reader.getInputAsStrings().get(0);
        Timer.start();

        String[] rangesArray = input.split(",");

        long count = 0;

        for (String range : rangesArray) {
            // Split range into the lower and upper bound
            String[] elements = range.split("-");

            // Iterate through all values between the ranges
            mainLoop:
            for (long i = Long.parseLong(elements[0]); i <= Long.parseLong(elements[1]); i++) {
                // Create a string from the value
                String stringRepresentation = String.valueOf(i);
                int sequenceLength = stringRepresentation.length();
                int half = sequenceLength / 2;

                Map<Character, Short> digits = new HashMap<>();
                // Check if amount of unique digits makes for a possible repeating sequence
                for (char c : stringRepresentation.toCharArray()) {
                    short val = digits.getOrDefault(c, (short) 0);
                    digits.put(c, val);
                    if (digits.size() > half) {
                        continue mainLoop;
                    }
                }

                // If there is only 1 occurring digit in the sequence, it must be an invalid ID
                int uniqueDigits = digits.size();
                if (uniqueDigits == 1) {
                    count += i;
                    continue mainLoop;
                }

                // Split the string into appropriate sizes (up to half the string) and check if they are equal
                sequenceLoop:
                for (int split = uniqueDigits; split <= half; split++) {
                    String[] results = stringRepresentation.split("(?<=\\G.{" + split + "})");
                    String check = results[0];

                    // Check if the parts are equal
                    for (String result : results) {
                        if (!result.equals(check)) {
                            continue sequenceLoop;
                        }
                    }

                    // Count towards total
                    count += i;
                    continue mainLoop;
                }
            }
        }

        Timer.finish();
        System.out.println("D2P1: " + count);
    }
}
