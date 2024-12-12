package com.cachedcloud.aoc22.day8;

import com.cachedcloud.aoc.common.FileReader;

import java.util.ArrayList;
import java.util.List;

public class DayEightPartTwo {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day8.txt").getInputAsStrings();
        List<List<Integer>> parsedInput = input.stream().map(DayEightPartTwo::getNumericValues).toList();

        int lowerBound = 0;
        int xUpperBound = input.get(0).length();
        int yUpperBound = input.size();

        int highestScenicScore = 0;

        for (int x = 0; x < input.get(0).length(); x++) {
            for (int y = 0; y < input.size(); y++) {
                // X and Y are the coordinates of the tree
                int treeHeight = parsedInput.get(y).get(x);

                // Get distance to the right
                int distRight = 0;
                for (int runX = x + 1; runX < xUpperBound; runX++) {
                    distRight++;
                    if (parsedInput.get(y).get(runX) >= treeHeight) break;
                }

                // Get distance to the left
                int distLeft = 0;
                for (int runX = x - 1; runX >= lowerBound; runX--) {
                    distLeft++;
                    if (parsedInput.get(y).get(runX) >= treeHeight) break;
                }

                // Get distance up
                int distUp = 0;
                for (int runY = y - 1; runY >= lowerBound; runY--) {
                    distUp++;
                    if (parsedInput.get(runY).get(x) >= treeHeight) break;
                }

                // Get distance down
                int distDown = 0;
                for (int runY = y + 1; runY < yUpperBound; runY++) {
                    distDown++;
                    if (parsedInput.get(runY).get(x) >= treeHeight) break;
                }

                // Calculate score
                int score = distRight * distLeft * distUp * distDown;
                if (score > highestScenicScore) {
                    highestScenicScore = score;
                    System.out.println("New best score is now at coordinate x=" + x + ", y=" + y + " (height: " + treeHeight + ") with a score of " + score
                            + " (r:" + distRight + ", l:" + distLeft + ", u:" + distUp + ", d:" + distDown + ")");
                }
            }
        }

        System.out.println("Result (2022 D8P2): " + highestScenicScore);
    }

    public static List<Integer> getNumericValues(String input) {
        List<Integer> integerList = new ArrayList<>();
        for (char ch : input.toCharArray()) {
            integerList.add(Character.getNumericValue(ch));
        }
        return integerList;
    }
}
