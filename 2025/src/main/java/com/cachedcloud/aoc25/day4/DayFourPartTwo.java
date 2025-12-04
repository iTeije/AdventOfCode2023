package com.cachedcloud.aoc25.day4;

import com.cachedcloud.aoc.GridUtil;
import com.cachedcloud.aoc.common.FileReader;

public class DayFourPartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day4.txt");
//        FileReader reader = new FileReader("example-input-day4.txt");

        char[][] charGrid = GridUtil.createCharGrid(reader.getInputAsStrings());
        
        int count = 0;
        int iteration = 0;
        while (true) {
            int result = remove(charGrid, iteration);

            if (result == 0) break;
            count += result;
            iteration++;
        }

        System.out.println("D4P2: " + count);
    }

    public static int remove(char[][] charGrid, int iteration) {
        int count = 0;

        for (int y = 0; y < charGrid.length; y++) {
            for (int x = 0; x < charGrid[0].length; x++) {
                if ((charGrid[y][x] == '@' || charGrid[y][x] == iteration) && canAccess(charGrid, x, y)) {
                    count++;
                    charGrid[y][x] = (char) iteration;
                }
            }
        }

        return count;
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
