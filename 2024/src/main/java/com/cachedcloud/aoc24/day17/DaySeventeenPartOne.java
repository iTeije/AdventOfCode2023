package com.cachedcloud.aoc24.day17;

import com.cachedcloud.aoc.common.FileReader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DaySeventeenPartOne {

    private static long registerA, registerB, registerC;

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day17.txt");
//        FileReader reader = new FileReader("example-input-day17.txt");
        List<String> input = reader.getInputAsStrings();

        registerA = Long.parseLong(input.get(0).substring(12));
        registerB = Long.parseLong(input.get(1).substring(12));
        registerC = Long.parseLong(input.get(2).substring(12));

        // Process instructions
        String[] rawInstructions = input.get(4).substring(9).split(",");
        List<Integer> output = new ArrayList<>();
        for (int i = 0; i < rawInstructions.length; i+=2) {
            int opcode = Integer.parseInt(rawInstructions[i]);
            int operand = Integer.parseInt(rawInstructions[i+1]);

            int pointer = processOp(opcode, operand, output);
            if (pointer != -1) {
                i = pointer;
                i -= 2;
            }
        }

        System.out.println("D17P1: " + output.stream().map(String::valueOf).collect(Collectors.joining(",")));
    }

    private static long getComboOperand(int operand) {
        if (operand <= 3) return operand;
        if (operand == 4) return registerA;
        if (operand == 5) return registerB;
        if (operand == 6) return registerC;
        return -1; // reserved - should not occur
    }

    private static int processOp(int opcode, int operand, List<Integer> output) {
        System.out.println("Processing opcode=" + opcode + " operand=" + operand);
        if (opcode == 0) { // adv instruction - division
            long division = registerA / (long) Math.pow(2, getComboOperand(operand));
            registerA = Math.min(Integer.MAX_VALUE, division);
            System.out.println("Setting registerA (opcode 0) to: " + Math.min(Integer.MAX_VALUE, division));
        } else if (opcode == 1) { // bxl instruction - bitwise XOR
            long xor = registerB ^ operand;
            registerB = xor;
            System.out.println("Setting registerB (opcode 1) to: " + xor);
        } else if (opcode == 2) { // bst instruction - modulo 8
            long value = getComboOperand(operand) % 8;
            registerB = value;
            System.out.println("Setting registerB (opcode 2) to: " + value);
        } else if (opcode == 3) { // jnz instruction - sometimes doesn't do anything, sometimes does
            if (registerA == 0) return -1;
            System.out.println("Jumping pointer to literal operand " + operand);
            return operand;
        } else if (opcode == 4) { // bxc instruction
            long xor = registerB ^ registerC;
            registerB = xor;
            System.out.println("Setting registerB (opcode 4) to: " + xor);
        } else if (opcode == 5) { // out instruction
            long val = getComboOperand(operand) % 8;
            output.add((int) val);
            System.out.println("Adding " + val + " to output");
        } else if (opcode == 6) { // bdv instruction
            long division = registerA / (long) Math.pow(2, getComboOperand(operand));
            registerB = Math.min(Integer.MAX_VALUE, division);
            System.out.println("Setting registerB (opcode 6) to: " + Math.min(Integer.MAX_VALUE, division));
        } else if (opcode == 7) { // cdv instruction
            long division = registerA / (long) Math.pow(2, getComboOperand(operand));
            registerC = Math.min(Integer.MAX_VALUE, division);
            System.out.println("Setting registerC (opcode 7) to: " + Math.min(Integer.MAX_VALUE, division));
        }
        return -1;
    }

}
