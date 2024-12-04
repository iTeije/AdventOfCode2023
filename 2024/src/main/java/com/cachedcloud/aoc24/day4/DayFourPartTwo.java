package com.cachedcloud.aoc24.day4;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;

import java.util.ArrayList;
import java.util.List;

public class DayFourPartTwo {

    public static List<Line> LINES = new ArrayList<Line>();
    static {
        LINES.add(new Line(
                Offset.of(1, 1),
                Offset.of(-1, 1),
                Offset.of(-1, -1),
                Offset.of(1, -1)
        )); // S north, M south
        LINES.add(new Line(
                Offset.of(-1, -1),
                Offset.of(1, -1),
                Offset.of(1, 1),
                Offset.of(-1, 1)
        )); // S south, M north
        LINES.add(new Line(
                Offset.of(-1, -1),
                Offset.of(-1, 1),
                Offset.of(1, 1),
                Offset.of(1, -1)
        )); // S west, M east
        LINES.add(new Line(
                Offset.of(1, 1),
                Offset.of(1, -1),
                Offset.of(-1, -1),
                Offset.of(-1, 1)
        )); // S east, M west
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
                if (c == 'A') {
                    for (Line l : LINES) {
                        try {
                            if (grid[y + l.m1.dy][x + l.m1.dx] == 'M' && grid[y + l.m2.dy][x + l.m2.dx] == 'M' &&
                                    grid[y + l.s1.dy][x + l.s1.dx] == 'S' && grid[y + l.s2.dy][x + l.s2.dx] == 'S') {
                                count++;
                            }
                        } catch (Exception e) {}
                    }
                }

                x++;
            }
            y++;
        }

        System.out.println(count);
    }

    public static class Line {
        public Offset s1;
        public Offset s2;
        public Offset m1;
        public Offset m2;

        public Line(Offset s1, Offset s2, Offset m1, Offset m2) {
            this.s1 = s1;
            this.s2 = s2;
            this.m1 = m1;
            this.m2 = m2;
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
