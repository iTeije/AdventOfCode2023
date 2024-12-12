package com.cachedcloud.aoc23.day2;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayTwoPartTwo {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day2.txt").getInputAsStrings();

        int powerSum = 0;

        for (String line : input) {
            // Remove Game prefix from string
            line = line.replace("Game ", "");
            String[] lineSplit = line.split(":");

            int minRed = 1;
            int minGreen = 1;
            int minBlue = 1;

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
                    if (color.equals("red") && amount > minRed) minRed = amount;
                    if (color.equals("green") && amount > minGreen) minGreen = amount;
                    if (color.equals("blue") && amount > minBlue) minBlue = amount;
                }
            }

            // Hands have passed, add game id to total
            powerSum += (minRed * minGreen * minBlue);
        }

        System.out.println("Result (2023 D2P2): " + powerSum);
    }
}
