package com.cachedcloud.aoc25.day3;

import com.cachedcloud.aoc.common.FileReader;

import java.util.concurrent.atomic.AtomicInteger;

public class DayThreePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day3.txt");
//        FileReader reader = new FileReader("example-input-day3.txt");

        AtomicInteger count = new AtomicInteger();
        reader.getInputAsStrings().forEach(s -> {
            count.addAndGet(Integer.parseInt(jottage(s, 2)));
        });

        System.out.println("D3P1: " + count);
    }

    public static String jottage(String input, int batteries) {
        StringBuilder builder = new StringBuilder();
        int startIndex = 0;

        while (batteries > 0) {
            int highest = 0;
            int highestIndex = startIndex;
            for (int i = startIndex; i < input.length() - batteries + 1; i++) {
                char charAt = input.charAt(i);
                int intValue = charAt - '0';
                if (intValue > highest) {
                    highest = intValue;
                    highestIndex = i;
                }
            }

            builder.append(highest);
            startIndex = highestIndex + 1;

            batteries--;
        }

        return builder.toString();
    }

}
