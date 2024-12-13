package com.cachedcloud.aoc.location;

public class BigCoordinate {

    public long x;
    public long y;

    public BigCoordinate(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public BigCoordinate relative(Direction direction, int distance) {
        return BigCoordinate.of(this.x + ((long) direction.getDx() * distance), this.y + ((long) direction.getDy() * distance));
    }

    // This only works for horizontal/vertical and not for orientations
    public Direction directionTo(BigCoordinate other) {
        long dx = other.x - this.x;
        long dy = other.y - this.y;

        if (dy > 0) return Direction.SOUTH;
        if (dy < 0) return Direction.NORTH;
        if (dx > 0) return Direction.EAST;
        if (dx < 0) return Direction.WEST;
        return Direction.CENTER;
    }

    public static BigCoordinate of(long x, long y) {
        return new BigCoordinate(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BigCoordinate that = (BigCoordinate) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        long result = x;
        result = 31 * result + y;
        return Long.hashCode(result);
    }

}
