package com.cachedcloud.aoc22.day3;

import com.cachedcloud.aoc.FileReader;

import java.util.List;

public class DayThreePartTwo {

    public static void main(String[] unusedArgs) {
        FileReader reader = new FileReader("input-day3.txt");
        List<String> input = reader.getInputAsStrings();

        int sum = 0;
        String str1 = "", str2 = "", str3 = "";

        for (int i = 1; i <= input.size(); i++) {
            if (i % 3 == 0) {
                // Define str3
                str3 = input.get(i - 1);
                // Compute result
                int highestPriority = 0;
                for (char ch : str3.toCharArray()) {
                    String stringChar = String.valueOf(ch);
                    if (str1.contains(stringChar) && str2.contains(stringChar)) {
                        highestPriority = Math.max(highestPriority, getPriority(ch));
                    }
                }
                sum += highestPriority;
            } else if (i % 2 == 0) {
                str2 = input.get(i - 1);
            } else str1 = input.get(i - 1);
        }

        System.out.println("Result (2022 D3P2): " + sum);
    }

    private static int getPriority(char ch) {
        int offset = Character.isUpperCase(ch) ? 38 : 96;
        return (int) ch - offset;
    }
}
