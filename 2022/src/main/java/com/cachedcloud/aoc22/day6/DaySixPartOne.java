package com.cachedcloud.aoc22.day6;

import com.cachedcloud.aoc.common.FileReader;

import java.util.Set;
import java.util.stream.Collectors;

public class DaySixPartOne {

    public static void main(String[] args) {
        // Parse the single line of input
        String input = new FileReader("input-day6.txt").getInputAsStrings().get(0);

        for (int i = 4; i < input.length(); i++) {
            String sub = input.substring(i - 4, i);

            Set<Character> characters = sub.chars()
                    .mapToObj(ch -> (char) ch)
                    .collect(Collectors.toSet());

            if (characters.size() == 4) {
                System.out.println("Result (2022 D6P1): " + i);
                break;
            }
        }
    }
}
