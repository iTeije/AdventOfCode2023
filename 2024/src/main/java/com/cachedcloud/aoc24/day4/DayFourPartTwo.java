package com.cachedcloud.aoc24.day4;

import com.cachedcloud.aoc.Coordinate;
import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.GridUtil;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DayFourPartTwo {

    public static List<Line> LINES = new ArrayList<Line>();
    static {
        LINES.add(new Line(
                Coordinate.of(1, 1),
                Coordinate.of(-1, 1),
                Coordinate.of(-1, -1),
                Coordinate.of(1, -1)
        )); // S north, M south
        LINES.add(new Line(
                Coordinate.of(-1, -1),
                Coordinate.of(1, -1),
                Coordinate.of(1, 1),
                Coordinate.of(-1, 1)
        )); // S south, M north
        LINES.add(new Line(
                Coordinate.of(-1, -1),
                Coordinate.of(-1, 1),
                Coordinate.of(1, 1),
                Coordinate.of(1, -1)
        )); // S west, M east
        LINES.add(new Line(
                Coordinate.of(1, 1),
                Coordinate.of(1, -1),
                Coordinate.of(-1, -1),
                Coordinate.of(-1, 1)
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
                            if (grid[y + l.m1.y][x + l.m1.x] == 'M' && grid[y + l.m2.y][x + l.m2.x] == 'M' &&
                                    grid[y + l.s1.y][x + l.s1.x] == 'S' && grid[y + l.s2.y][x + l.s2.x] == 'S') {
                                count++;
                            }
                        } catch (Exception ignored) {}
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
        public Coordinate s1;
        public Coordinate s2;
        public Coordinate m1;
        public Coordinate m2;
    }

}
