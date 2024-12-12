package com.cachedcloud.aoc24.day2;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayTwoPartOne {

    public static void main(String[] args) {
//        FileReader fileReader = new FileReader("example-input-day2.txt");
        FileReader fileReader = new FileReader("input-day2.txt");
        List<String> input = fileReader.getInputAsStrings();

        int safe = 0;
        for (String line : input) {
            String[] parts = line.split(" ");

            int increasing = 0;
            int decreasing = 0;
            boolean unsafe = false;
            int previous = -1;
            for (String part : parts) {
                int current = Integer.parseInt(part);
                if (previous == -1) {
                    previous = current;
                    continue;
                }
                if (current > previous) increasing++;
                if (current < previous) decreasing++;
                if (Math.abs(current - previous) > 3 || (current - previous) == 0) unsafe = true;
                previous = current;
            }

            if (((increasing > 0 && decreasing == 0) || (decreasing > 0 && increasing == 0)) && !unsafe) {
                System.out.println(increasing + " " + decreasing);
                safe++;
            }
        }

        System.out.println("D2P1 Answer: " + safe);
    }

}
