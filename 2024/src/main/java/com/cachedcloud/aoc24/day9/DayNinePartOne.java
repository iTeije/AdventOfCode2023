package com.cachedcloud.aoc24.day9;

import com.cachedcloud.aoc.FileReader;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DayNinePartOne {

    public static Entry FREE_SPACE = new Entry(Type.FREE_SPACE);

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day9.txt");
//        FileReader reader = new FileReader("example-input-day9.txt");

        // Input is only one line
        String input = reader.getInputAsStrings().get(0);

        // So I was thinking about parsing the input and creating a very long string, but since file IDs can contain
        // multiple digits, I won't
        List<Entry> fileSystem = new ArrayList<>();

        int idIndex = 0;
        // Loop through characters in pairs of two
        for (int i = 0; i < input.length(); i += 2) {
            int blocks = input.charAt(i) - '0';
            int freeSpace = 0;

            // Confirm input length since it's sometimes an uneven number
            if (i + 1 < input.length()) {
                freeSpace = input.charAt(i + 1) - '0';
            }

            // Add block entries
            Entry blockEntry = new Entry(Type.FILE, idIndex);
            for (int block = 0; block < blocks; block++) {
                fileSystem.add(blockEntry);
            }

            // Add free space
            for (int space = 0; space < freeSpace; space++) {
                fileSystem.add(FREE_SPACE);
            }

            // Increase file id index
            idIndex++;
        }

        // Test output at this point: 00...111...2...333.44.5555.6666.777.888899

        // Move file blocks to the left
        mainLoop:
        for (int i = 0; i < fileSystem.size(); i++) {
            Entry entry = fileSystem.get(i);
            if (entry.type != Type.FREE_SPACE) continue;

            // Search from the right for file blocks
            for (int j = fileSystem.size() - 1; j >= 0; j--) {
                Entry rightEntry = fileSystem.get(j);
                if (rightEntry.type == Type.FREE_SPACE) continue;

                // If the index from the right is equal or less than the index from the left, the moving process in complete
                if (j <= i) break mainLoop;

                // Update entries
                fileSystem.set(i, rightEntry);
                fileSystem.set(j, FREE_SPACE);
                continue mainLoop;
            }
        }

        // Test output at this point: 0099811188827773336446555566..............

        // Calculate checksum
        long output = 0;
        for (int i = 0; i < fileSystem.size(); i++) {
            Entry entry = fileSystem.get(i);
            if (entry.type == Type.FREE_SPACE) break;
            output += (long) i * entry.fileId;
        }

        //  Print checksum
        System.out.println("D9P1: " + output);
    }

    public enum Type {
        FILE,
        FREE_SPACE
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Entry {
        public final Type type;
        public int fileId;
    }
}