package com.cachedcloud.aoc24.day16;

import com.cachedcloud.aoc.ParseUtil;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;
import com.cachedcloud.aoc.grid.Grid;
import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.location.Direction;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class DaySixteenPartOne {

    public static void main(String[] args) {
        Timer.start();
//        FileReader reader = new FileReader("input-day16.txt");
        FileReader reader = new FileReader("example-input-day16.txt");

        // Initialize grid and default values
        Grid<Character> grid = ParseUtil.createCharGrid(reader.getInputAsStrings());
        Coordinate endCoordinate = grid.findFirst('E');
        Coordinate startCoordinate = grid.findFirst('S');

        System.out.println("D16P1: " + pathFindNew(grid, startCoordinate, endCoordinate));
        Timer.finish();
    }

    private static int pathFindNew(Grid<Character> grid, Coordinate startCoordinate, Coordinate endCoordinate) {
        Queue<State> queue = new LinkedList<>();
        Map<Coordinate, Integer> bestScores = new HashMap<>();
        int bestScore = Integer.MAX_VALUE;

        // Initialize queue from starting coordinate
        queue.add(new State(startCoordinate, Direction.EAST, 0));

        // Run queue as long as it is not empty
        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            Coordinate currentCoordinate = currentState.coordinate;

            // If we reached the end coordinate, update the best score
            if (currentCoordinate.equals(endCoordinate)) {
                bestScore = Math.min(bestScore, currentState.score);
                continue;
            }

            // Loop through neighbours
            grid.getNeighbours(currentCoordinate, false).forEach((direction, character) -> {
                // ignore walls
                if (character == '#') return;

                Coordinate next = currentCoordinate.relative(direction, 1);
                int localScore = currentState.score + 1;

                if (direction != currentState.direction) localScore += 1000;

                // Check if a score for this path exists and if so, make sure its higher/worse than the current running score
                if (!bestScores.containsKey(next) || localScore < bestScores.get(next)) {
                    bestScores.put(next, localScore);
                    queue.add(new State(next, direction, localScore));
                }
            });
        }

        return bestScore == Integer.MAX_VALUE ? -1 : bestScore;

    }

    private record State(Coordinate coordinate, Direction direction, int score) {}
}