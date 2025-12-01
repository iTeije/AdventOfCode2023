package com.cachedcloud.aoc24.day16;

import com.cachedcloud.aoc.ParseUtil;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;
import com.cachedcloud.aoc.grid.Grid;
import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.location.Direction;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DaySixteenPartTwo {

    public static void main(String[] args) {
        Timer.start();
//        FileReader reader = new FileReader("input-day16.txt");
        FileReader reader = new FileReader("example-input-day16.txt");

        // Initialize grid and default values
        Grid<Character> grid = ParseUtil.createCharGrid(reader.getInputAsStrings());
        Coordinate endCoordinate = grid.findFirst('E');
        Coordinate startCoordinate = grid.findFirst('S');

        System.out.println("D16P2: " + pathFindNew(grid, startCoordinate, endCoordinate));
        Timer.finish();
    }

    private static int pathFindNew(Grid<Character> grid, Coordinate startCoordinate, Coordinate endCoordinate) {
        Queue<State> queue = new LinkedList<>();
        Map<Coordinate, Score> bestScores = new HashMap<>();
        Score bestScore = new Score(Integer.MAX_VALUE, 0);

        // Initialize queue from starting coordinate
        queue.add(new State(startCoordinate, Direction.EAST, new Score(1, 1)));

        // Run queue as long as it is not empty
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            Coordinate currentCoordinate = currentState.coordinate;

            // If we reached the end coordinate, update the best score
            if (currentCoordinate.equals(endCoordinate)) {
                System.out.println("Reached end with length " + currentState.score.length + " and score " + currentState.score.score);
                if (bestScore.score > currentState.score.score) {
                    bestScore = currentState.score;
                }
                continue;
            }

            // Loop through neighbours
            grid.getNeighbours(currentCoordinate, false).forEach((direction, character) -> {
                // ignore walls
                if (character == '#') return;

                Coordinate next = currentCoordinate.relative(direction, 1);
                int localScore = currentState.score.score + 1 + (direction != currentState.direction ? 1000 : 0);

                // Check if a score for this path exists and if so, make sure its higher/worse than the current running score
                if (!bestScores.containsKey(next) || localScore < bestScores.get(next).score) {
                    Score score = new Score(localScore, currentState.score.length + 1);
                    bestScores.put(next, score);

//                    System.out.println("adding score with " + score.score + " and length " + score.length);
                    queue.add(new State(next, direction, score));
                }
            });
        }

        return bestScore.length;

    }

    private record State(Coordinate coordinate, Direction direction, Score score) {}

    @Setter
    @AllArgsConstructor
    private static class Score {
        int score;
        int length;
    }

}
