package com.cachedcloud.aoc22.day1;

import com.cachedcloud.aoc.FileReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DayOnePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
        List<String> input = reader.getInputAsStrings();

        int runningCount = 0;
        List<Integer> totals = new ArrayList<>();

        for (String str : input) {
            if (str.equals("")) {
                totals.add(runningCount);
                runningCount = 0;
                continue;
            }

            runningCount += Integer.parseInt(str);
        }

        // Sort and reverse list
        Collections.sort(totals);
        Collections.reverse(totals);

        int total = totals.stream().limit(3).mapToInt(Integer::intValue).sum();

        System.out.println("Result (2022 D1P2): " + total);
    }
}
