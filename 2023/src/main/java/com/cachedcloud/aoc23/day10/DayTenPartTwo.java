package com.cachedcloud.aoc23.day10;

import com.cachedcloud.aoc.FileReader;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class DayTenPartTwo {

    private static final Map<Character, Pipe> PIPES = new HashMap<>() {{
        put('F', new Pipe(Direction.EAST, Direction.SOUTH, PipeType.CORNER));
        put('L', new Pipe(Direction.NORTH, Direction.EAST, PipeType.CORNER));
        put('J', new Pipe(Direction.WEST, Direction.NORTH, PipeType.CORNER));
        put('7', new Pipe(Direction.WEST, Direction.SOUTH, PipeType.CORNER));
        put('|', new Pipe(Direction.NORTH, Direction.SOUTH, PipeType.UP));
        put('-', new Pipe(Direction.WEST, Direction.EAST, PipeType.FLAT));
    }};

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day10.txt").getInputAsStrings();

        Coordinate startCoordinate = null;
        Coordinate current = null;

        // Look for start coordinate
        int yIndex = 0;
        for (String line : input) {
            if (line.contains("S")) {
                startCoordinate = current = new Coordinate(line.indexOf("S"), yIndex, null);
                break;
            }
            yIndex++;
        }

        // Find first pipe
        if (getPipe(input, Coordinate.of(startCoordinate.x + 1, startCoordinate.y)).allowsPipeFrom(Direction.WEST)) {
            current = Coordinate.of(startCoordinate.x + 1, startCoordinate.y, Direction.WEST);
        } else if (getPipe(input, Coordinate.of(startCoordinate.x - 1, startCoordinate.y)).allowsPipeFrom(Direction.EAST)) {
            current = Coordinate.of(startCoordinate.x - 1, startCoordinate.y, Direction.EAST);
        } else if (getPipe(input, Coordinate.of(startCoordinate.x, startCoordinate.y + 1)).allowsPipeFrom(Direction.SOUTH)) {
            current = Coordinate.of(startCoordinate.x, startCoordinate.y + 1, Direction.SOUTH);
        } else if (getPipe(input, Coordinate.of(startCoordinate.x, startCoordinate.y - 1)).allowsPipeFrom(Direction.NORTH)) {
            current = Coordinate.of(startCoordinate.x, startCoordinate.y - 1, Direction.NORTH);
        }

        List<Coordinate> boundary = new ArrayList<>();
        boundary.add(startCoordinate);

        long shoelace = calculatePoints(startCoordinate, current);

        // Start following the pipes
        do {
            boundary.add(current);

            Pipe currentPipe = getPipe(input, current);
            Direction newDirection = currentPipe.getOther(current.from);
            Coordinate nextPipe = Coordinate.of(current.x + newDirection.xMod, current.y + newDirection.yMod, Direction.getOpposite(newDirection));
            long additionalArea = calculatePoints(current, nextPipe);
            shoelace += additionalArea;
            System.out.println("Shoelace: " + shoelace + " (" + additionalArea + ") " + current + " " + nextPipe);
            current = nextPipe;
        } while (!current.equals(startCoordinate));

        System.out.println("Boundary points (b): " + boundary.size());
        System.out.println("Shoelace (A): " + Math.abs(shoelace/2));

        System.out.println("Result (2023 D10P2): " + (Math.abs(shoelace/2) + 1 - (boundary.size()/2)) + " tiles in loop");
    }

    public static long calculatePoints(Coordinate first, Coordinate second) {
        return ((long) first.x * second.y) - ((long) second.x * first.y);
    }

    public static Pipe getPipe(List<String> input, Coordinate coordinate) {
        char pipe = input.get(coordinate.y).charAt(coordinate.x);
        if (pipe == 'S') return new Pipe(Direction.NULL, Direction.NULL, PipeType.CORNER);

        return PIPES.get(pipe);
    }

    @AllArgsConstructor
    static class Pipe {
        public final Direction one;
        public final Direction two;
        public final PipeType type;

        public boolean allowsPipeFrom(Direction from) {
            return one.equals(from) || two.equals(from);
        }

        public Direction getOther(Direction first) {
            return first.equals(one) ? two : one;
        }
    }


    @AllArgsConstructor
    static class Coordinate {
        public final int x;
        public final int y;
        public final Direction from;

        public static Coordinate of(int x, int y) {
            return new Coordinate(x, y, null);
        }

        public static Coordinate of(int x, int y, Direction direction) {
            return new Coordinate(x, y, direction);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinate that = (Coordinate) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "{x="+x+",y="+y+",from="+from.name()+"}";
        }
    }

    @AllArgsConstructor
    enum Direction {
        NORTH(0, -1),
        EAST(1, 0),
        SOUTH(0, 1),
        WEST(-1, 0),
        NULL(0, 0);

        public int xMod;
        public int yMod;

        public static Direction getOpposite(Direction direction) {
            return switch (direction) {
                case NORTH -> SOUTH;
                case EAST -> WEST;
                case SOUTH -> NORTH;
                case WEST -> EAST;
                default -> NULL;
            };
        }
    }

    enum PipeType {
        CORNER,
        UP,
        FLAT
    }
}