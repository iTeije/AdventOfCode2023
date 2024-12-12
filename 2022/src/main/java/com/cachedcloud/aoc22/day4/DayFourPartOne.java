package com.cachedcloud.aoc22.day4;

import com.cachedcloud.aoc.common.FileReader;
import java.util.List;

public class DayFourPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day4.txt");
        List<String> input = reader.getInputAsStrings();

        int count = 0;
        for (String line : input) {
            String[] ranges = line.split(",");
            Range firstRange = Range.from(ranges[0]);
            Range secondRange = Range.from(ranges[1]);

            if (firstRange.start >= secondRange.start && firstRange.end <= secondRange.end) {
                count++;
            } else if (secondRange.start >= firstRange.start && secondRange.end <= firstRange.end) {
                count++;
            }
        }

        System.out.println("Result (2022 D4P1): " + count);
    }

    private static class Range {
        public final int start;
        public final int end;

        public Range(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public static Range from(String serialized) {
            String[] data = serialized.split("-");
            return new Range(Integer.parseInt(data[0]), Integer.parseInt(data[1]));
        }
    }
}
