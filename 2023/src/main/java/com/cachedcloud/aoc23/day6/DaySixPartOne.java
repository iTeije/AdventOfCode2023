package com.cachedcloud.aoc23.day6;

import com.cachedcloud.aoc.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DaySixPartOne {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day6.txt").getInputAsStrings();

        // Filter input
        input = input.stream().map(s -> s.trim().replaceAll(" +", " ")).collect(Collectors.toList());

        int[] times = Arrays.stream(input.get(0).split(" ")).filter(s -> !s.contains(":")).mapToInt(Integer::parseInt).toArray();
        int[] distances = Arrays.stream(input.get(1).split(" ")).filter(s -> !s.contains(":")).mapToInt(Integer::parseInt).toArray();

        List<Integer> beatingPossibilities = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            int time = times[i];
            int distance = distances[i];

            int canBeat = 0;

            for (int btnTime = 1; btnTime <= time; btnTime++) {
                int finalDistance = btnTime * (time - btnTime);
                if (finalDistance > distance) canBeat++;
            }
            beatingPossibilities.add(canBeat);
        }

        System.out.println("Result (2023 D6P1): " + beatingPossibilities.stream().reduce(1, (a, b) -> a * b));
    }
}
