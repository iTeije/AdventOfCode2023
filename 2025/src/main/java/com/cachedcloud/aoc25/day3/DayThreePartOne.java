package com.cachedcloud.aoc25.day3;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayThreePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day3.txt");
//        FileReader reader = new FileReader("example-input-day3.txt");
        List<String> input = reader.getInputAsStrings();

        int count = 0;
        for (String line : input) {
            int firstHighest = 0;
            int highestIndex = 0;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                int intValue = c - '0';

                if (intValue > firstHighest) {
                    firstHighest = intValue;
                    highestIndex = i;
                }
            }

            int secondHighest = 0;
            int secondIndex = 0;
            boolean isLast = highestIndex == line.length() - 1;

            for (int i = 0; i < line.length(); i++) {
                if (i == highestIndex) continue;

                char c = line.charAt(i);
                int intValue = c - '0';

                if (intValue > secondHighest && (isLast || i > highestIndex || intValue >= firstHighest)) {
                    secondHighest = intValue;
                    secondIndex = i;
                }
            }

            if (secondIndex > highestIndex) {
                System.out.println("s>h " + firstHighest + secondHighest);
                count += Integer.parseInt(String.valueOf(firstHighest + "" + secondHighest));
            } else {
                System.out.println("s<h " + secondHighest + firstHighest);
                count += Integer.parseInt(String.valueOf(secondHighest + "" + firstHighest));
            }
        }



        System.out.println("D3P1: " + count);
    }

}
