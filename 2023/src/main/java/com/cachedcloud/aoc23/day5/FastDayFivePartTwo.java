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
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class FastDayFivePartTwo {

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

        // Loop through seed ranges
        seedRanges.parallelStream().forEach(range -> {
            List<SeedRange> additionalRanges = new ArrayList<>();

            AtomicLong previous = new AtomicLong(0);
            OptionalLong optionalLong = generateLongStream(range).map(seed -> {
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
                double calc = (double) previous.get() / correspondingNumber;
                if (calc > 1.2 || calc < 0.8) {
                    SeedRange newRange = new SeedRange(seed - 1000, 1000);
                    System.out.println("Adding new range from " + seed + " to " + newRange.computedEnd);
                    additionalRanges.add(newRange);
                }
                previous.set(correspondingNumber);
                return correspondingNumber;
            }).min();

            long altMin = Long.MAX_VALUE;
            for (SeedRange altRange : additionalRanges) {
                OptionalLong secondOptionalLong = LongStream.range(altRange.start, altRange.computedEnd).map(seed -> {
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

                altMin = Math.min(secondOptionalLong.orElse(Long.MAX_VALUE), altMin);
            }

            locationNumbers.add(Math.min(optionalLong.orElse(Long.MAX_VALUE), altMin));
        });

        // Sort list of location numbers from lowest to highest
        Collections.sort(locationNumbers);

        System.out.println("Result (2023 D5P2): " + locationNumbers.get(0));
        Timer.finish();
    }

    private static LongStream generateLongStream(SeedRange range) {
        LongStream.Builder builder = LongStream.builder();

        // Add first values
        long i = range.start;
        while (i % 1000 != 0) {
            builder.add(i);
            i++;
        }

        // Add values in range (every 1000)
        i = range.start;
        while (i < range.computedEnd) {
            builder.add(i);
            i+= 1000;
        }

        // Add last values
        for (i = range.computedEnd - (range.computedEnd % 1000); i < range.computedEnd; i++) {
            builder.add(i);
        }

        return builder.build();
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
