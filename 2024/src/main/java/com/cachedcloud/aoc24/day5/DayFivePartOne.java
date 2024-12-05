package com.cachedcloud.aoc24.day5;

import com.cachedcloud.aoc.FileReader;
import com.cachedcloud.aoc.Timer;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

public class DayFivePartOne {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day5.txt");
//        FileReader reader = new FileReader("example-input-day5.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();

        boolean checkRules = true;
        Set<Rule> rules = new HashSet<>();

        List<List<Integer>> updateList = new ArrayList<>();

        for (String line : input) {
            if (checkRules) {
                String[] elements = line.split("\\|");
                if (elements.length == 2) {
                    rules.add(new Rule(Integer.parseInt(elements[0]), Integer.parseInt(elements[1])));
                } else checkRules = false;
            } else {
                String[] elements = line.split(",");
                List<Integer> update = new ArrayList<>();
                Arrays.stream(elements).forEach(element -> update.add(Integer.parseInt(element)));
                updateList.add(update);
            }
        }

        int count = 0;
        for (List<Integer> update : updateList) {
            boolean inOrder = isInOrder(rules, update);
            if (inOrder) count += update.get((update.size() - 1)/2);
        }

        Timer.finish();
        System.out.println(count);
    }

    public static boolean isInOrder(Set<Rule> ruleSet, List<Integer> update) {
        for (Rule rule : ruleSet) {
            int beforeIndex = update.indexOf(rule.before);
            int afterIndex = update.indexOf(rule.after);
            if (beforeIndex == -1 || afterIndex == -1) continue;

            if (beforeIndex > afterIndex) return false;
        }

        return true;
    }

    @AllArgsConstructor
    public static class Rule {
        public int before;
        public int after;
    }

}
