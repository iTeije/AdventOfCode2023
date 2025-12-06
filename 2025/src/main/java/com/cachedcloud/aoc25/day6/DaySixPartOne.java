package com.cachedcloud.aoc25.day6;

import com.cachedcloud.aoc.common.FileReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaySixPartOne {

    public static Pattern NUMBER_SPACES = Pattern.compile("[0-9 ]+");
    public static Pattern NUMBERS = Pattern.compile("[0-9]+");
    public static Pattern OPERATORS = Pattern.compile("[*+]");

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day6.txt");
//        FileReader reader = new FileReader("example-input-day6.txt");

        List<String> input = reader.getInputAsStrings();
        Map<Integer, List<Integer>> map = new HashMap<>();
        long count = 0;

        for (String line : input) {
            int index = 0;
            if (NUMBER_SPACES.matcher(line).matches()) {
                Matcher matcher = NUMBERS.matcher(line);

                while (matcher.find()) {
                    map.computeIfAbsent(index, integer -> new ArrayList<>()).add(Integer.valueOf(matcher.group()));
                    index++;
                }
            } else {
                Matcher matcher = OPERATORS.matcher(line);

                while (matcher.find()) {
                    // Get operator and apply it to the numbers
                    Operator operator = Operator.get(matcher.group());
                    List<Integer> list = map.get(index);
                    long result = list.getFirst();

                    for (int i = 1; i < list.size(); i++) {
                        result = operator.execute(result, list.get(i));
                    }

                    count += result;
                    index++;
                }
            }
        }

        System.out.println("D6P1: " + count);
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
