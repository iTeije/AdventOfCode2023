package com.cachedcloud.aoc24.day3;

import com.cachedcloud.aoc.FileReader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DayThreePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day3.txt");
//        FileReader reader = new FileReader("example-input-day3.txt");
        List<String> input = reader.getInputAsStrings();

        String regex = "mul\\(\\s*\\d+,\\s*\\d+\\s*\\)";
        Pattern pattern = Pattern.compile(regex);

        int count =0;

        for (String line : input) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
                String group = matcher.group();
                String[] elements = group.replace("mul(", "").replace(")", "").split(",");

                count += Integer.parseInt(elements[0]) * Integer.parseInt(elements[1]);
            }
        }

        System.out.println(count);
    }

}