package com.cachedcloud.aoc24.day11;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class DayElevenPartTwo {

    public static void main(String[] args) {
        Timer.start();
        FileReader reader = new FileReader("input-day11.txt");
//        FileReader reader = new FileReader("example-input-day11.txt");

        Map<Long, BigInteger> stones = new HashMap<>(); // map stone numbers (k) to occurrences (v)

        // Read input
        Arrays.stream(reader.getInputAsStrings().get(0).split(" ")).map(Long::parseLong).forEach(stone -> {
            stones.compute(stone, (k, v) -> v == null ? BigInteger.ONE : v.add(BigInteger.valueOf(1L)));
        });

        for (int iteration = 0; iteration < 100000; iteration++) {
            System.out.println("Iteration: " + iteration);
            Map<Long, List<BigInteger>> modifications = new HashMap<>();

            // Loop through stones from previous iteration
            for (Map.Entry<Long, BigInteger> entry : stones.entrySet()) {
                long number = entry.getKey();
                BigInteger occurrences = entry.getValue();

                // Rule 1: replace zeroes with ones
                if (number == 0) {
                    modifications.computeIfAbsent(number, k -> new ArrayList<>()).add(occurrences.negate());
                    modifications.computeIfAbsent(1L, k -> new ArrayList<>()).add(occurrences);
                    continue;
                }

                // Rule 2: split numbers that have an even number of digits
                String numberStr = String.valueOf(number);
                if (numberStr.length() % 2 == 0) {
                    int middleIndex = numberStr.length() / 2;
                    Long firstNumber = Long.parseLong(numberStr.substring(0, middleIndex));
                    Long secondNumber = Long.parseLong(numberStr.substring(middleIndex));

                    modifications.computeIfAbsent(number, k -> new ArrayList<>()).add(occurrences.negate());
                    modifications.computeIfAbsent(firstNumber, k -> new ArrayList<>()).add(occurrences);
                    modifications.computeIfAbsent(secondNumber, k -> new ArrayList<>()).add(occurrences);
                    continue;
                }

                // Rule 3: multiply old value by 2024
                modifications.computeIfAbsent(number, k -> new ArrayList<>()).add(occurrences.negate());
                modifications.computeIfAbsent(number*2024, k -> new ArrayList<>()).add(occurrences);
            }

            // Apply modifications
            for (Map.Entry<Long, List<BigInteger>> modification : modifications.entrySet()) {

                final BigInteger[] main = {BigInteger.ZERO};
                modification.getValue().forEach(bigInteger -> {
                    main[0] = main[0].add(bigInteger);
                });
                stones.compute(modification.getKey(), (k, v) -> {
                    if (v == null) {
                        return main[0];
                    } else {
                        return v.add(main[0]);
                    }
                });
            }

            // Remove 0 values to avoid unnecessary compute power when map grows larger
            stones.values().removeIf(val -> Objects.equals(val, BigInteger.ZERO));
        }

        AtomicReference<BigInteger> bigInteger = new AtomicReference<>(BigInteger.valueOf(0L));
        stones.values().stream().forEach(val -> {
            bigInteger.set(bigInteger.get().add(val));
        });

        System.out.println("D11P2: " + bigInteger.get().toString() + " (unique numbers: " + stones.size() + ")");
        Timer.finish();
    }

    // Bundle all modifications in a single call instead of duplicating and scattering 10 identical compute calls throughout the code
    private static void applyModifications(Map<Long, Long> mods, Long... longs) {
        for (int i = 0; i < longs.length - 1; i+=2) {
            int finalI = i;
            mods.compute(longs[i], (key, value) -> value == null ? longs[finalI + 1] : value + longs[finalI + 1]);
        }
    }
}