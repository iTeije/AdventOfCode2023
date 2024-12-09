package com.cachedcloud.aoc24.day9;

import com.cachedcloud.aoc.FileReader;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DayNinePartTwo {

    public static void main(String[] args) {
        FileReader reader = new FileReader("input-day9.txt");
//        FileReader reader = new FileReader("example-input-day9.txt");

        // Input is only one line
        String input = reader.getInputAsStrings().get(0);

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
            Entry blockEntry = new Entry(Type.FILE, blocks, idIndex);
            fileSystem.add(blockEntry);

            // Add free space
            fileSystem.add(new Entry(Type.FREE_SPACE, freeSpace));

            // Increase file id index
            idIndex++;
        }

        // Move file blocks to the left
        int currentFileId = -1;
        mainLoop:
        for (int i = fileSystem.size() - 1; i >= 0; i--) { // Right pointer
            Entry entry = fileSystem.get(i);

            // Ignore free space make sure to not move the same file twice
            if (entry.type == Type.FREE_SPACE) continue;
            if (currentFileId == -1) currentFileId = entry.fileId;
            if (currentFileId < entry.fileId) continue;

            // Search for free space (left pointer)
            for (int j = 0; j < fileSystem.size(); j++) {
                Entry leftEntry = fileSystem.get(j);

                // Ignore files and free spaces that are too small
                if (leftEntry.type == Type.FILE) continue;
                if (leftEntry.length < entry.length) continue;

                // If sufficient free space is available to the RIGHT of the current position,
                // we obviously do not want to move the file block.
                if (j >= i) continue mainLoop;

                // Move file block
                int lengthDelta = leftEntry.length - entry.length;
                fileSystem.set(j, entry);
                fileSystem.set(i, new Entry(Type.FREE_SPACE, entry.length));
                if (lengthDelta > 0) {
                    fileSystem.add(j + 1, new Entry(Type.FREE_SPACE, lengthDelta));
                }

                continue mainLoop;
            }
        }

        // Calculate checksum
        long output = 0;
        int index = 0;
        for (Entry entry : fileSystem) {
            // Loop through the length of the entry (since the position needs to be updated)
            for (int i = 0; i < entry.length; i++) {
                if (entry.type == Type.FILE) {
                    output += (long) index * entry.fileId;
                }

                index++;
            }
        }

        //  Print checksum
        System.out.println("D9P2: " + output);
    }

    public enum Type {
        FILE,
        FREE_SPACE
    }

    @AllArgsConstructor
    public static class Entry {
        public final Type type;
        public int length;
        public int fileId;

        public Entry(Type type, int length) {
            this.type = type;
            this.length = length;
        }
    }
}