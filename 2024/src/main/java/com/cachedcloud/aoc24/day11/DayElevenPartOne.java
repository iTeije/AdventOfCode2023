package com.cachedcloud.aoc24.day11;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayElevenPartOne {

    // This is obviously not working in P2

    public static void main(String[] args) {
        Timer.start();
        FileReader reader = new FileReader("input-day11.txt");
//        FileReader reader = new FileReader("example-input-day11.txt");

        List<Long> stones = new ArrayList<>(Arrays.stream(reader.getInputAsStrings().get(0).split(" ")).map(Long::parseLong).toList());

        for (int iteration = 0; iteration < 25; iteration++) {
            int startingSize = stones.size();
            for (int i = startingSize - 1; i >= 0; i--) {
                long number = stones.get(i);
                // Rule 1: replace zeroes with ones
                if (number == 0) {
                    stones.set(i, 1L);
                    continue;
                }

                // Rule 2: split numbers that have an even number of digits
                // Since the numbers need to be split, a logarithmic approach makes no sense, so I'll just go
                // with the string length approach.
                String numberStr = String.valueOf(number);
                if (numberStr.length() % 2 == 0) {
                    int middleIndex = numberStr.length() / 2;
                    stones.set(i, Long.parseLong(numberStr.substring(0, middleIndex)));
                    stones.add(i + 1, Long.parseLong(numberStr.substring(middleIndex)));
                    continue;
                }

                // Rule 3: new stone with old value multiplied by 2024
                stones.set(i, number * 2024);
            }
        }

        System.out.println("D11P1: " + stones.size());
        Timer.finish();
    }

}
