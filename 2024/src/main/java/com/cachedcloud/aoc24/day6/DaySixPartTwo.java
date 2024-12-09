package com.cachedcloud.aoc24.day6;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.Timer;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

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

    public static AtomicInteger counter = new AtomicInteger(0);
    public static ExecutorService executors = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day6.txt");
//        FileReader reader = new FileReader("example-input-day6.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();
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

        Set<Coordinate> coordinatesToCheck = new DaySixPartOne(input, grid).visitedCoordinates();
        List<Future<Void>> futures = new ArrayList<>();

        for (Coordinate coordinate : coordinatesToCheck) {
            Coordinate finalDefaultGuard = defaultGuard;
            futures.add(executors.submit(() -> {
                processCoordinate(grid, coordinate.x, coordinate.y, finalDefaultGuard);
                return null;
            }));
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }

        executors.shutdown();

        Timer.finish();
        System.out.println(counter.get());
    }

    public static void processCoordinate(char[][] grid, int x, int y, Coordinate defaultGuard) {
        System.out.println("Processing coordinate (" + x + ", " + y + ")");
        char[][] gridCopy = Arrays.stream(grid)
                .map(char[]::clone)
                .toArray(char[][]::new);

        Direction direction = Direction.NORTH;
        gridCopy[y][x] = '#';

//        BitSet bitSet = new BitSet(68765);
        BitSet bitSet = new BitSet(67604);

        Entry entry;
        Coordinate guard = defaultGuard;

        try {
            while ((entry = move(gridCopy, guard, bitSet, direction)) != null) {
                guard = entry.coordinate;
                direction = entry.approachingDirection;
            }
            counter.incrementAndGet();
        } catch (IndexOutOfBoundsException ignored) {}
    }

    public static Entry move(char[][] grid, Coordinate guard, BitSet bitSet, Direction direction) throws IndexOutOfBoundsException {
        // Handle turns
        if (getCharInDirection(grid, direction, guard) == '#') {
            Entry entry = new Entry(new Coordinate(guard.x + direction.dx, guard.y + direction.dy), direction);
            int hashCode = entry.hashCode();
            if (bitSet.get(hashCode)) return null;
            bitSet.set(hashCode);

            direction = turn(direction);
            return new Entry(guard, direction);
        }

        return new Entry(new Coordinate(guard.x + direction.dx, guard.y + direction.dy), direction);
    }

    public static char getCharInDirection(char[][] grid, Direction direction, Coordinate fromCurrent) {
        return grid[fromCurrent.y + direction.dy][fromCurrent.x + direction.dx];
    }

    public static Direction turn(Direction direction) {
        return switch (direction) {
            case NORTH -> Direction.EAST;
            case EAST -> Direction.SOUTH;
            case SOUTH -> Direction.WEST;
            case WEST -> Direction.NORTH;
        };
    }

    @AllArgsConstructor
    public static class Entry {
        public final Coordinate coordinate;
        public final Direction approachingDirection;

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Entry entry) {
                return entry.coordinate.equals(coordinate) && entry.approachingDirection == approachingDirection;
            } else return false;
        }

        @Override
        public int hashCode() {
            return (coordinate.y * 130 + coordinate.x) * 4 + approachingDirection.ordinal();
        }
    }

}
