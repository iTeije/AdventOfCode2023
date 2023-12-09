package com.cachedcloud.aoc23.day9;

import com.cachedcloud.aoc.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayNinePartTwo {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day9.txt").getInputAsStrings();

        // Parse input and turn them into the primary sequences
        List<List<Integer>> sequences = input.stream().map(line -> {
            String[] parts = line.split(" ");
            return Arrays.stream(parts).map(Integer::parseInt).collect(Collectors.toList());
        }).toList();

        long sum = 0;

        // Loop through sequences
        for (List<Integer> sequence : sequences) {
            // Create list of subsequences and add the primary sequence to itself
            List<List<Integer>> subSequences = new ArrayList<>();
            subSequences.add(sequence);

            // Create subsequences until it only generates zeros
            boolean allZeros = false;
            while (!allZeros) {
                // Get previous subsequence (which is the primary sequence on the first iteration)
                List<Integer> previousSequence = subSequences.get(subSequences.size() - 1);

                List<Integer> newSubSequence = new ArrayList<>();
                int total = 0;

                // Calculate numbers between values from the previous sequence
                for (int i = 0; i < previousSequence.size() - 1; i++) {
                    int delta = previousSequence.get(i + 1) - previousSequence.get(i);
                    newSubSequence.add(delta);
                    total += Math.abs(delta);
                }

                // Add subsequence to list of subsequences and check if this is the last one
                subSequences.add(newSubSequence);
                if (total == 0) allZeros = true;
            }

            int count = 0;
            for (int i = 0; i < subSequences.size(); i++) {
                List<Integer> currentSubSequence = subSequences.get(subSequences.size() - i - 1);
                int currentFirstNumber = currentSubSequence.get(0);
                count = currentFirstNumber - count;
                currentSubSequence.add(0, count);
            }

            sum += count;
        }

        System.out.println("Result (2023 D9P2): " + sum);
    }
}
