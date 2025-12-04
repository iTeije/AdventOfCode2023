package com.cachedcloud.aoc25.day4;

import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.FileReader;

public class DayFourPartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day4.txt");
//        FileReader reader = new FileReader("example-input-day4.txt");

        char[][] charGrid = GridUtil.createCharGrid(reader.getInputAsStrings());
        int count = 0;
        int y = 0;
        for (char[] line : charGrid) {
            int x = 0;
            for (char roll : line) {
                if (roll == '@' && canAccess(charGrid, x, y)) count++;
                x++;
            }
            y++;
        }

        System.out.println("D4P1: " + count);
    }

    // This is [x][y] whereas the usual grids use [y][x]. can't be bothered to fix it
    public static int[][] OFFSETS = new int[][]{
            {-1, -1}, {0, -1}, {1, -1},
            {-1, 0}, {1, 0},
            {-1, 1}, {0, 1}, {1, 1}
    };

    public static boolean canAccess(char[][] grid, int x, int y) {
        int blockedCount = 0;
        for (int[] offset : OFFSETS) {
            int checkX = x + offset[0];
            int checkY = y + offset[1];

            if (checkX < 0 || checkX >= grid[0].length) continue;
            if (checkY < 0 || checkY >= grid.length) continue;

            if (grid[checkY][checkX] == '@') {
                blockedCount++;
            }
        }
        return blockedCount < 4;
    }

}
