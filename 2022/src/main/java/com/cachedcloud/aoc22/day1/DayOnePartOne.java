package com.cachedcloud.aoc22.day1;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayOnePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day1.txt");
        List<String> input = reader.getInputAsStrings();

        int runningCount = 0;
        int max = 0;

        for (String str : input) {
            if (str.equals("")) {
                if (runningCount > max) max = runningCount;
                runningCount = 0;
                continue;
            }

            runningCount += Integer.parseInt(str);
        }

        System.out.println("Result (2022 D1P1): " + max);
    }
}