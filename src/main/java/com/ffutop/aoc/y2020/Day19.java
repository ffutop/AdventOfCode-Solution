package com.ffutop.aoc.y2020;

import java.util.*;

public class Day19 extends BasicDay {

    private Map<Integer, String> ruleMap;
    private Map<Integer, Set<String>> validMap;

    public static void main(String[] args) {
        Day19 day19 = new Day19();
        System.out.println(day19.solve1());
        System.out.println(day19.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        ruleMap = new HashMap<>();
        validMap = new HashMap<>();
        Set<String> set = new HashSet<>();
        for (String line : list) {
            if (line.contains(":")) {
                String[] tokens = line.split(": ");
                if ("\"a\"".equals(tokens[1]) || "\"b\"".equals(tokens[1])) {
                    ruleMap.put(Integer.parseInt(tokens[0]), tokens[1].substring(1,2));
                    validMap.put(Integer.parseInt(tokens[0]), new HashSet<>(Arrays.asList(tokens[1].substring(1,2))));
                } else {
                    ruleMap.put(Integer.parseInt(tokens[0]), tokens[1]);
                }
            } else {
                set.add(line);
            }
        }
        dfs(0);
        int count = 0;
        for (String str : set) {
            count += validMap.get(0).contains(str) ? 1 : 0;
        }
        return count;
    }

    private int solve2() {
        List<String> list = readLists();
        int counter = 0;

        Set<String> set31 = validMap.get(31);
        Set<String> set42 = validMap.get(42);
        for (String line : list) {
            if (line.contains(":")) {
                continue;
            }
            if (line.length() % 8 != 0) {
                continue;
            }

            int count42 = 0;
            int count31 = 0;
            boolean valid = true;
            for (int i=0;i<line.length();i+=8) {
                String part = line.substring(i, i+8);
                if (set31.contains(part)) {
                    count31 ++;
                } else if (set42.contains(part)) {
                    if (count31 != 0) {
                        valid = false;
                        break;
                    }
                    count42 ++;
                } else {
                    valid = false;
                    break;
                }
            }
            if (count42 > count31 && count31 != 0 && valid) {
                counter ++;
            }

        }

        return counter;
    }

    private void dfs(Integer index) {
        if (validMap.containsKey(index)) {
            return;
        }
        String value = ruleMap.get(index);
        String[] tokens = value.split(" \\| ");
        Set<String> set = new HashSet<>();
        for (String token : tokens) {
            String[] childIndexes = token.split(" ");
            Set<String> prevSet = new HashSet<String>() {{
                add("");
            }};
            for (String childIndex : childIndexes) {
                Integer childIndexInt = Integer.parseInt(childIndex.trim());
                dfs(childIndexInt);
                Set<String> nextSet = new HashSet<>();
                for (String previous : prevSet) {
                    for (String next : validMap.get(childIndexInt)) {
                        nextSet.add(previous + next);
                    }
                }
                prevSet = nextSet;
            }
            set.addAll(prevSet);
        }
        validMap.put(index, set);
    }
}
