package com.cachedcloud.aoc;

import java.util.ArrayList;
import java.util.List;

public class GridUtil {

    /*
     * Deprecated: use ParseUtil.createCharGrid(List<String>)
     */
    public static char[][] createCharGrid(List<String> input) {
        char[][] grid = new char[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }
        return grid;
    }

    public static int[][] createIntGrid(List<String> input) {
        int[][] grid = new int[input.size()][input.get(0).length()];
        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < input.get(i).length(); j++) {
                grid[i][j] = Character.getNumericValue(input.get(i).charAt(j));
            }
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

    public static void print(int[][] grid) {
        List<String> list = new ArrayList<>();
        for (int[] ints : grid) {
            StringBuilder builder = new StringBuilder();
            for (int anInt : ints) {
                builder.append(anInt);
            }
            list.add(builder.toString());
        }
        list.forEach(System.out::println);
    }

    public static char[][] createAndFill(int wide, int tall, char fill) {
        char[][] array = new char[tall][wide];
        for (int y = 0; y < tall; y++) {
            for (int x = 0; x < wide; x++) {
                array[y][x] = fill;
            }
        }
        return array;
    }

    public static Character[][] createAndFillObject(int wide, int tall, Character fill) {
        Character[][] array = new Character[tall][wide];
        for (int y = 0; y < tall; y++) {
            for (int x = 0; x < wide; x++) {
                array[y][x] = fill;
            }
        }
        return array;
    }

}
