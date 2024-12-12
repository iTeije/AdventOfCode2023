package com.cachedcloud.aoc22.day8;

import com.cachedcloud.aoc.common.FileReader;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class DayEightPartOne {

    public static void main(String[] args) {
        List<String> input = new FileReader("input-day8.txt").getInputAsStrings();

        // Count horizontally first
        Set<Coordinate> coordinates = new HashSet<>();
        count(input, coordinates, false);

        System.out.println(coordinates.size());

        // Generate column lists
        List<String> verticalInput = new ArrayList<>();
        for (int i = 0; i < input.get(0).length(); i++) {
            StringBuilder builder = new StringBuilder("");
            for (String s : input) {
                builder.append(s.charAt(i));
            }
            verticalInput.add(builder.toString());
        }

        // Count vertically
        count(verticalInput, coordinates, true);

        System.out.println("Result (2022 D8P1): " + coordinates.size());
    }

    public static void count(List<String> input, Set<Coordinate> coordinates, boolean vertical) {
        int yIndex = 0;
        // Check horizontally
        for (String row : input) {
            // Get list of numeric values for the input string
            List<Integer> integerList = getNumericValues(row);

            // Check from left to right/top to bottom
            int currentHighest = -1;
            int xIndex = 0;
            for (int tree : integerList) {
                if (tree > currentHighest) {
                    currentHighest = tree;
                    coordinates.add(vertical ? new Coordinate(yIndex, xIndex) : new Coordinate(xIndex, yIndex));
                }
                xIndex++;
            }

            // Reverse the list, so I can check from right to left or bottom to top
            Collections.reverse(integerList);
            currentHighest = -1;
            xIndex = integerList.size() - 1;
            for (int tree : integerList) {
                if (tree > currentHighest) {
                    currentHighest = tree;
                    coordinates.add(vertical ? new Coordinate(yIndex, xIndex) : new Coordinate(xIndex, yIndex));
                }
                xIndex--;
            }
            yIndex++;
        }
    }

    public static List<Integer> getNumericValues(String input) {
        List<Integer> integerList = new ArrayList<>();
        for (char ch : input.toCharArray()) {
            integerList.add(Character.getNumericValue(ch));
        }
        return integerList;
    }

    @AllArgsConstructor
    static class Coordinate {
        public final int x;
        public final int y;

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Coordinate other)) return false;
            return other.x == this.x && other.y == this.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
