package com.cachedcloud.aoc22.day3;

import com.cachedcloud.aoc.FileReader;

import javax.swing.*;
import java.util.List;

public class DayThreePartOne {

    public static void main(String[] unusedArgs) {
        FileReader reader = new FileReader("input-day3.txt");
        List<String> input = reader.getInputAsStrings();

        int sum = 0;

        for (String line : input) {
            int len = line.length();
            String firstHalf = line.substring(0, len / 2);
            String secondHalf = line.substring(len / 2, len);

            int highestPriority = 0;
            for (char ch : firstHalf.toCharArray()) {
                if (secondHalf.contains(String.valueOf(ch))) {
                    int priority = getPriority(ch);
                    if (priority > highestPriority) highestPriority = priority;
                }
            }

            sum += highestPriority;
        }

        System.out.println("Result (2022 D3P1): " + sum);
    }

    private static int getPriority(char ch) {
        int offset = Character.isUpperCase(ch) ? 38 : 96;
        return (int) ch - offset;
    }
}
