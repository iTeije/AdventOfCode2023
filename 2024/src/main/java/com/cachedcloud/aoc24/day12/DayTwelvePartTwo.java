package com.cachedcloud.aoc24.day12;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.Direction;
import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.grid.CharGrid;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class DayTwelvePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day12.txt");
//        FileReader reader = new FileReader("example-input-day12.txt");

        char[][] input = GridUtil.createCharGrid(reader.getInputAsStrings());
        CharGrid grid = new CharGrid(input);

        LinkedList<Coordinate> queue = new LinkedList<>();
        Set<Coordinate> visited = new HashSet<>();

        // Every iteration of this mapping resembles a new region
        int result = grid.stream().filter(coordinate -> !visited.contains(coordinate)).mapToInt(coordinate -> {
            Set<Edge> visitedEdges = ConcurrentHashMap.newKeySet();
            AtomicInteger area = new AtomicInteger(0);

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
                        char neighbourChar = grid.charAt(neighbour);
                        if (grid.charAt(coordinate) == neighbourChar) {
                            queue.push(neighbour);
                        } else {
                            // Store edge
                            visitedEdges.add(new Edge(current, neighbour));
                        }
                    });
                }
            }

            // Filter out any edges that are part of the same side, except for one
            visitedEdges.stream().filter(visitedEdges::contains).forEach(edge -> {
                // Get relative direction from the two coordinates involved in the edge
                Direction direction = edge.from.directionTo(edge.to);

                // Function to remove all adjacent edges in a single direction
                Consumer<Direction> filter = heading -> {
                    int totalRemoved = 0;
                    while (visitedEdges.remove(new Edge(edge.from.relative(heading, 1 + totalRemoved), edge.to.relative(heading, 1 + totalRemoved)))) {
                        totalRemoved++;
                    }
                };

                // Run the function in both directions
                filter.accept(direction.turnLeft());
                filter.accept(direction.turnRight());
            });

            return area.get() * visitedEdges.size();
        }).sum();

        System.out.println("D12P1: " + result);
    }

    private record Edge(Coordinate from, Coordinate to) {}

}
