package com.cachedcloud.aoc.grid;

import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.location.Direction;
import com.google.mu.util.stream.BiStream;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class Grid<T> {

    private final Map<Coordinate, T> grid;
    @Setter private T emptyValue;

    private final int width;
    private final int height;

    public Grid(T[][] grid, T defaultValue) {
        this.grid = new HashMap<>();
        this.emptyValue = defaultValue;
        int maxWidth = 0;
        this.height = grid.length;
        for (int y = 0; y < height; y++) {
            int width = grid[y].length;
            if (width > maxWidth) maxWidth = width;
            for (int x = 0; x < width; x++) {
                this.grid.put(new Coordinate(x, y), grid[y][x]);
            }
        }
        this.width = maxWidth;
    }

    public T get(Coordinate coordinate) {
        return this.grid.getOrDefault(coordinate, emptyValue);
    }

    public Stream<Coordinate> stream() {
        return this.grid.keySet().stream();
    }

    public BiStream<Coordinate, T> streamGrid() {
        BiStream.Builder<Coordinate, T> builder = BiStream.builder();
        grid.forEach(builder::add);
        return builder.build();
    }

    public T valueAt(Coordinate coordinate) {
        return this.grid.getOrDefault(coordinate, emptyValue);
    }

    public T setValue(Coordinate coordinate, T value) {
        return this.grid.put(coordinate, value);
    }

    public Map<Direction, T> getNeighbours(Coordinate from, boolean diagonal) {
        Map<Direction, T> neighbours = new HashMap<>();
        for (Direction direction : Direction.getDirections(diagonal)) {
            neighbours.put(direction, valueAt(from.relative(direction, 1)));
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

    public void print() {
        for (int y = 0; y < height; y++) {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < width; x++) {
                builder.append(valueAt(Coordinate.of(x, y)).toString());
            }
            System.out.println(builder.toString());
        }
    }

}
