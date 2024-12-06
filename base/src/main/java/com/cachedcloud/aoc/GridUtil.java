package com.cachedcloud.aoc;

import java.util.ArrayList;
import java.util.List;

public class GridUtil {

    public static char[][] createCharGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    public static void print(char[][] grid) {
        List<String> list = new ArrayList<>();
        for (char[] chars : grid) {
            StringBuilder builder = new StringBuilder();
            for (char aChar : chars) {
                builder.append(aChar);
            }
            list.add(builder.toString());
        }

        list.forEach(System.out::println);
    }

}
