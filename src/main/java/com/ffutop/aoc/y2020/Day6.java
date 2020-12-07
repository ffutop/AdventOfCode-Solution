package com.ffutop.aoc.y2020;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fangfeng
 * @since 2020-12-06
 */
public class Day6 extends BasicDay {

    public static void main(String[] args) {
        Day6 day6 = new Day6();
        System.out.println(day6.solve1());
        System.out.println(day6.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        list.add("");

        int counter = 0;
        Set<Character> set = new HashSet<>();
        for (String str : list) {
            if ("".equals(str)) {
                counter += set.size();
                set = new HashSet<>();
            } else {
                for (char c : str.toCharArray()) {
                    set.add(c);
                }
            }
        }
        return counter;
    }

    private int solve2() {
        List<String> list = readLists();
        list.add("");

        int counter = 0;
        int[] arr = new int[26];
        int count = 0;
        for (String str : list) {
            if ("".equals(str)) {
                for (int i=0;i<26;i++) {
                    if (arr[i] == count) {
                        counter++;
                    }
                }
                count = 0;
                arr = new int[26];
            } else {
                for (char c : str.toCharArray()) {
                    arr[c-'a']++;
                }
                count++;
            }
        }
        return counter;
    }
}
