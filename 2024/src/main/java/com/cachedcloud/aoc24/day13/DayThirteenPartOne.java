package com.cachedcloud.aoc24.day13;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.location.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class DayThirteenPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day13.txt");
//        FileReader reader = new FileReader("example-input-day13.txt");

        // 3 tokens for A, 1 for B
        List<String> input = reader.getInputAsStrings();
        List<Game> games = new ArrayList<>();

        // Parse each game
        for (int i = 0; i < input.size(); i+=4) {
            games.add(new Game(
                    parseCoordinate(input.get(i).substring(10), "\\+"),
                    parseCoordinate(input.get(i+1).substring(10), "\\+"),
                    parseCoordinate(input.get(i+2).substring(7), "=")
            ));
        }

        // Do the maths and sum the results
        int count = games.stream().map(game -> {
            int aAmount = (game.buttonB.y * game.buttonA.x) - (game.buttonB.x * game.buttonA.y);
            float aPresses = (float) ((game.buttonB.y * game.target.x) - (game.buttonB.x * game.target.y)) / aAmount;
            float bPresses = (game.target.y - game.buttonA.y * aPresses) / game.buttonB.y;

            // Each button can be pressed no more than 100 times
            if (aPresses > 100 || bPresses > 100) return 0;
            // The number of presses must not be a floating-point number
            if (aPresses % 1 != 0 || bPresses % 1 != 0) return 0;

            // Calculate token amounts
            return (int) (aPresses * 3 + bPresses);
        }).mapToInt(Integer::intValue).sum();

        System.out.println("D13P1: " + count);
    }

    // Stripped input: X{delimiter}number, Y{delimiter}number
    private static Coordinate parseCoordinate(String input, String delimiter) {
        String[] xy = input.split(", ");
        return Coordinate.of(Integer.parseInt(xy[0].split(delimiter)[1]), Integer.parseInt(xy[1].split(delimiter)[1]));
    }

    private record Game(Coordinate buttonA, Coordinate buttonB, Coordinate target) {}

}