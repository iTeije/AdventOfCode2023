package com.cachedcloud.aoc23.day7;

import com.cachedcloud.aoc.FileReader;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class DaySevenPartOne {

    private static final List<Character> cardWeights = new LinkedList<>(Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'));

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day7.txt").getInputAsStrings();
        List<Hand> hands = input.stream().map(string -> {
            String[] data = string.split(" ");
            return new Hand(data[0], Integer.parseInt(data[1]));
        }).toList();

        List<Type> checkOrder = new LinkedList<>(Arrays.asList(Type.values()));

        Map<Type, List<Hand>> handMap = new LinkedHashMap<>();
        checkOrder.forEach(type -> {
            handMap.put(type, new ArrayList<>());
        });
        handLoop:
        for (Hand hand : hands) {
            for (Type type : checkOrder) {
                boolean match = type.matchFunction.apply(hand.hand);
                if (match) {
                    handMap.get(type).add(hand);
                    continue handLoop;
                }
            }
        }

        final AtomicLong totalWinnings = new AtomicLong();
        final int[] rank = {1000};
        handMap.forEach((type, list) -> {
            // Sort the list of hands by highest card
            list.sort((a, b) -> {
                for (int i = 0; i < 5; i++) {
                    int weightA = getHighCard(a.hand, i);
                    int weightB = getHighCard(b.hand, i);
                    if (weightA == weightB) continue;
                    return Integer.compare(weightB, weightA);
                }
                return 0;
            });
            for (Hand hand : list) {
                totalWinnings.addAndGet((long) hand.bid * rank[0]);
                rank[0]--;
            }
        });

        System.out.println("Result (2023 D7P1): " + totalWinnings.get());
    }

    public static int getHighCard(String hand, int charAt) {
        char card = hand.charAt(charAt);
        return cardWeights.indexOf(card);
    }

    @AllArgsConstructor
    static class Hand {
        public final String hand;
        public final int bid;
    }

    @AllArgsConstructor
    enum Type {
        FIVE_OF_A_KIND(6, hand -> {
            Set<Character> characters = new HashSet<>();
            for (char ch : hand.toCharArray()) {
                characters.add(ch);
            }
            return characters.size() == 1;
        }),
        FOUR_OF_A_KIND(5, hand -> {
            Map<Character, Integer> characters = new HashMap<>();
            for (char ch : hand.toCharArray()) {
                characters.put(ch, characters.getOrDefault(ch, 0) + 1);
            }
            return characters.containsValue(4);
        }),
        FULL_HOUSE(4, hand -> {
            Map<Character, Integer> characters = new HashMap<>();
            for (char ch : hand.toCharArray()) {
                characters.put(ch, characters.getOrDefault(ch, 0) + 1);
            }
            // After four and five of a kind checks, this will suffice
            return characters.size() == 2;
        }),
        THREE_OF_A_KIND(3, hand -> {
            Map<Character, Integer> characters = new HashMap<>();
            for (char ch : hand.toCharArray()) {
                characters.put(ch, characters.getOrDefault(ch, 0) + 1);
            }
            return characters.containsValue(3);
        }),
        TWO_PAIR(2, hand -> {
            Map<Character, Integer> characters = new HashMap<>();
            for (char ch : hand.toCharArray()) {
                characters.put(ch, characters.getOrDefault(ch, 0) + 1);
            }
            int pairCount = 0;
            for (int val : characters.values()) {
                if (val == 2) pairCount++;
            }
            return pairCount == 2;
        }),
        ONE_PAIR(1, hand -> {
            Map<Character, Integer> characters = new HashMap<>();
            for (char ch : hand.toCharArray()) {
                characters.put(ch, characters.getOrDefault(ch, 0) + 1);
            }
            return characters.containsValue(2);
        }),
        HIGH_CARD(0, hand -> true);

        private final int weight;
        private final Function<String, Boolean> matchFunction;
    }
}
