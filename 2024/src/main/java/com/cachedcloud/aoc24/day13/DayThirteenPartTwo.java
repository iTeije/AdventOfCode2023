package com.cachedcloud.aoc24.day13;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.location.BigCoordinate;

import java.util.ArrayList;
import java.util.List;

public class DayThirteenPartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day13.txt");
//        FileReader reader = new FileReader("example-input-day13.txt");

        // 3 tokens for A, 1 for B
        List<String> input = reader.getInputAsStrings();
        List<Game> games = new ArrayList<>();

        // Parse each game
        for (int i = 0; i < input.size(); i+=4) {
            games.add(new Game(
                    parseCoordinate(input.get(i).substring(10), "\\+", false),
                    parseCoordinate(input.get(i+1).substring(10), "\\+", false),
                    parseCoordinate(input.get(i+2).substring(7), "=", true)
            ));
        }

        // Do the maths and sum the results
        long count = games.stream().map(game -> {
            long aAmount = (game.buttonB.y * game.buttonA.x) - (game.buttonB.x * game.buttonA.y);
            long aPresses = ((game.buttonB.y * game.target.x) - (game.buttonB.x * game.target.y)) / aAmount;
            long bPresses = (game.target.y - game.buttonA.y * aPresses) / game.buttonB.y;

            // Validate output
            if (aPresses * game.buttonA.x + bPresses * game.buttonB.x != game.target.x ||
                    aPresses * game.buttonA.y + bPresses * game.buttonB.y != game.target.y) return 0;

            // Calculate token amount
            return aPresses * 3 + bPresses;
        }).mapToLong(Number::longValue).sum();

        System.out.println("D13P2: " + count);
    }

    // Stripped input: X{delimiter}number, Y{delimiter}number
    private static BigCoordinate parseCoordinate(String input, String delimiter, boolean isPrize) {
        String[] xy = input.split(", ");
        return BigCoordinate.of(
                Integer.parseInt(xy[0].split(delimiter)[1]) + (isPrize ? 10000000000000L : 0),
                Integer.parseInt(xy[1].split(delimiter)[1]) + + (isPrize ? 10000000000000L : 0)
        );
    }

    private record Game(BigCoordinate buttonA, BigCoordinate buttonB, BigCoordinate target) {}

}
