package com.cachedcloud.aoc25.day1;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayOnePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
//        FileReader reader = new FileReader("example-input-day1.txt");
        List<String> input = reader.getInputAsStrings();

        int dial = 50;
        int zeroCount = 0;

        for (String str : input) {
            char rotation = str.charAt(0);
            int shift = Integer.parseInt(str.substring(1));

            if (rotation == 'L') {
                int value = dial == 0 ? 100 : dial;
                if (shift >= value) zeroCount += 1 + (shift - value) / 100;
                dial = (dial - shift + 1000) % 100;
            } else {
                int value = dial + shift;
                zeroCount += (dial + shift) / 100;
                dial = value % 100;
            }
        }

        System.out.println("D1P2: " + zeroCount);
    }
}
