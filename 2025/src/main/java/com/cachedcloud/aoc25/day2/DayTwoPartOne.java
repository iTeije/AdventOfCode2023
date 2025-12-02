package com.cachedcloud.aoc25.day2;

import com.cachedcloud.aoc.common.FileReader;

public class DayTwoPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day2.txt");
//        FileReader reader = new FileReader("example-input-day2.txt");
        String input = reader.getInputAsStrings().get(0);

        String[] rangesArray = input.split(",");

        long count = 0;

        for (String range : rangesArray) {
            // Split range into the lower and upper bound
            String[] elements = range.split("-");

            // Iterate through all values between the ranges
            for (long i = Long.parseLong(elements[0]); i <= Long.parseLong(elements[1]); i++) {
                // Create a string from the value
                String stringRepresentation = String.valueOf(i);
                int sequenceLength = stringRepresentation.length();

                // Ignore strings with odd length (a repeating sequence will always have an even number of characters)
                if (sequenceLength % 2 != 0) continue;

                if (stringRepresentation.substring(0, sequenceLength / 2).equals(stringRepresentation.substring(sequenceLength / 2))) {
                    System.out.println(stringRepresentation);
                    count += i;
                }
            }
        }

        System.out.println("D2P1: " + count);
    }

}
