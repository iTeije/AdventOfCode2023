package com.cachedcloud.aoc24.day4;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DayFourPartOne {

    public static List<Line> LINES = new ArrayList<Line>();
    static {
        LINES.add(new Line(
                Coordinate.of(1, 0),
                Coordinate.of(2, 0),
                Coordinate.of(3, 0)
        )); // Left to right
        LINES.add(new Line(
                Coordinate.of(-1, 0),
                Coordinate.of(-2, 0),
                Coordinate.of(-3, 0)
        )); // Right to left
        LINES.add(new Line(
                Coordinate.of(0, 1),
                Coordinate.of(0, 2),
                Coordinate.of(0, 3)
        )); // Down to up
        LINES.add(new Line(
                Coordinate.of(0, -1),
                Coordinate.of(0, -2),
                Coordinate.of(0, -3)
        )); // Up to down
        LINES.add(new Line(
                Coordinate.of(1, 1),
                Coordinate.of(2, 2),
                Coordinate.of(3, 3)
        )); // Northeast
        LINES.add(new Line(
                Coordinate.of(-1, 1),
                Coordinate.of(-2, 2),
                Coordinate.of(-3, 3)
        )); // Northwest
        LINES.add(new Line(
                Coordinate.of(-1, -1),
                Coordinate.of(-2, -2),
                Coordinate.of(-3, -3)
        )); // Southwest
        LINES.add(new Line(
                Coordinate.of(1, -1),
                Coordinate.of(2, -2),
                Coordinate.of(3, -3)
        )); // Your mom
    }

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day4.txt");
//        FileReader reader = new FileReader("example-input-day4.txt");
        List<String> input = reader.getInputAsStrings();

        char[][] grid = GridUtil.createCharGrid(input);
        int count = 0;

        int y = 0;
        for (String line : input) {
            int x = 0;
            for (char c : line.toCharArray()) {
                if (c == 'X') {
                    for (Line l : LINES) {
                        try {
                            if (grid[y + l.m.y][x + l.m.x] == 'M' && grid[y + l.a.y][x + l.a.x] == 'A' && grid[y + l.s.y][x + l.s.x] == 'S') count++;
                        } catch (Exception ignored) {} // catch index out of bounds
                    }
                }

                x++;
            }
            y++;
        }

        System.out.println(count);
    }

    @AllArgsConstructor
    public static class Line {
        public Coordinate m;
        public Coordinate a;
        public Coordinate s;
    }
}
