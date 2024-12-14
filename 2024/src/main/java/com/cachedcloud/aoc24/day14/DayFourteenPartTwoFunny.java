package com.cachedcloud.aoc24.day14;

import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.location.Coordinate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DayFourteenPartTwoFunny {

    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("input-day14.txt");
        int tall = 103;
        int wide = 101;

        // Get robots
        List<Robot> robots = new ArrayList<>();
        for (String line : reader.getInputAsStrings()) {
            String[] parts = line.split(" ");
            robots.add(new Robot(getCoordinate(parts[0]), getCoordinate(parts[1])));
        }

        //83 + 101 * 71.5 * 2
        // 60 + 103 * 71.5

        int seconds = 6446;
        while (true) {
            char[][] grid = GridUtil.createAndFill(wide, tall, '.');
            // Calculate and print
            for (Robot robot : robots) {
                int x = (robot.velocity.x * seconds + robot.start.x) % wide;
                int y = (robot.velocity.y * seconds + robot.start.y) % tall;
                if (x < 0) x += wide;
                if (y < 0) y += tall;
                grid[y][x] = '#';
            }

            // Print grid
            GridUtil.print(grid);

            System.out.println("Second: " + seconds);
            System.in.read();
            seconds+=1;
        }
    }

    private static Coordinate getCoordinate(String input) {
        return Coordinate.of(input.substring(2));
    }

    private record Robot(Coordinate start, Coordinate velocity) {}
}
