package com.cachedcloud.aoc.location;

public class Coordinate {

    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate relative(Direction direction, int distance) {
        return Coordinate.of(this.x + (direction.getDx() * distance), this.y + (direction.getDy() * distance));
    }

    // This only works for horizontal/vertical and not for orientations
    public Direction directionTo(Coordinate other) {
        int dx = other.x - this.x;
        int dy = other.y - this.y;

        if (dy > 0) return Direction.SOUTH;
        if (dy < 0) return Direction.NORTH;
        if (dx > 0) return Direction.EAST;
        if (dx < 0) return Direction.WEST;
        return Direction.CENTER;
    }

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    public static Coordinate of(String input) {
        // x,y
        String[] parts = input.split(",");
        return Coordinate.of(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }
}
