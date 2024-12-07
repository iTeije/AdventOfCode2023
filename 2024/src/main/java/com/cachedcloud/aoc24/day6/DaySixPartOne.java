package com.cachedcloud.aoc24.day6;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.Timer;
import lombok.AllArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaySixPartOne {

    @AllArgsConstructor
    public enum Direction {
        NORTH(-1, 0),
        EAST(0, 1),
        SOUTH(1, 0),
        WEST(0, -1);

        public final int dy;
        public final int dx;
    }

    private final List<String> input;
    private final char[][] grid;
    private Coordinate guard;

    public DaySixPartOne(List<String> input, char[][] grid) {
        this.input = input;
        this.grid = grid;

        // Determine guard starting position
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            int index = line.indexOf('^');
            if (index != -1) {
                this.guard = new Coordinate(index, y);
                break;
            }
        }
    }

    public Set<Coordinate> visitedCoordinates() {
        // Default direction
        Set<Coordinate> exploredCoordinates = new HashSet<>();
        exploredCoordinates.add(guard);

        Coordinate coordinate;
        while ((coordinate = move(grid, guard)) != null) {
            exploredCoordinates.add(coordinate);
            guard = coordinate;
        }
        return exploredCoordinates;
    }

    public static Direction direction = Direction.NORTH;

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day6.txt");
//        FileReader reader = new FileReader("example-input-day6.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();

        char[][] grid = GridUtil.createCharGrid(input);
        System.out.println("D6P1: " + new DaySixPartOne(input, grid).visitedCoordinates().size());

        Timer.finish();
    }

    public static Coordinate move(char[][] grid, Coordinate guard) {
        // Handle turns
        try {
            if (getCharInDirection(grid, direction, guard) == '#') {
                turn();
                return guard;
            }
        } catch (IndexOutOfBoundsException exception) {
            return null;
        }

        return new Coordinate(guard.x + direction.dx, guard.y + direction.dy);
    }

    public static char getCharInDirection(char[][] grid, Direction direction, Coordinate fromCurrent) {
        return grid[fromCurrent.y + direction.dy][fromCurrent.x + direction.dx];
    }

    public static void turn() {
        if (direction == Direction.NORTH) {
            direction = Direction.EAST;
        } else if (direction == Direction.EAST) {
            direction = Direction.SOUTH;
        } else if (direction == Direction.SOUTH) {
            direction = Direction.WEST;
        } else if (direction == Direction.WEST) {
            direction = Direction.NORTH;
        }
    }

}
