package com.cachedcloud.aoc22.day6;

import com.cachedcloud.aoc.common.FileReader;

import java.util.Set;
import java.util.stream.Collectors;

public class DaySixPartTwo {

    public static void main(String[] args) {
        // Parse the single line of input
        String input = new FileReader("input-day6.txt").getInputAsStrings().get(0);
        int distinctConsecutiveCount = 14;

        for (int i = distinctConsecutiveCount; i < input.length(); i++) {
            String sub = input.substring(i - distinctConsecutiveCount, i);

            Set<Character> characters = sub.chars()
                    .mapToObj(ch -> (char) ch)
                    .collect(Collectors.toSet());

            if (characters.size() == distinctConsecutiveCount) {
                System.out.println("Result (2022 D6P2): " + i);
                break;
            }
        }
    }
}
