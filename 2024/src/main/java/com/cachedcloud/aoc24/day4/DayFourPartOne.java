package com.cachedcloud.aoc24.day4;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;

import java.util.ArrayList;
import java.util.List;

public class DayFourPartOne {

    public static List<Line> LINES = new ArrayList<Line>();
    static {
        LINES.add(new Line(
                Offset.of(1, 0),
                Offset.of(2, 0),
                Offset.of(3, 0)
        )); // Left to right
        LINES.add(new Line(
                Offset.of(-1, 0),
                Offset.of(-2, 0),
                Offset.of(-3, 0)
        )); // Right to left
        LINES.add(new Line(
                Offset.of(0, 1),
                Offset.of(0, 2),
                Offset.of(0, 3)
        )); // Down to up
        LINES.add(new Line(
                Offset.of(0, -1),
                Offset.of(0, -2),
                Offset.of(0, -3)
        )); // Up to down
        LINES.add(new Line(
                Offset.of(1, 1),
                Offset.of(2, 2),
                Offset.of(3, 3)
        )); // Northeast
        LINES.add(new Line(
                Offset.of(-1, 1),
                Offset.of(-2, 2),
                Offset.of(-3, 3)
        )); // Northwest
        LINES.add(new Line(
                Offset.of(-1, -1),
                Offset.of(-2, -2),
                Offset.of(-3, -3)
        )); // Southwest
        LINES.add(new Line(
                Offset.of(1, -1),
                Offset.of(2, -2),
                Offset.of(3, -3)
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
                            if (grid[y + l.m.dy][x + l.m.dx] == 'M' && grid[y + l.a.dy][x + l.a.dx] == 'A' && grid[y + l.s.dy][x + l.s.dx] == 'S') count++;
                        } catch (Exception ignored) {} // catch index out of bounds
                    }
                }

                x++;
            }
            y++;
        }

        System.out.println(count);
    }

    public static class Line {
        public Offset m;
        public Offset a;
        public Offset s;
        public Line(Offset m, Offset a, Offset s) {
            this.m = m;
            this.a = a;
            this.s = s;
        }
    }

    public static class Offset {
        public int dx;
        public int dy;
        public Offset(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }
        public static Offset of(int dx, int dy) {
            return new Offset(dx, dy);
        }
    }

}
