package com.ffutop.aoc.y2021;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fangfeng
 * @since 2021-12-18
 */
public class Day14 extends BasicDay {


    public static void main(String[] args) {
        Day14 day14 = new Day14();
        System.out.println(day14.solve1());
        System.out.println(day14.solve2());
    }

    private Polymer parseInput() {
        Polymer polymer = new Polymer();
        readLists((line, lineNo) -> {
            if (lineNo == 0) {
                polymer.template = line;
            } else if (line.contains(" -> ")) {
                String[] tokens = line.split(" -> ");
                polymer.map.put(tokens[0], tokens[1]);
            }
            return null;
        });
        return polymer;
    }

    private int solve1() {
        Polymer polymer = parseInput();
        String template = process(polymer.template, polymer.map, 10);
        int[] alpha = new int[26];
        for (char c : template.toCharArray()) {
            alpha[c-'A']++;
        }
        int min = Arrays.stream(alpha).filter(val->val!=0).min().getAsInt();
        int max = Arrays.stream(alpha).max().getAsInt();
        return max - min;
    }

    private long solve2() {
        Polymer polymer = parseInput();
        String template = process(polymer.template, polymer.map, 20);
        Map<String, int[]> round20Map = new HashMap<>();
        for (Map.Entry<String, String> entry : polymer.map.entrySet()) {
            String round20 = process(entry.getKey(), polymer.map, 20);
            int[] alpha = new int[26];
            for (char c : round20.toCharArray()) {
                alpha[c-'A']++;
            }
            round20Map.put(entry.getKey(), alpha);
        }

        long[] alpha = new long[26];
        char[] chars = template.toCharArray();
        int length = template.length();
        alpha[chars[0]-'A']++;
        for (int index=1;index<length;index++) {
            String key = new StringBuilder().append(chars[index-1]).append(chars[index]).toString();
            int[] partAlpha = round20Map.get(key);
            for (int i=0;i<26;i++) {
                alpha[i] += partAlpha[i];
            }
            alpha[chars[index-1]-'A']--;
        }
        long min = Arrays.stream(alpha).filter(val->val!=0).min().getAsLong();
        long max = Arrays.stream(alpha).max().getAsLong();
        return max - min;
    }

    private String process(String template, Map<String, String> map, int rounds) {
        for (int round=0;round<rounds;round++) {
            int length = template.length();
            StringBuilder newTemplate = new StringBuilder().append(template.charAt(0));
            for (int index=1;index<length;index++) {
                String pair = template.substring(index-1, index+1);
                if (map.containsKey(pair)) {
                    newTemplate.append(map.get(pair));
                }
                newTemplate.append(template.charAt(index));
            }
            template = newTemplate.toString();
        }
        return template;
    }

    private static class Polymer {
        String template;
        Map<String, String> map = new HashMap<>();
    }

}
