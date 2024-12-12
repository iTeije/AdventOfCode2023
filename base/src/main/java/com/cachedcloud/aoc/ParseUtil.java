package com.cachedcloud.aoc;

import com.cachedcloud.aoc.grid.Grid;
import org.apache.commons.lang3.ArrayUtils;

import java.util.List;

public class ParseUtil {

    /**
     * Create a character array and pass it to my Grid utility class
     *
     * @param input string puzzle input
     * @return new grid instance
     */
    public static Grid<Character> createCharGrid(List<String> input) {
        Character[][] grid = new Character[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = ArrayUtils.toObject(input.get(i).toCharArray());
        }
        return new Grid<>(grid, ' ');
    }

}
