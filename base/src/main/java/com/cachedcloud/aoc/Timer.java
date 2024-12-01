package com.cachedcloud.aoc;

/*
 * Little utility class to print the time to compute
 */
public class Timer {

    private static Long start = System.currentTimeMillis();
    private static Long finish = System.currentTimeMillis();

    public static void start() {
        start = System.currentTimeMillis();
    }

    public static void finish() {
        finish = System.currentTimeMillis();
        System.out.println("\nTook " + (finish - start) + "ms to execute.");
    }

}
