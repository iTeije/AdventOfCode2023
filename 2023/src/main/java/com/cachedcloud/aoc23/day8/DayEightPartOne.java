package com.cachedcloud.aoc23.day8;

import com.cachedcloud.aoc.common.FileReader;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayEightPartOne {

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


        int count = 0;
        Entry pointer = elements.get("AAA");
        for (char action : instructions.toCharArray()) {
            if (action == 'R') {
                pointer = elements.get(pointer.right);
            } else if (action == 'L') {
                pointer = elements.get(pointer.left);
            }

            count++;
            if (pointer.self.equals("ZZZ")) {
                break;
            }
        }

        System.out.println("Result (2023 D8P1): " + count);
    }

    @AllArgsConstructor
    private static class Entry {
        public final String self;
        public final String left;
        public final String right;
    }
}
