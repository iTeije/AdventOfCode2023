package com.cachedcloud.aoc25.day5;

import com.cachedcloud.aoc.common.FileReader;
import java.util.*;

public class DayFivePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day5.txt");
//        FileReader reader = new FileReader("example-input-day5.txt");

        List<String> input = reader.getInputAsStrings();

        List<Pair> pairs = new ArrayList<>();
        long count = 0;
        long highestId = 0;

        // parsing input
        for (String line : input) {
            if (line.isEmpty()) {
                break;
            }
            String[] elements = line.split("-");
            pairs.add(new Pair(Long.parseLong(elements[0]), Long.parseLong(elements[1])));
        }

        // sort from lowest to highest end value (natan reference)
        List<Pair> sortedPairs = pairs.stream().sorted(Comparator.comparingLong(o -> o.lower)).toList();

        for (Pair pair : sortedPairs) {
            if (pair.upper >= highestId) {
                count += pair.upper - Math.max(pair.lower, highestId) + 1;
                highestId = pair.upper + 1;
            }
        }

        System.out.println("D5P2: " + count);
    }

    public record Pair(long lower, long upper) {}

}
