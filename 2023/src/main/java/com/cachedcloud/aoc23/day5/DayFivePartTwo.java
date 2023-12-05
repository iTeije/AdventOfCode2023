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
import java.util.OptionalLong;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class DayFivePartTwo {

    public static void main(String[] args) {
        Timer.start();
        List<String> input = new FileReader("input-day5.txt").getInputAsStrings();

        List<Long> seeds = Arrays.stream(input.get(0).substring(7).split(" ")).map(Long::valueOf).toList();
        input = input.subList(2, input.size());

        List<SeedRange> seedRanges = new ArrayList<>();
        long amountOfSeeds = 0;
        for (int i = 0; i < seeds.size(); i+=2) {
            long range = seeds.get(i + 1);

            seedRanges.add(new SeedRange(seeds.get(i), range));
            amountOfSeeds += range;
        }

        System.out.println(seedRanges.size() + " pairs with a total of " + amountOfSeeds + " seeds.");

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

        AtomicLong finished = new AtomicLong();

        // Attempts to reduce overhead
        List<List<Mapping>> cachedMappingValues = new ArrayList<>(mappings.values());
        List<Long> locationNumbers = new ArrayList<>();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        long finalAmountOfSeeds = amountOfSeeds;
        scheduler.scheduleAtFixedRate(() -> {
            printProgress(finalAmountOfSeeds, finished.get());
        }, 1, 1, TimeUnit.SECONDS);

        // Loop through seed ranges
        seedRanges.parallelStream().forEach(range -> {
            OptionalLong optionalLong = LongStream.range(range.start, range.computedEnd).map(seed -> {
                long correspondingNumber = seed;
                categoryLoop:
                // Loop through all categories until the input number (starting off with the seed) finds a mapping that is in range
                for (List<Mapping> category : cachedMappingValues) {
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
                finished.getAndIncrement();
                return correspondingNumber;
            }).min();

            locationNumbers.add(optionalLong.orElse(Long.MAX_VALUE));
        });

        // Sort list of location numbers from lowest to highest
        Collections.sort(locationNumbers);
        System.out.println(locationNumbers.toString());

        System.out.println("Result (2023 D5P2): " + locationNumbers.get(0));
        Timer.finish();

        scheduler.shutdown();
    }

    private static void printProgress(long total, long currentCompletedItems) {
        double progressPercentage = (double) currentCompletedItems / total * 100;

        // Print progress as a progress bar
        System.out.print("\rProgress: [");
        int progressChars = (int) (currentCompletedItems / (double) total * 50);
        for (int i = 0; i < 50; i++) {
            System.out.print(i < progressChars ? "=" : " ");
        }
        System.out.print("] ");

        // Print progress as raw amounts
        System.out.print("Completed: " + currentCompletedItems + "/" + total);

        // Print progress as a percentage
        System.out.printf(" (%.2f%%)", progressPercentage);

        // Flush to ensure the output is displayed
        System.out.flush();

        if (currentCompletedItems == total) {
            System.out.println("\nShutting down progress thread.");
            Thread.currentThread().interrupt();
        }
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
            if (input >= sourceStart && input < computedEnd) {
                return input + computedDiff;
            }
            return -1;
        }
    }

    static class SeedRange {
        public final long start;
        public final long range;

        public final long computedEnd;

        public SeedRange(long start, long range) {
            this.start = start;
            this.range = range;

            this.computedEnd = start + range;
        }
    }
}
