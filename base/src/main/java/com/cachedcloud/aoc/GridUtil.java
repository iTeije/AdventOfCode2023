package com.cachedcloud.aoc;

import java.util.List;

public class GridUtil {

    public static char[][] createCharGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

}
