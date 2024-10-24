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

        boolean[][] loop = new boolean[input.size()][input.get(0).length()];
        loop[startCoordinate.y][startCoordinate.x] = true;

        // Start following the pipes
        do {
            loop[current.y][current.x] = true;

            Pipe currentPipe = getPipe(input, current);
            Direction newDirection = currentPipe.getOther(current.from);
            current = Coordinate.of(current.x + newDirection.xMod, current.y + newDirection.yMod, Direction.getOpposite(newDirection));
        } while (!current.equals(startCoordinate));

        int horizontalSize = input.get(0).length();
        int count = 0;
        List<Coordinate> notChecked = new ArrayList<>();
        int validTiles = 0;
        int invalidTiles = 0;
        for (int y = 0; y < input.size(); y++) {
            for (int x = 0; x < horizontalSize; x++) {
                if (loop[y][x]) {
                    invalidTiles++;
                    notChecked.add(Coordinate.of(x, y));
                    continue;
                }

                validTiles++;

                int cornerPieces = 0;
                int intersections = 0;
                if (x + 1 < horizontalSize) {
                    for (int xRay = x + 1; xRay < horizontalSize; xRay++) {
                        if (loop[y][xRay]) {
                            Pipe pipe = getPipe(input, Coordinate.of(xRay, y));
                            if (pipe.type == PipeType.CORNER) {
                                cornerPieces++;
                            } else if (pipe.type == PipeType.UP) {
                                intersections++;
                            }

                        }
                    }
                }

                // If number of intersections is odd, then the coordinate is inside the loop
                if ((intersections + (cornerPieces/2)) % 2 == 1) {
                    System.out.println(x + "," + y + " in loop: " + intersections + "/" + cornerPieces
                            + " (" + (intersections + (cornerPieces/2)) + " - " + ((intersections + (cornerPieces/2)) % 2) + ")");
                    count++;
                }
            }
        }

        System.out.println("Result (2023 D10P2): " + count + " tiles in loop");
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
