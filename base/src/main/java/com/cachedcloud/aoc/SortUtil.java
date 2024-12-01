package com.cachedcloud.aoc;

import java.util.Collections;
import java.util.List;

public class SortUtil {

    public static <T extends Comparable<T>> void ascending(List<T> list) {
        Collections.sort(list);
    }

    public static <T extends Comparable<T>> void descending(List<T> list) {
        list.sort(Collections.reverseOrder());
    }

}
