package com.cachedcloud.aoc22.day5;

import com.cachedcloud.aoc.common.FileReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DayFivePartOne {

    public static void main(String[] unusedArgs) {
        List<String> crateInput = new FileReader("input-day5-stacks.txt").getInputAsStrings();
        String labelLine = crateInput.get(crateInput.size() - 1);

        Map<Integer, Integer> labelIndex = new HashMap<>(); // crate # -> character index
        int index = 0;
        for (char ch : labelLine.toCharArray()) {
            if (Character.isDigit(ch)) {
                labelIndex.put(Integer.parseInt(String.valueOf(ch)), index);
            }
            index++;
        }

        Map<Integer, List<Character>> crates = new HashMap<>();
        labelIndex.keySet().forEach(crate -> {
            crates.put(crate, new ArrayList<>());
        });

        for (int i = crateInput.size() - 2; i >= 0; i--) {
            String line = crateInput.get(i);
            labelIndex.forEach((crate, charIndex) -> {
                if (line.length() < charIndex) return;

                char charAt = line.charAt(charIndex);
                if (charAt != ' ') {
                    crates.get(crate).add(charAt);
                }
            });
        }

        // For debug purposes
        crates.forEach((crate, list) -> {
            System.out.println("crate " + crate + ": " + Arrays.toString(list.toArray()));
        });

        // Read movement data
        List<String> movementInput = new FileReader("input-day5.txt").getInputAsStrings();
        List<Move> moves = new LinkedList<>();
        for (String line : movementInput) {
            String[] data = line.split(" ");
            for (int i = 0; i < Integer.parseInt(data[1]); i++) {
                moves.add(new Move(Integer.parseInt(data[3]), Integer.parseInt(data[5])));
            }
        }

        moves.forEach(move -> {
            List<Character> fromCrate = crates.get(move.from);
            char ch = fromCrate.remove(fromCrate.size() - 1);

            crates.get(move.to).add(ch);
        });

        // Print highest crate on each stack
        StringBuilder builder = new StringBuilder("");
        crates.forEach((crate, list) -> {
            builder.append(list.get(list.size() - 1));
        });

        System.out.println("Result (2022 D5P1): " + builder);
    }

    static class Move {
        public final int from;
        public final int to;
        public Move(int from, int to) {
            this.from = from;
            this.to = to;
        }
    }
}
