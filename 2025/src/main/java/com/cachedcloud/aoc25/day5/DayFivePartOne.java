package com.cachedcloud.aoc25.day5;

import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.FileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DayFivePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day5.txt");
//        FileReader reader = new FileReader("example-input-day5.txt");

        List<String> input = reader.getInputAsStrings();
        Set<Pair> pairs = new HashSet<>();

        boolean ranges = true;
        int count = 0;

        for (String line : input) {
            if (ranges) {
                if (line.isEmpty()) {
                    ranges = false;
                    continue;
                }
                String[] elements = line.split("-");
                pairs.add(new Pair(Long.parseLong(elements[0]), Long.parseLong(elements[1])));
            } else {
                long num = Long.parseLong(line);

                // Check if this long is in the range of one of the pairs
                if (pairs.stream().anyMatch(pair -> num >= pair.lower && num <= pair.upper)) {
                    count++;
                }
            }

        }

        System.out.println("D5P1: " + count);
    }

    public record Pair(long lower, long upper) {}
}
