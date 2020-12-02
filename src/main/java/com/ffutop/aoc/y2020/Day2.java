package com.ffutop.aoc.y2020;

import java.util.List;

/**
 * @author fangfeng
 * @since 2020-12-02
 */
public class Day2 extends BasicDay {

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        System.out.println(day2.solve1());
        System.out.println(day2.solve2());
    }

    private int solve1() {
        int result = 0;
        List<String> list = readLists();
        int min, max;
        char c;
        String pattern;
        for (String str : list) {
            String[] tokens = str.split(" ");
            min = Integer.parseInt(tokens[0].split("-")[0]);
            max = Integer.parseInt(tokens[0].split("-")[1]);
            c = tokens[1].charAt(0);
            pattern = tokens[2];

            int counter = 0;
            for (char pc : pattern.toCharArray()) {
                counter += (pc == c ? 1 : 0);
            }

            if (min <= counter && counter <= max) {
                result ++;
            }
        }
        return result;
    }

    private int solve2() {
        int result = 0;
        List<String> list = readLists();
        int min, max;
        char c;
        String pattern;
        for (String str : list) {
            String[] tokens = str.split(" ");
            min = Integer.parseInt(tokens[0].split("-")[0]);
            max = Integer.parseInt(tokens[0].split("-")[1]);
            c = tokens[1].charAt(0);
            pattern = tokens[2];

            int counter = (pattern.charAt(min-1) == c ? 1 : 0) + (pattern.charAt(max-1) == c ? 1 : 0);
            result += (counter == 1 ? 1 : 0);
        }
        return result;
    }

}
