package com.cachedcloud.aoc23.day5;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.Timer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DayFivePartOne {

    public static void main(String[] args) {
        Timer.start();
        List<String> input = new FileReader("input-day5.txt").getInputAsStrings();

        List<Long> seeds = Arrays.stream(input.get(0).substring(7).split(" ")).map(Long::valueOf).toList();
        input = input.subList(2, input.size() - 1);

        Map<Integer, LinkedList<Mapping>> mappings = new HashMap<>();
        int index = 0;

        // Advent of Input Parsing
        for (String line : input) {
            // Detect new category and increase index
            if (line.contains("-")) {
                index++;
                mappings.put(index, new LinkedList<>());
                continue;
            } else if (line.equals("")) continue;

            String[] parts = line.split(" ");

            // Save the three numbers from each line as a mapping for this specific category
            mappings.get(index).add(new Mapping(Long.parseLong(parts[0]), Long.parseLong(parts[1]), Long.parseLong(parts[2])));
        }

        List<Long> locationNumbers = new ArrayList<>();

        // Loop seeds through
        for (Long seed : seeds) {
            long correspondingNumber = seed;
            categoryLoop:
            // Loop through all categories until the input number (starting off with the seed) finds a mapping that is in range
            for (List<Mapping> category : mappings.values()) {
                for (Mapping mapping : category) {
                    // Get output from mapping (-1 if not in range)
                    long result = mapping.getOutput(correspondingNumber);
                    // If the input is in range of the mapping, save the number and continue to the next category
                    if (result != -1) {
                        correspondingNumber = result;
                        continue categoryLoop;
                    }
                }
            }
            // Cache final location number
            locationNumbers.add(correspondingNumber);
            System.out.println("Seed " + seed + " has location " + correspondingNumber);
        }

        // Sort list of location numbers from lowest to highest
        Collections.sort(locationNumbers);

        System.out.println("Result (2023 D5P1): " + locationNumbers.get(0));
        Timer.finish();
    }

    static class Mapping {
        public final long sourceStart;

        public final long computedEnd;
        public final long computedDiff;

        public Mapping(long destStart, long sourceStart, long sourceRange) {
            this.sourceStart = sourceStart;

            this.computedEnd = sourceStart + sourceRange;
            this.computedDiff = destStart - sourceStart;
        }

        public long getOutput(long input) {
            if (input > sourceStart && input < computedEnd) {
                return input + computedDiff;
            }
            return -1;
        }
    }
}
