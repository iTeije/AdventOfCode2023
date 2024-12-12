package com.cachedcloud.aoc24.day1;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;

import java.util.*;

public class DayOnePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();
        Map<Integer, Short> frequencies = new HashMap<>();

        List<Integer> firstList = new ArrayList<>();

        for (String line : input) {
            String[] parts = line.split(" {3}");
            firstList.add(Integer.parseInt(parts[0]));

            frequencies.compute(Integer.parseInt(parts[1]), (key, val) -> {
                if (val == null) {return (short) 1;}
                return (short) (val + 1);
            });
        }

        int similarityScore = 0;

        for (Integer leftInteger : firstList) {
            similarityScore += leftInteger * frequencies.getOrDefault(leftInteger, (short) 0);
        }

        System.out.printf("D1P2 Answer: " + similarityScore);
        Timer.finish();
    }

}
