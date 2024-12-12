package com.cachedcloud.aoc.grid;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.Direction;
import com.google.mu.util.stream.BiStream;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class CharGrid {

    private final Map<Coordinate, Character> grid;
    public final Character emptyCharacter;

    public CharGrid(char[][] grid) {
        this(grid, ' ');
    }

    public CharGrid(char[][] grid, Character emptyCharacter) {
        this.grid = new HashMap<>();
        this.emptyCharacter = emptyCharacter;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                this.grid.put(new Coordinate(x, y), grid[y][x]);
            }
        }
    }

    public Character get(Coordinate coordinate) {
        return this.grid.getOrDefault(coordinate, emptyCharacter);
    }

    public Stream<Coordinate> stream() {
        return this.grid.keySet().stream();
    }

    public BiStream<Coordinate, Character> streamGrid() {
        BiStream.Builder<Coordinate, Character> builder = BiStream.builder();
        grid.forEach(builder::add);
        return builder.build();
    }

    public Character charAt(Coordinate coordinate) {
        return this.grid.getOrDefault(coordinate, emptyCharacter);
    }

    public Map<Direction, Character> getNeighbours(Coordinate from, boolean diagonal) {
        Map<Direction, Character> neighbours = new HashMap<>();
        for (Direction direction : Direction.getDirections(diagonal)) {
            neighbours.put(direction, charAt(from.relative(direction, 1)));
        }
        return neighbours;
    }

    public Map<Direction, Coordinate> getNeighbouringCoordinates(Coordinate from) {
        return this.getNeighbouringCoordinates(from, false);
    }

    public Map<Direction, Coordinate> getNeighbouringCoordinates(Coordinate from, boolean diagonal) {
        Map<Direction, Coordinate> neighbours = new HashMap<>();
        for (Direction direction : Direction.getDirections(diagonal)) {
            neighbours.put(direction, from.relative(direction, 1));
        }
        return neighbours;
    }
}
