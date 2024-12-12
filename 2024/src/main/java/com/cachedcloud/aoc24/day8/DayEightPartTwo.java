package com.cachedcloud.aoc24.day8;

import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.Timer;

import java.util.*;

public class DayEightPartTwo {

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

        // Print grid with antinodes (VISUALIZATION ONLY)
        for (Coordinate c : antiNodes) {
            grid[c.y][c.x] = '#';
        }
        GridUtil.print(grid);

        System.out.println("D8P1: " + antiNodes.size());
    }

    public static void getAntiNodes(Coordinate from, List<Coordinate> antennas, Set<Coordinate> antiNodes, int xMax, int yMax) {
        // No antinodes if there are less than 2 antennas of the same type
        if (antennas.isEmpty()) return;
        // It is now confirmed that there are two antennas of the same type. During the first iteration of the following loop,
        // the first element that was initially skipped will be added to the antinodes set.
        if (antennas.size() == 1) antiNodes.add(antennas.get(0));
        antiNodes.add(from);

        for (Coordinate coordinate : antennas) {
            // Determine horizontal and vertical distance
            int yDiff = from.y - coordinate.y;
            int xDiff = from.x - coordinate.x;

            // Create antinodes on one side until it is out of bounds (with the same distance between each)
            int firstAntiNodeX = from.x + xDiff;
            int firstAntiNodeY = from.y + yDiff;
            while (firstAntiNodeX >= 0 && firstAntiNodeX < xMax && firstAntiNodeY >= 0 && firstAntiNodeY < yMax) {
                antiNodes.add(new Coordinate(firstAntiNodeX, firstAntiNodeY));
                firstAntiNodeX += xDiff;
                firstAntiNodeY += yDiff;
            }

            // Do the same on the other side
            int secondAntiNodeX = coordinate.x - xDiff;
            int secondAntiNodeY = coordinate.y - yDiff;
            while (secondAntiNodeX >= 0 && secondAntiNodeX < xMax && secondAntiNodeY >= 0 && secondAntiNodeY < yMax) {
                antiNodes.add(new Coordinate(secondAntiNodeX, secondAntiNodeY));
                secondAntiNodeX -= xDiff;
                secondAntiNodeY -= yDiff;
            }
        }
    }

}
