package com.cachedcloud.aoc24.day1;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.Timer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayOnePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();

        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = new ArrayList<>();

        for (String line : input) {
            String[] parts = line.split(" {3}");
            firstList.add(Integer.parseInt(parts[0]));
            secondList.add(Integer.parseInt(parts[1]));
        }

        long similarityScore = 0;

        for (Integer leftInteger : firstList) {
            similarityScore += (long) leftInteger * Collections.frequency(secondList, leftInteger);
        }

        System.out.printf("D1P2 Answer: " + similarityScore);
        Timer.finish();
    }

}
