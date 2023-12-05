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

    // Configuration
    private static int SPREAD = 1000;
    private static double DIFF = 0.1;

    public static void main(String[] args) {
        Timer.start();

        List<String> input = new FileReader("input-day5.txt").getInputAsStrings();

        List<Long> seeds = Arrays.stream(input.get(0).substring(7).split(" ")).map(Long::valueOf).toList();
        input = input.subList(2, input.size());

        List<SeedRange> seedRanges = new ArrayList<>();
        for (int i = 0; i < seeds.size(); i+=2) {
            long range = seeds.get(i + 1);

            seedRanges.add(new SeedRange(seeds.get(i), range));
        }

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

        // Attempts to reduce overhead
        List<List<Mapping>> cachedMappingValues = new ArrayList<>(mappings.values());
        List<Long> locationNumbers = new ArrayList<>();

        double upper = 1 + DIFF;
        double lower = 1 - DIFF;

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

                /*
                Check if the final location is much different from the previous location.
                If so, it is confirmed that this seed has a different path through the mappings
                than the previous one, which means that we should process the surrounding seeds
                as well.
                 */
                double calc = (double) previous.get() / correspondingNumber;
                if (calc > upper || calc < lower) {
                    SeedRange newRange = new SeedRange(seed - SPREAD, SPREAD);
                    additionalRanges.add(newRange);
                }

                previous.set(correspondingNumber);
                return correspondingNumber;
            }).min();

            // Perform the same calculation on the seed ranges that were added in the previous
            // calculation.
            AtomicLong altMin = new AtomicLong(Long.MAX_VALUE);
            additionalRanges.parallelStream().forEach(altRange -> {
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
                    return correspondingNumber;
                }).min();

                altMin.set(Math.min(secondOptionalLong.orElse(Long.MAX_VALUE), altMin.get()));
            });

            locationNumbers.add(Math.min(optionalLong.orElse(Long.MAX_VALUE), altMin.get()));
        });

        // Sort list of location numbers from lowest to highest
        Collections.sort(locationNumbers);

        System.out.println("Result (2023 D5P2): " + locationNumbers.get(0));
        Timer.finish();
    }

    /**
     * Generate an optimized LongStream that only includes one in every thousand seeds. If the
     * final location differs too much from the previous seed, it will process the seeds
     * around it as well.
     *
     * @param range the seed-range
     * @return the generated {@link LongStream}
     */
    private static LongStream generateLongStream(SeedRange range) {
        LongStream.Builder builder = LongStream.builder();

        // Add first values
        for (int i = 0; i <= SPREAD; i++) {
            builder.add(range.start + i);
            builder.add(range.computedEnd - i);
        }

        // Add values in range (every specified number of times)
        long i = range.start;
        while (i < range.computedEnd) {
            builder.add(i);
            i+= SPREAD;
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
