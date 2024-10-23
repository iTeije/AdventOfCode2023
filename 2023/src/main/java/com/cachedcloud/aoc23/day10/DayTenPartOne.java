package com.cachedcloud.aoc23.day10;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc23.day3.DayThreePartOne;
import com.cachedcloud.aoc23.day3.DayThreePartTwo;
import lombok.AllArgsConstructor;

import javax.swing.plaf.SplitPaneUI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class DayTenPartOne {

    private static final Map<Character, Pipe> PIPES = new HashMap<>() {{
        put('F', new Pipe(Direction.EAST, Direction.SOUTH));
        put('L', new Pipe(Direction.NORTH, Direction.EAST));
        put('J', new Pipe(Direction.WEST, Direction.NORTH));
        put('7', new Pipe(Direction.WEST, Direction.SOUTH));
        put('|', new Pipe(Direction.NORTH, Direction.SOUTH));
        put('-', new Pipe(Direction.WEST, Direction.EAST));
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

        // Start following and counting pipes
        int steps = 1;
        while (!current.equals(startCoordinate)) {
            Pipe currentPipe = getPipe(input, current);
            System.out.println(current.from + " (" + current.x + ", " + current.y + ")");
            Direction newDirection = currentPipe.getOther(current.from);
            current = Coordinate.of(current.x + newDirection.xMod, current.y + newDirection.yMod, Direction.getOpposite(newDirection));
            steps++;
        }
        System.out.println(current.from + " (" + current.x + ", " + current.y + ")");

        System.out.println("Start coordinate: " + startCoordinate.x + ", " + startCoordinate.y);
        System.out.println("Result (2023 D10P1): " + steps + " steps, max distance = " + (steps / 2));
    }

    public static Pipe getPipe(List<String> input, Coordinate coordinate) {
        return PIPES.get(input.get(coordinate.y).charAt(coordinate.x));
    }

    @AllArgsConstructor
    static class Pipe {
        public final Direction one;
        public final Direction two;

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
}
