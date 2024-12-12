package com.cachedcloud.aoc22.day2;

import com.cachedcloud.aoc.common.FileReader;

import java.util.List;

public class DayTwoPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day2.txt");
        List<String> input = reader.getInputAsStrings();

        int points = 0;

        for (String str : input) {
            String[] arguments = str.split(" ");
            Choice myChoice = Choice.valueOf(arguments[1]);

            points += myChoice.getResult(arguments[0].charAt(0));
            points += myChoice.points;
        }

        System.out.println("Result (2022 D2P1): " + points);
    }

    enum Choice {
        X( 'C', 'A', 1),
        Y('A', 'B', 2),
        Z('B', 'C', 3);

        private final char beats;
        private final char equivalent;
        private final int points;

        Choice(char beats, char equivalent, int points) {
            this.beats = beats;
            this.equivalent = equivalent;
            this.points = points;
        }

        public int getResult(char opponentChoice) {
            if (beats == opponentChoice) return 6;
            if (equivalent == opponentChoice) return 3;
            return 0;
        }
    }
}
