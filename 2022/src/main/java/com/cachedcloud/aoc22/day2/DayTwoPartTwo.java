package com.cachedcloud.aoc22.day2;

import com.cachedcloud.aoc.common.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayTwoPartTwo {

    private static final Map<Character, Integer> choicePointMappings = new HashMap<>() {{
        put('X', 1);
        put('Y', 2);
        put('Z', 3);
    }};

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day2.txt");
        List<String> input = reader.getInputAsStrings();

        int points = 0;

        for (String str : input) {
            String[] arguments = str.split(" ");
            OpponentChoice opponentChoice = OpponentChoice.valueOf(arguments[0]);
            points += opponentChoice.getPoints(Tristate.valueOf(arguments[1]));
        }

        System.out.println("Result (2022 D2P2): " + points);
    }

    enum OpponentChoice {
        A('Y', 'X', 'Z'),
        B('Z', 'Y', 'X'),
        C('X', 'Z', 'Y');

        private final char winCondition;
        private final char drawCondition;
        private final char loseCondition;

        OpponentChoice(char winCondition, char drawCondition, char loseCondition) {
            this.winCondition = winCondition;
            this.drawCondition = drawCondition;
            this.loseCondition = loseCondition;
        }

        public int getPoints(Tristate state) {
            if (state == Tristate.Z) {
                return choicePointMappings.get(winCondition) + 6;
            } else if (state == Tristate.X) {
                return choicePointMappings.get(loseCondition);
            } else return choicePointMappings.get(drawCondition) + 3;
        }


    }

    enum Tristate {
        X,
        Y,
        Z
    }

}
