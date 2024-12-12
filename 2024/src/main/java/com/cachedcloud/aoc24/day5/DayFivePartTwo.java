package com.cachedcloud.aoc24.day5;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;
import lombok.AllArgsConstructor;

import java.util.*;

public class DayFivePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day5.txt");
//        FileReader reader = new FileReader("example-input-day5.txt");
        List<String> input = reader.getInputAsStrings();
        Timer.start();

        boolean checkRules = true;
        Set<Rule> rules = new HashSet<>();

        List<List<Integer>> updateList = new ArrayList<>();

        // Parse input
        for (String line : input) {
            if (checkRules) {
                // PARSE RULES
                String[] elements = line.split("\\|");
                if (elements.length == 2) {
                    rules.add(new Rule(Integer.parseInt(elements[0]), Integer.parseInt(elements[1])));
                } else checkRules = false;
            } else {
                // PARSE UPDATES
                String[] elements = line.split(",");
                List<Integer> update = new ArrayList<>();
                Arrays.stream(elements).forEach(element -> update.add(Integer.parseInt(element)));
                updateList.add(update);
            }
        }

        int count = 0;
        for (List<Integer> update : updateList) {
            boolean inOrder = isInOrder(rules, update);
            if (!inOrder) {
                // Fix order here
                List<Integer> copy = new ArrayList<>(update);
                List<Rule> applicableRules = getApplicableRules(rules, copy);

                // I did not expect this to work. I hate making sorting functions
                copy.sort((o1, o2) -> {
                    for (Rule rule : applicableRules) {
                        if (rule.before == o1 && rule.after == o2) return 0;
                        if (rule.before == o2 && rule.after == o1) return -1;
                    }
                    return 0;
                });

                count += copy.get((copy.size() - 1)/2);
            }
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

    public static List<Rule> getApplicableRules(Set<Rule> ruleSet, List<Integer> update) {
        List<Rule> applicableRules = new ArrayList<>();
        for (Rule rule : ruleSet) {
            if (update.contains(rule.before)
                    && update.contains(rule.after)) applicableRules.add(rule);
        }

        return applicableRules;
    }

    @AllArgsConstructor
    public static class Rule {
        public int before;
        public int after;
    }

}
