package com.cachedcloud.aoc24.day2;

import com.cachedcloud.aoc.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayTwoPartTwo {

    public static void main(String[] args) {
//        FileReader fileReader = new FileReader("example-input-day2.txt");
        FileReader fileReader = new FileReader("input-day2.txt");
        List<String> input = fileReader.getInputAsStrings();

        int safe = 0;
        int jokered =0;

        for (String line : input) {
            String[] parts = line.split(" ");
            List<String> partList = new ArrayList<>(Arrays.asList(parts));

            int increasing = 0;
            int decreasing = 0;
            boolean unsafe = false;
            boolean joker = false;
            int previous = -1;

            int index =0;
            int lastIncreasingIndex = 0;
            int lastDecreasingIndex = 0;
            partsLoop:
            for (String part : parts) {
                int current = Integer.parseInt(part);
                if (previous == -1) {
                    previous = current;
                    index++;
                    continue;
                }
                if (current > previous) {
                    increasing++;
                    lastIncreasingIndex = index;
                }
                if (current < previous) {
                    decreasing++;
                    lastDecreasingIndex = index;
                }
                if (Math.abs(current - previous) > 3 || (current - previous) == 0) {
                    joker = true;
                    partList.remove(index);
                    break partsLoop;
                };
                previous = current;
                index++;
            }
            if ((increasing != 0 && decreasing > increasing) && !joker) {
                System.out.println(decreasing + " " + increasing + " (decreasing dominant)");
                partList.remove(lastIncreasingIndex);
                joker = true;
            } else if ((decreasing != 0 && increasing > decreasing) && !joker) {
                System.out.println(decreasing + " " + increasing + " (increasing dominant)");
                partList.remove(lastDecreasingIndex);
                joker = true;
            }

            if (joker) {
                jokered++;
                increasing = 0;
                decreasing = 0;
                previous = -1;
                for (String part : partList) {
                    int current = Integer.parseInt(part);
                    if (previous == -1) {
                        previous = current;
                        continue;
                    }
                    if (current > previous) increasing++;
                    if (current < previous) decreasing++;
                    if (Math.abs(current - previous) > 3 || (current - previous) == 0) {
                        unsafe = true;
                    };
                    previous = current;
                }
            }

            if (((increasing > 0 && decreasing == 0) || (decreasing > 0 && increasing == 0)) && !unsafe) {
                System.out.println("Joker " + joker + ": " + partList + " " + Arrays.toString(parts));
                safe++;
            } else {
                System.out.println("UNSAFE: Joker " + joker + ": " + partList + " " + Arrays.toString(parts) + ": " + decreasing + " " + increasing + " " + unsafe);
            }
        }

        System.out.println(jokered);
        System.out.println(safe);
    }

    public boolean isSafe(List<Integer> list) {
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

        return !unsafe || ((increasing > 0 && decreasing == 0) || (decreasing > 0 && increasing == 0));
    }
}
