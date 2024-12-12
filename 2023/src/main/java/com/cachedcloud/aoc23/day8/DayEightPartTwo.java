package com.cachedcloud.aoc23.day8;

import com.cachedcloud.aoc.common.FileReader;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DayEightPartTwo {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day8.txt").getInputAsStrings();

        // Parse instructions
        String instructions = input.get(0).repeat(100);

        // Store elements
        Map<String, Entry> elements = new HashMap<>();
        input.subList(2, input.size()).forEach(entry -> {
            String elementName = entry.split(" = ")[0];
            String[] elementInstructions = entry.split(" = ")[1].replace("(", "")
                    .replace(")", "").split(", ");
            elements.put(elementName, new Entry(elementName, elementInstructions[0], elementInstructions[1]));
        });

        // Generate a list of path lenghts from all nodes that end with 'A'
        List<Integer> pathLenghts = elements.entrySet().stream().filter(mapEntry -> {
            return mapEntry.getKey().endsWith("A");
        }).map(mapEntry -> {
            Entry pointer = mapEntry.getValue();
            int count = 0;
            for (char action : instructions.toCharArray()) {
                if (action == 'R') {
                    pointer = elements.get(pointer.right);
                } else if (action == 'L') {
                    pointer = elements.get(pointer.left);
                }

                count++;
                if (pointer.self.endsWith("Z")) {
                    break;
                }
            }
            return count;
        }).sorted(Integer::compare).collect(Collectors.toList()); // not using Stream::toList because the return value is a immutable list

        // Get smallest number
        long total = pathLenghts.remove(0);
        // Calculate lcm for all combined path lengths
        for (int pathLength : pathLenghts) {
            total = getLcm(total, pathLength);
        }

        System.out.println("Result (2023 D8P2): " + total);
    }

    public static long getLcm(long firstNumber, int secondNumber) {
        long absHigherNumber = Math.max(firstNumber, secondNumber);
        long absLowerNumber = Math.min(firstNumber, secondNumber);

        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }

    @AllArgsConstructor
    private static class Entry {
        public final String self;
        public final String left;
        public final String right;
    }
}
