package com.cachedcloud.aoc24.day9;

import com.cachedcloud.aoc.common.FileReader;
import com.cachedcloud.aoc.common.Timer;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class DayNinePartTwo {

    public static void main(String[] args) {
        Timer.start();
        FileReader reader = new FileReader("additional-input-day9.txt");
//        FileReader reader = new FileReader("input-day9.txt");
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
            Entry blockEntry = new Entry(true, blocks, idIndex);
            fileSystem.add(blockEntry);

            // Add free space
            fileSystem.add(new Entry(false, freeSpace));

            // Increase file id index
            idIndex++;
        }

        // Move file blocks to the left
        int currentFileId = -1;
        int firstFreeSpace = 0;
        mainLoop:
        for (int i = fileSystem.size() - 1; i >= 0; i--) { // Right pointer
            Entry entry = fileSystem.get(i);

            // Ignore free space make sure to not move the same file twice
            if (!entry.isFile) continue;
            if (currentFileId == -1) currentFileId = entry.fileId;
            if (currentFileId < entry.fileId) continue;

            int nextFreeSpace = 0; // optimization
            // Search for free space (left pointer)
            for (int j = firstFreeSpace; j < i; j++) {
                Entry leftEntry = fileSystem.get(j);

                // Ignore files and free spaces that are too small
                if (leftEntry.isFile) continue;
                if (nextFreeSpace == 0) nextFreeSpace = j;
                if (leftEntry.length < entry.length) continue;

                // Move file block
                int lengthDelta = leftEntry.length - entry.length;
                fileSystem.set(j, entry);
                fileSystem.set(i, new Entry(false, entry.length));
                if (lengthDelta > 0) {
                    fileSystem.add(j + 1, new Entry(false, lengthDelta));
                }
                currentFileId = entry.fileId;

                if (nextFreeSpace > firstFreeSpace) firstFreeSpace = nextFreeSpace;
                continue mainLoop;
            }
        }

        // Calculate checksum
        long output = 0;
        int index = 0;
        for (Entry entry : fileSystem) {
            // Loop through the length of the entry (since the position needs to be updated)
            for (int i = 0; i < entry.length; i++) {
                if (entry.isFile) {
                    output += (long) index * entry.fileId;
                }

                index++;
            }
        }

        //  Print checksum
        System.out.println("D9P2: " + output);
        Timer.finish();
    }

    @AllArgsConstructor
    public static class Entry {
        public final boolean isFile;
        public int length;
        public int fileId;

        public Entry(boolean isFile, int length) {
            this.isFile = isFile;
            this.length = length;
        }
    }
}