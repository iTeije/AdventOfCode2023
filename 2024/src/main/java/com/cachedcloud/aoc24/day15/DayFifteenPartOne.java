package com.cachedcloud.aoc24.day15;

import com.cachedcloud.aoc.ParseUtil;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.grid.Grid;
import com.cachedcloud.aoc.location.Coordinate;
import com.cachedcloud.aoc.location.Direction;

import java.util.ArrayList;
import java.util.List;

public class DayFifteenPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day15.txt");
//        FileReader reader = new FileReader("example-input-day15.txt");

        List<String> gridInput = new ArrayList<>();
        List<Direction> moves = new ArrayList<>();
        Coordinate current = null;
        boolean finishedGrid = false;
        int y = 0;
        for (String line : reader.getInputAsStrings()) {
            if (!finishedGrid) {
                if (line.isEmpty()) {
                    finishedGrid = true;
                    continue;
                }
                int index;
                if ((index = line.indexOf('@')) != -1) {
                    current = Coordinate.of(index, y);
                }
                gridInput.add(line);
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
        Grid<Character> grid = ParseUtil.createCharGrid(gridInput);

        // Cycle through moves
        for (Direction direction : moves) {
            Coordinate next = current.relative(direction, 1);
            Character character = grid.get(next);
            // Check if movement is possible without moving boxes/running into walls
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

            // Push box and make sure it is not blocked by a wall somewhere along the path
            Coordinate nextBox = next.dupe();
            int boxesToMove = 1;
            boolean canMove = true;
            while (true) {
                nextBox = nextBox.relative(direction, 1);
                Character valAt = grid.valueAt(nextBox);
                if (valAt == '#') {
                    canMove = false;
                    break;
                } else if (valAt == '.') {
                    break;
                } else {
                    boxesToMove++;
                }
            }

            // Actually move the box
            if (canMove) {
                grid.setValue(current, '.');
                grid.setValue(next, '@');
                grid.setValue(next.relative(direction, boxesToMove), 'O');
                current = next;
            }
        }

        // Calculate GPS coordinates of boxes
        long count = grid.streamGrid().mapToLong((coordinate, character) -> {
            if (character == 'O') return 100L * coordinate.y + coordinate.x;
            return 0L;
        }).sum();

        System.out.println("D15P1: " + count);
    }

}