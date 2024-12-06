package com.cachedcloud.aoc24.day6;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;
import lombok.AllArgsConstructor;

import java.util.*;

public class DaySixPartTwo {

    @AllArgsConstructor
    public enum Direction {
        NORTH(-1, 0),
        EAST(0, 1),
        SOUTH(1, 0),
        WEST(0, -1);

        public final int dy;
        public final int dx;
    }

    public static Direction direction = Direction.NORTH;

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day6.txt");
//        FileReader reader = new FileReader("example-input-day6.txt");
        List<String> input = reader.getInputAsStrings();
        char[][] grid = GridUtil.createCharGrid(input);

        Coordinate defaultGuard = null;

        // Determine guard starting position
        for (int y = 0; y < input.size(); y++) {
            String line = input.get(y);
            int index = line.indexOf('^');
            if (index != -1) {
                defaultGuard = new Coordinate(index, y);
                break;
            }
        }

        int count = 0;
        for (int y = 0; y < grid.length; y++) {
            System.out.println("Processing line " + y);
            for (int x = 0; x < grid[y].length; x++) {
                char[][] gridCopy = Arrays.stream(grid)
                        .map(char[]::clone)
                        .toArray(char[][]::new);

                char c = gridCopy[y][x];
                if (c == '^') continue;
                if (c == '#') continue;

                direction = Direction.NORTH;
                gridCopy[y][x] = '#';

                Set<Entry> entries = new HashSet<>();

                Coordinate coordinate;
                Coordinate guard = defaultGuard;

                try {
                    while ((coordinate = move(gridCopy, guard, entries)) != null) {
                        guard = coordinate;
                    }
                    count++;
                } catch (IndexOutOfBoundsException ignored) {}
            }
        }

        System.out.println(count);
    }

    public static Coordinate move(char[][] grid, Coordinate guard, Set<Entry> entries) throws IndexOutOfBoundsException {
        // Handle turns
        if (getCharInDirection(grid, direction, guard) == '#') {
            Entry entry = new Entry(new Coordinate(guard.x + direction.dx, guard.y + direction.dy), direction);
            if (entries.contains(entry)) return null;
            entries.add(entry);

            turn();
            return guard;
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

    @AllArgsConstructor
    public static class Entry {
        public final Coordinate obstruction;
        public final Direction approachingDirection;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry entry) {
                return entry.obstruction.equals(obstruction) && entry.approachingDirection == approachingDirection;
            } else return false;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
    }

}
