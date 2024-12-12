package com.cachedcloud.aoc;

import lombok.Getter;

@Getter
public enum Direction {

    NORTH(0, -1),
    NORTHEAST(1, -1),
    EAST(1, 0),
    SOUTHEAST(1, 1),
    SOUTH(0, 1),
    SOUTHWEST(-1, 1),
    WEST(-1, 0),
    NORTHWEST(-1, -1),
    CENTER(0, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Direction turnRight() {
        return switch (this) {
            case NORTH -> EAST;
            case NORTHEAST -> SOUTHEAST;
            case EAST -> SOUTH;
            case SOUTHEAST -> SOUTHWEST;
            case SOUTH -> WEST;
            case SOUTHWEST -> NORTHWEST;
            case WEST -> NORTH;
            case NORTHWEST -> NORTHEAST;
            case CENTER -> CENTER;
        };
    }

    public Direction turnLeft() {
        return switch (this) {
            case NORTH -> WEST;
            case NORTHWEST -> SOUTHWEST;
            case WEST -> SOUTH;
            case SOUTHWEST -> SOUTHEAST;
            case SOUTH -> EAST;
            case SOUTHEAST -> NORTHEAST;
            case EAST -> NORTH;
            case NORTHEAST -> NORTHWEST;
            case CENTER -> CENTER;
        };
    }

    private static Direction[] eightDirections() {
        return new Direction[]{NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST};
    }

    public static Direction[] getDirections(boolean diagonal) {
        if (diagonal) return eightDirections();
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }
}
