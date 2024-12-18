package com.cachedcloud.aoc24.day12;

import com.cachedcloud.aoc.ParseUtil;
import com.cachedcloud.aoc.grid.Grid;
import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.common.FileReader;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DayTwelvePartOne {

    public static void main(String[] args) {
//        FileReader reader = new FileReader("input-day12.txt");
        FileReader reader = new FileReader("example-input-day12.txt");

        Grid<Character> grid = ParseUtil.createCharGrid(reader.getInputAsStrings());

        LinkedList<Coordinate> queue = new LinkedList<>();
        Set<Coordinate> visited = new HashSet<>();

        // Every iteration of this mapping resembles a new region
        int result = grid.stream().filter(coordinate -> !visited.contains(coordinate)).mapToInt(coordinate -> {
            AtomicInteger area = new AtomicInteger(0);
            AtomicInteger perimeter = new AtomicInteger(0);

            // Queue current coordinate
            queue.push(coordinate);

            // Run floodfill
            while (!queue.isEmpty()) {
                Coordinate current = queue.pop();
                // Mark current coordinate as visited and proceed if it was not previously visited
                if (visited.add(current)) {
                    // Increment area
                    area.incrementAndGet();

                    // Get neighbouring coordinates
                    grid.getNeighbouringCoordinates(current).forEach((direction, neighbour) -> {
                        // Get garden plot type and check if it is the type that we are looking for
                        char neighbourChar = grid.valueAt(neighbour);
                        if (grid.valueAt(coordinate) == neighbourChar) {
                            queue.push(neighbour);
                        } else {
                            // Increase perimeter size
                            perimeter.incrementAndGet();
                        }
                    });
                }
            }

            return area.get() * perimeter.get();
        }).sum();

        System.out.println("D12P1: " + result);
    }
}