package com.cachedcloud.aoc23.day2;

import com.cachedcloud.aoc.FileReader;

import java.util.List;

public class DayTwoPartOne {

    public static void main(String[] args) {
        List<String> input = new FileReader("input-day2.txt").getInputAsStrings();

        int totalIds = 0;

        main:
        for (String line : input) {
            // Remove Game prefix from string
            line = line.replace("Game ", "");
            String[] lineSplit = line.split(":");

            // Get game id
            int gameId = Integer.parseInt(lineSplit[0]);

            // Loop through hands
            String[] hands = lineSplit[1].split(";");
            for (String hand : hands) {
                // Split up the cube data
                String[] cubes = hand.split(",");
                for (String cubeData : cubes) {
                    // Remove facing space character
                    cubeData = cubeData.substring(1);
                    // Split up into an integer and a string color
                    String[] colorAndAmount = cubeData.split(" ");
                    int amount = Integer.parseInt(colorAndAmount[0]);
                    String color = colorAndAmount[1];

                    // Match color and amounts against their respective limits
                    if (color.equals("red") && amount > 12) continue main;
                    if (color.equals("green") && amount > 13) continue main;
                    if (color.equals("blue") && amount > 14) continue main;
                }
            }

            // Hands have passed, add game id to total
            totalIds += gameId;
        }

        System.out.println("Result (2023 D2P1): " + totalIds);
    }
}
