package com.cachedcloud.aoc24.day14;

import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.grid.Grid;
import com.cachedcloud.aoc.location.Coordinate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DayFourteenPartTwo {

    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("input-day14.txt");
        int tall = 103;
        int wide = 101;

        // Get robots
        List<Robot> robots = new ArrayList<>();
        for (String line : reader.getInputAsStrings()) {
            String[] parts = line.split(" ");
            robots.add(new Robot(getCoordinate(parts[0]), getCoordinate(parts[1])));
        }

        int seconds = 0;

        whileLoop:
        while (true) {
            System.out.println("Testing " + seconds);
            Character[][] grid = GridUtil.createAndFillObject(wide, tall, '.');

            int index =0;
            // Calculate and print
            for (Robot robot : robots) {
                index++;
                int x = (robot.velocity.x * seconds + robot.start.x) % wide;
                int y = (robot.velocity.y * seconds + robot.start.y) % tall;
                if (x < 0) x += wide;
                if (y < 0) y += tall;
                grid[y][x] = '#';

                // Checking for adjacent robots
                if (index > 100 && y > 27 && y < 61 && x > 49) {
                    Grid<Character> utilGrid = new Grid<>(grid, '.');
                    Set<Coordinate> visited = new HashSet<>();
                    LinkedList<Coordinate> queue = new LinkedList<>();
                    queue.push(Coordinate.of(x, y));

                    while (!queue.isEmpty()) {
                        Coordinate current = queue.pop();
                        if (visited.add(current)) {
                            utilGrid.getNeighbouringCoordinates(current, false).forEach((dir, neighbour) -> {
                                char neighbourChar = utilGrid.valueAt(neighbour);
                                if (neighbourChar == '#') {
                                    queue.push(neighbour);
                                }
                            });
                        }
                    }

                    if (visited.size() > 50) {
                        System.out.println("Second " + seconds + " contains 50+ neighbouring robots.");
                        break whileLoop;
                    }
                }
            }

            seconds++;
        }
    }

    private static Coordinate getCoordinate(String input) {
        return Coordinate.of(input.substring(2));
    }

    private record Robot(Coordinate start, Coordinate velocity) {}

}
