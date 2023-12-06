package com.cachedcloud.aoc23.day6;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.Timer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DaySixPartTwo {

    public static void main(String[] unusedArgs) {
        Timer.start();
        List<String> input = new FileReader("input-day6.txt").getInputAsStrings();

        // Filter input
        input = input.stream().map(s -> s.trim().replaceAll(" +", " ")).collect(Collectors.toList());

        long time = Long.parseLong(Arrays.stream(input.get(0).split(" ")).filter(s -> !s.contains(":")).collect(Collectors.joining("")));
        long distance = Long.parseLong(Arrays.stream(input.get(1).split(" ")).filter(s -> !s.contains(":")).collect(Collectors.joining("")));

        AtomicLong canBeat = new AtomicLong();

        // Create a sequential intstream that runs all possible times
        IntStream.range(1, (int) (time + 1)).sequential().forEach(buttonTime -> {
            long timeRemaining = time - buttonTime;
            long finalDistance = timeRemaining * buttonTime;
            if (finalDistance > distance) {
                canBeat.getAndIncrement();
            }
        });

        System.out.println("Result (2023 D6P2): " + canBeat.get());
        Timer.finish();
    }
}
