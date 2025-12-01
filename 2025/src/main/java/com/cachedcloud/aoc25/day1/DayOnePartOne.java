package com.cachedcloud.aoc25.day1;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayOnePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
//        FileReader reader = new FileReader("example-input-day1.txt");
        List<String> input = reader.getInputAsStrings();

        int dial = 50;
        int zeroCount = 0;

        for (String str : input) {
            char rotation = str.charAt(0);
            int shift = Integer.parseInt(str.substring(1));

            dial = rotation == 'L' ? dial - shift : dial + shift;
            dial %= 100;

            if (dial < 0) {
                dial = dial + 100;
            }

            if (dial == 0) zeroCount++;
        }

        System.out.println("D1P1: " + zeroCount);
    }
}
