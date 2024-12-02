package com.cachedcloud.aoc24.day2;

import com.cachedcloud.aoc.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayTwoPartTwo {

    public static void main(String[] args) {
//        FileReader fileReader = new FileReader("example-input-day2.txt");
        FileReader fileReader = new FileReader("input-day2.txt");
        List<String> input = fileReader.getInputAsStrings();

        int safe = 0;

        inputLoop:
        for (String line : input) {
            String[] parts = line.split(" ");
            List<Integer> partList = Arrays.stream(parts).map(Integer::parseInt).toList();

            for (int i = 0; i < partList.size(); i++) {
                List<Integer> clone = new ArrayList<>(partList);
                clone.remove(i);
                if (isSafe(clone)) {
                    safe++;
                    continue inputLoop;
                }
            }
        }

        System.out.println(safe);
    }

    public static boolean isSafe(List<Integer> list) {
        int increasing = 0;
        int decreasing = 0;
        boolean unsafe = false;
        int previous = -1;

        for (int current : list) {
            if (previous == -1) {
                previous = current;
                continue;
            }
            if (current > previous) increasing++;
            if (current < previous) decreasing++;
            if (Math.abs(current - previous) > 3 || (current - previous) == 0) unsafe = true;
            previous = current;
        }

        return !unsafe && ((increasing > 0 && decreasing == 0) || (decreasing > 0 && increasing == 0));
    }
}
