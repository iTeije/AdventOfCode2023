package com.cachedcloud.aoc24.day17;

import com.cachedcloud.aoc.common.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class DaySeventeenPartTwo {

    private static long registerA = 0, registerB, registerC;

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day17.txt");
//        FileReader reader = new FileReader("example-input-day17.txt");
        List<String> input = reader.getInputAsStrings();

        long indexA = 0;
        registerB = Long.parseLong(input.get(1).substring(12));
        registerC = Long.parseLong(input.get(2).substring(12));

        // Process instructions
        String[] rawInstructions = input.get(4).substring(9).split(",");
        int[] instructions = Arrays.stream(rawInstructions).mapToInt(Integer::parseInt).toArray();
        int listSize = instructions.length;

        // GUESS WHAT, BRUTEFORCING DOES NOT WORK
        // FFS I ACTUALLY NEED TO BE SMART AND USE OCTAL
        // BUT I MOST CERTAINLY DO NOT KNOW HOW

        registerLoop:
        while (true) {
            indexA++;
            registerA = indexA; registerB = 0; registerC = 0;
            if (registerA % 1000000 == 0) {
                System.out.println("Checking registerA: " + registerA);
            }

            int instructionsIndex = 0;
            for (int i = 0; i < listSize; i+=2) {
                int opcode = instructions[i];
                int operand = instructions[i+1];

                AtomicInteger atomicOutput = new AtomicInteger(-1);
                int pointer = processOp(opcode, operand, atomicOutput);
                if (pointer != -1) {
                    i = pointer;
                    i -= 2;
                } else if (atomicOutput.get() != -1) {
                    int output = atomicOutput.get();
                    boolean inappropriateSize = instructionsIndex + 1 > listSize;
                    if (inappropriateSize) continue registerLoop;

                    boolean inappropriateOutput = instructions[instructionsIndex] != output;
                    if (inappropriateOutput) continue registerLoop;
                    instructionsIndex++;

                    if (instructionsIndex == listSize) break registerLoop;
                }
            }
            // Make sure the output contains the same number of characters as the original instructions, otherwise continue
            if (instructionsIndex + 1 != listSize) continue;
            break;
        }

        System.out.println("D17P2: " + indexA);
    }

    private static int processOp(int opcode, int operand, AtomicInteger output) {
        if (opcode == 0) { // adv instruction - division
            long division = registerA / (long) Math.pow(2, getComboOperand(operand));
            registerA = Math.min(Integer.MAX_VALUE, division);
        } else if (opcode == 1) { // bxl instruction - bitwise XOR
            registerB = registerB ^ operand;
        } else if (opcode == 2) { // bst instruction - modulo 8
            registerB = getComboOperand(operand) % 8;
        } else if (opcode == 3) { // jnz instruction - sometimes doesn't do anything, sometimes does
            if (registerA == 0) return -1;
            return operand;
        } else if (opcode == 4) { // bxc instruction
            registerB = registerB ^ registerC;
        } else if (opcode == 5) { // out instruction
            long val = getComboOperand(operand) % 8;
            output.set((int) val);
        } else if (opcode == 6) { // bdv instruction
            long division = registerA / (long) Math.pow(2, getComboOperand(operand));
            registerB = Math.min(Integer.MAX_VALUE, division);
        } else if (opcode == 7) { // cdv instruction
            long division = registerA / (long) Math.pow(2, getComboOperand(operand));
            registerC = Math.min(Integer.MAX_VALUE, division);
        }
        return -1;
    }

    private static long getComboOperand(int operand) {
        if (operand <= 3) return operand;
        if (operand == 4) return registerA;
        if (operand == 5) return registerB;
        if (operand == 6) return registerC;
        return -1; // reserved - should not occur
    }

}
