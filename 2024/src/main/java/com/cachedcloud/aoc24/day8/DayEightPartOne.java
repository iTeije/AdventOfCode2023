package com.cachedcloud.aoc24.day8;

import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.Timer;

import java.util.*;

public class DayEightPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day8.txt");
//        FileReader reader = new FileReader("example-input-day8.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();

        Map<Character, List<Coordinate>> antennas = new HashMap<>();
        Set<Coordinate> antiNodes = new HashSet<>();

        char[][] grid = GridUtil.createCharGrid(input);
        int yMax = grid.length;
        int xMax = grid[0].length;

        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                char at = grid[y][x];
                if (at == '.') continue;

                List<Coordinate> coordinates = antennas.computeIfAbsent(at, k -> new ArrayList<>());
                Coordinate current = new Coordinate(x, y);

                getAntiNodes(current, coordinates, antiNodes, xMax, yMax);
                coordinates.add(current);
            }
        }

        System.out.println("D8P1: " + antiNodes.size());
    }

    public static void getAntiNodes(Coordinate from, List<Coordinate> antennas, Set<Coordinate> antiNodes, int xMax, int yMax) {
        // No antinodes if there are less than 2 antennas of the same type
        if (antennas.isEmpty()) return;

        for (Coordinate coordinate : antennas) {
            // Determine horizontal and vertical distance
            int yDiff = from.y - coordinate.y;
            int xDiff = from.x - coordinate.x;

            // Create antinode on one side and check if it is in bounds of the grid
            int firstAntiNodeX = from.x + xDiff;
            int firstAntiNodeY = from.y + yDiff;
            if (firstAntiNodeX >= 0 && firstAntiNodeX < xMax && firstAntiNodeY >= 0 && firstAntiNodeY < yMax) {
                antiNodes.add(new Coordinate(firstAntiNodeX, firstAntiNodeY));
            }

            // Create antinode on the other side and check if it in bounds of the grid
            int secondAntiNodeX = coordinate.x - xDiff;
            int secondAntiNodeY = coordinate.y - yDiff;
            if (secondAntiNodeX >= 0 && secondAntiNodeX < xMax && secondAntiNodeY >= 0 && secondAntiNodeY < yMax) {
                antiNodes.add(new Coordinate(secondAntiNodeX, secondAntiNodeY));
            }
        }
    }
}
