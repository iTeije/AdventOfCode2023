package com.cachedcloud.aoc24.day7;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DaySevenPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day7.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();

        long count = 0L;

        for (String line : input) {
            String[] parts = line.split(": ");
            long answer = Long.parseLong(parts[0]);
            LinkedList<Long> longValues = new LinkedList<>();

            String[] values = parts[1].split(" ");
            Arrays.stream(values).forEach(v -> longValues.add(Long.parseLong(v)));

            // Execute recursive function
            if (isTrue(answer, longValues.removeFirst(), longValues)) count += answer;
        }

        System.out.println("D7P1: " + count);
    }

    public static boolean isTrue(long expectedAnswer, long currentValue, LinkedList<Long> longValues) {
        // Exit condition
        if (longValues.isEmpty()) {
            return expectedAnswer == currentValue;
        }

        LinkedList<Long> copiedList = new LinkedList<>(longValues);
        copiedList.removeFirst();

        long nextValue = longValues.removeFirst();
        return isTrue(expectedAnswer, currentValue + nextValue, longValues)
                || isTrue(expectedAnswer, currentValue * nextValue, copiedList);
    }
}
