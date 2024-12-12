package com.cachedcloud.aoc24.day10;

import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.GridUtil;

import java.util.ArrayList;
import java.util.List;

public class DayTenPartTwo {

    public static int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day10.txt");
//        FileReader reader = new FileReader("example-input-day10.txt");
        List<String> input = reader.getInputAsStrings();

        int[][] grid = GridUtil.createIntGrid(input);
        int count = 0;

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                // Find zeroes/trailheads
                if (grid[y][x] != 0) continue;

                // Start pathfinding
                count += getScore(grid, x, y, 0).size();
            }
        }

        System.out.println("D10P2: " + count);
    }

    public static List<Coordinate> getScore(int[][] grid, int x, int y, int currentHeight) {
        // Use a list so that every separate branch will register its coordinate instead of collecting all branches
        // that lead to the same 9-coordinate as a single path.
        List<Coordinate> coordinates = new ArrayList<>();

        // Test each direction
        for (int[] direction : directions) {
            int targetX = x + direction[0];
            int targetY = y + direction[1];

            // Get and validate height at neighbouring heights
            int height = getHeight(grid, targetX, targetY);
            if (height == -1) continue;
            if (height != currentHeight + 1) continue;

            // If target coordinate is at height 9, add it to the set
            if (height == 9) {
                coordinates.add(new Coordinate(targetX, targetY));
                continue;
            }

            // Add all coordinates from deeper branches
            coordinates.addAll(getScore(grid, targetX, targetY, height));
        }

        return coordinates;
    }

    public static int getHeight(int[][] grid, int x, int y) {
        if (x < 0 || x >= grid[0].length || y < 0 || y >= grid.length) return -1;
        return grid[y][x];
    }
}