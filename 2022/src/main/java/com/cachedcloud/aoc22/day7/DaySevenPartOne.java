package com.cachedcloud.aoc22.day7;

import com.cachedcloud.aoc.FileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class DaySevenPartOne {

    public static void main(String[] unusedArgs) {
        List<String> input = new FileReader("input-day7.txt").getInputAsStrings();

        AOCDirectory pointer = new AOCDirectory("/", null);
        AOCDirectory main = pointer;

        for (String line : input) {
            // Check if the line is a command
            if (line.startsWith("$")) {
                String[] cmd = line.substring(2).split(" ");
                // Check what command is used
                if (cmd[0].equalsIgnoreCase("cd")) {
                    String dir = cmd[1];
                    // Check for command usage
                    if (dir.equals("/")) {
                        // Go back to main directory (cd /)
                        pointer = main;
                    } else if (dir.equals("..")) {
                        // Up one directory/go to parent directory (cd ..)
                        pointer = pointer.parent;
                    } else {
                        // Go to the specified directory (cd dirname)
                        pointer = pointer.directories.get(dir);
                    }
                } else if (cmd[0].equalsIgnoreCase("ls")) {
                    /* Continue to next line, which won't be detected as command but rather as a file
                    or directory within the current directory. */
                    continue;
                }
                continue;
            }

            // Write to pointer
            String[] properties = line.split(" ");
            // Check type (dir or file)
            if (properties[0].equals("dir")) {
                pointer.directories.put(properties[1], new AOCDirectory(properties[1], pointer));
            } else {
                pointer.files.put(properties[1], new AOCFile(pointer, properties[1], Long.parseLong(properties[0])));
            }
        }

        // Get a sum of directory sizes
        AtomicLong count = new AtomicLong(0);
        getDirSize(main, count);

        System.out.println("Result (2022 D7P1): " + count.get());
    }

    public static long getDirSize(AOCDirectory directory, AtomicLong count) {
        long dirSize = 0;
        // Loop through child directories
        for (AOCDirectory childDir : directory.directories.values()) {
            long childDirSize = getDirSize(childDir, count);

            dirSize += childDirSize;
        }
        // Loop through files
        for (AOCFile file : directory.files.values()) {
            dirSize += file.size;
        }
        if (dirSize <= 100000) count.getAndAdd(dirSize);
        return dirSize;
    }


    static class AOCDirectory {
        public final String name;
        public final AOCDirectory parent;
        public final Map<String, AOCFile> files = new HashMap<>();
        public final Map<String, AOCDirectory> directories = new HashMap<>();

        public AOCDirectory(String name, AOCDirectory parent) {
            this.name = name;
            this.parent = parent;
        }
    }

    static class AOCFile {
        public final AOCDirectory directory;
        public final String name;
        public final long size;

        public AOCFile(AOCDirectory directory, String name, long size) {
            this.directory = directory;
            this.name = name;
            this.size = size;
        }
    }
}
