package com.cachedcloud.aoc24.day14;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.location.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class DayFourteenPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day14.txt");
        int tall = 103;
        int wide = 101;
        int seconds = 100;

        int q1 = 0, q2 = 0, q3 = 0, q4 = 0;
        int yCenter = (int) Math.floor(tall/2);
        int xCenter = (int) Math.floor(wide/2);

        // Get robots
        for (String line : reader.getInputAsStrings()) {
            String[] parts = line.split(" ");
            Coordinate startingCoordinate = getCoordinate(parts[0]);
            Coordinate velocity = getCoordinate(parts[1]);

            int x = (velocity.x * seconds + startingCoordinate.x) % wide;
            int y = (velocity.y * seconds + startingCoordinate.y) % tall;
            if (x < 0) x += wide;
            if (y < 0) y += tall;

            //
            if (y < yCenter) { // top half
                if (x > xCenter) {
                    q1++;
                } else if (x < xCenter) {
                    q2++;
                }
            } else if (y > yCenter) { // bottom half
                if (x < xCenter) {
                    q3++;
                } else if (x > xCenter) {
                    q4++;
                }
            }
        }

        System.out.println("D14P1: " + (q1 * q2 * q3 * q4));
    }

    private static Coordinate getCoordinate(String input) {
        return Coordinate.of(input.substring(2));
    }
}
