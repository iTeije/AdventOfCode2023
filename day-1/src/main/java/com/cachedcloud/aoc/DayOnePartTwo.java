package com.cachedcloud.aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayOnePartTwo {

    private static final Map<String, Integer> numberMappings = new HashMap<>() {{
        put("one", 1);
        put("two", 2);
        put("three", 3);
        put("four", 4);
        put("five", 5);
        put("six", 6);
        put("seven", 7);
        put("eight", 8);
        put("nine", 9);
    }};

    public static void main(String[] args) {
        FileReader reader = new com.cachedcloud.aoc.FileReader("input.txt");
        List<String> input = reader.getInputAsStrings();

        int sum = 0;

        // Replace text-based numbers with actual integers
        for (String str : input) {
            // Use hacky mappings method
            str = replaceMappings(str);

            // Extract numbers with regex
            String extractedNumbers = str.replaceAll("[^0-9]", "");

            // Get numbers
            String twoDigitNumber = String.valueOf(extractedNumbers.charAt(0)) + extractedNumbers.charAt(extractedNumbers.length() - 1);

            // Add to sum
            sum += Integer.parseInt(twoDigitNumber);
        }

        // Print result
        System.out.println("Result D1P2: " + sum);
    }

    private static String replaceMappings(String input) {
        StringBuilder builder = new StringBuilder(input);
        /*
         * To prevent the first number mappings from replacing a valid digit that is
         * technically earlier in the string, I will artificially create all possible string
         * combinations and match them against the number mappings instead.
         */

        int maxIndex = builder.length() - 3;

        for (int index = 0; index <= maxIndex; index++) {
            // Input length might have changed, make sure it's not out of bounds
            if (builder.length() < 3) break;

            for (int strLength = 3; strLength <= 5; strLength++) {
                // Make sure string is not of of bounds
                if (index + strLength - 1 >= builder.length()) continue;
                
                String subString = builder.substring(index, index + strLength);
                int val = numberMappings.getOrDefault(subString, -1);

                // Replace characters in string
                if (val != -1) {
                    builder.replace(index, index + strLength - 1, String.valueOf(val));
                }
            }
        }

        return builder.toString();
    }
}
