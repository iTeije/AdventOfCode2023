package com.cachedcloud.aoc24.day1;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.Timer;

import java.util.ArrayList;
import java.util.List;

public class DayOnePartOne {

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

        firstList.sort(Integer::compareTo);
        secondList.sort(Integer::compareTo);

        long diffCount = 0;
        for (int i = 0; i < firstList.size(); i++) {
            diffCount += Math.abs(firstList.get(i) - secondList.get(i));
        }

        System.out.printf("D1P1 Answer: " + diffCount);
        Timer.finish();
    }
}
