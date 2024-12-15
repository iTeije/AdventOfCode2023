package com.cachedcloud.aoc24.day15;

import com.cachedcloud.aoc.ParseUtil;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.grid.Grid;
import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.location.Direction;

import java.util.*;

public class DayFifteenPartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day15.txt");
//        FileReader reader = new FileReader("example-input-day15.txt");

        List<String> gridInput = new ArrayList<>();
        List<Direction> moves = new ArrayList<>();

        Coordinate current = null;
        boolean finishedGrid = false;
        int y = 0;
        // --- START INPUT PARSING ---
        for (String line : reader.getInputAsStrings()) {
            if (!finishedGrid) {
                if (line.isEmpty()) {
                    finishedGrid = true;
                    continue;
                }
                int index;
                if ((index = line.indexOf('@')) != -1) {
                    current = Coordinate.of(index * 2, y);
                }
                // Expand input (PART 2 ONLY)
                StringBuilder newLine = new StringBuilder();
                for (char c : line.toCharArray()) {
                    if (c == 'O') {
                        newLine.append("[]");
                    } else if (c == '@') {
                        newLine.append("@.");
                    } else {
                        newLine.repeat(c, 2);
                    }
                }
                // Add expanded input to grid input
                gridInput.add(newLine.toString());
                y++;
                continue;
            }

            // Parse moves
            for (char c : line.toCharArray()) {
                if (c == '^') moves.add(Direction.NORTH);
                if (c == '>') moves.add(Direction.EAST);
                if (c == '<') moves.add(Direction.WEST);
                if (c == 'v') moves.add(Direction.SOUTH);
            }
        }
        // Convert string list to a grid
        Grid<Character> grid = ParseUtil.createCharGrid(gridInput);
        // --- END OF INPUT PARSING ---

        // Cycle through moves
        for (Direction direction : moves) {
            Coordinate next = current.relative(direction, 1);
            Character character = grid.get(next);
            // Move if possible without moving boxes/running into walls
            if (character == '.') {
                grid.setValue(current, '.');
                grid.setValue(next, '@');
                current = next;
                continue;
            }

            // Nothing happens if movement is directly against a wall
            if (character == '#') {
                continue;
            }

            // Handle horizontal box moves
            Map<Coordinate, Character> branches = getBranchedCoordinates(grid, direction, next);
            // Check if the value associated with any of the coordinates is a wall
            boolean blocked = branches.values().stream().anyMatch(branchChar -> branchChar == '#');
            // Skip if blocked
            if (blocked) {
                continue;
            }
            // Move all related characters, starting from the opposite direction
            Set<Coordinate> updated = new HashSet<>();
            branches.forEach((coordinate, valueAt) -> {
                if (valueAt != '.') {
                    // Update the character at the coordinate one down
                    Coordinate newCoordinate = coordinate.relative(direction, 1);
                    updated.add(newCoordinate);
                    grid.setValue(newCoordinate, valueAt);

                    // ONLY replace the old coordinate with empty space if it was not already updated
                    // A non-updated value means that either an update is yet to come (which will override the current empty space),
                    // or there will be no update and it is actually empty
                    if (!updated.contains(coordinate)) {
                        grid.setValue(coordinate, '.');
                    }
                }
            });

            // Update current robot position
            grid.setValue(current, '.');
            grid.setValue(next, '@');
            current = next;
        }

        // Calculate GPS coordinates of boxes
        long count = grid.streamGrid().mapToLong((coordinate, character) -> {
            if (character == '[') return 100L * coordinate.y + coordinate.x;
            return 0L;
        }).sum();

        System.out.println("D15P1: " + count);
    }

    private static Coordinate getOtherBoxPart(Grid<Character> grid, Coordinate from) {
        return from.relative(grid.valueAt(from) == '[' ? Direction.EAST : Direction.WEST, 1);
    }

    // the box variable only represents one of the two coordinates involved in a single box. the other will be fetched inside of the function
    private static Map<Coordinate, Character> getBranchedCoordinates(Grid<Character> grid, Direction branchDirection, Coordinate box) {
        Map<Coordinate, Character> map = new HashMap<>();
        // Get the adjacent character that is part of the current box
        Coordinate otherPart = getOtherBoxPart(grid, box);
        // Store box coordinates and characters
        map.put(box, grid.valueAt(box));
        map.put(otherPart, grid.valueAt(otherPart));

        boolean horizontal = branchDirection == Direction.EAST || branchDirection == Direction.WEST;
        // Branch out to other boxes/characters in the provided direction
        Coordinate next = box.relative(branchDirection, horizontal ? 2 : 1);
        Character nextVal = grid.valueAt(next);
        // If branch contains square brackets, branch out further
        if (nextVal == '[' || nextVal == ']') {
            map.putAll(getBranchedCoordinates(grid, branchDirection, next));
        } else map.put(next, nextVal);

        if (!horizontal) {
            // Branch out to other boxes/characters in the provided direction, going from the other position involved in the current box
            Coordinate nextFromOther = otherPart.relative(branchDirection, 1);
            Character nextValOther = grid.valueAt(nextFromOther);
            // Branch out if the branch contains square brackets and avoid duplicate computing
            if ((nextValOther == '[' || nextValOther == ']') && nextValOther != grid.valueAt(otherPart)) {
                map.putAll(getBranchedCoordinates(grid, branchDirection, nextFromOther));
            } else map.put(nextFromOther, nextValOther);
        }

        return map;
    }
}