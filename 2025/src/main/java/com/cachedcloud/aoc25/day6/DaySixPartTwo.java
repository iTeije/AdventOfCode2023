package com.cachedcloud.aoc25.day6;

import com.cachedcloud.aoc.common.FileReader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaySixPartTwo {
    public static Pattern OPERATORS = Pattern.compile("[*+]");

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day6.txt");
//        FileReader reader = new FileReader("example-input-day6.txt");

        List<String> input = reader.getInputAsStrings();

        // Parse operators first (last line)
        Map<Integer, Operator> operatorMap = new HashMap<>();
        Matcher matcher = OPERATORS.matcher(input.getLast());

        int index = 0;
        while (matcher.find()) {
            operatorMap.put(index, Operator.get(matcher.group()));
            index++;
        }
        index--;

        long count = 0;
        int rows = input.size() - 1;
        int maxRowSize = 0;
        for (String s : input) {
            maxRowSize = Math.max(maxRowSize, s.length());
        }

        // Read from right to left
        List<Integer> cache = new ArrayList<>();

        for (int i = maxRowSize; i >= 0; i--) {
            StringBuilder builder = new StringBuilder();
            if (i != 0) {
                for (int row = 0; row < rows; row++) {
                    if (input.get(row).length() < i) continue;

                    char charAt = input.get(row).charAt(i - 1);
                    System.out.println(charAt);
                    if (charAt != ' ') builder.append(charAt);
                }
            }

            // Check if all rows are empty at this index
            if (builder.isEmpty() || i == 0) {
                long result = cache.getFirst();
                for (int resultIndex = 1; resultIndex < cache.size(); resultIndex++) {
                    result = operatorMap.get(index).execute(result, cache.get(resultIndex));
                }
                cache.clear();
                count += result;
                index--;
            } else {
                cache.add(Integer.parseInt(builder.toString()));
            }
        }

        System.out.println("D6P2: " + count);
    }

    public enum Operator {
        ADD {
            @Override
            public long execute(long firstValue, long secondValue) {
                return firstValue + secondValue;
            }
        },
        MULTIPLY {
            @Override
            public long execute(long firstValue, long secondValue) {
                return firstValue * secondValue;
            }
        };

        public abstract long execute(long firstValue, long secondValue);

        public static Operator get(String input) {
            if (input.equals("*")) return MULTIPLY;
            return ADD;
        }
    }

}
