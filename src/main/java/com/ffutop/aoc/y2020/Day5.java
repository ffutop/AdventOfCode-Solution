package com.ffutop.aoc.y2020;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author fangfeng
 * @since 2020-12-05
 */
public class Day5 extends BasicDay {

    public static void main(String[] args) {
        Day5 day5 = new Day5();
        System.out.println(day5.solve1());
        System.out.println(day5.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        int max = 0;
        for (String str : list) {
            int[] seats = seats(str);
            max = Math.max(max, seats[0] * 8 + seats[1]);
        }
        return max;
    }

    private int solve2() {
        List<String> list = readLists();
        Set<Integer> set = new TreeSet<>();
        for (String str : list) {
            int[] seats = seats(str);
            set.add(seats[0] * 8 + seats[1]);
        }
        int prev = -1;
        for (Integer seat : set) {
            if (prev != -1 && seat != prev + 1) {
                return prev+1;
            }
            prev = seat;
        }
        return -1;
    }

    private int[] seats(String string) {
        String rowStr = string.substring(0, 7);
        int left = 0, right = 127;
        for (char c : rowStr.toCharArray()) {
            if (c == 'F') {
                right = (left + right) / 2;
            } else {
                left = (left + right) / 2 + 1;
            }
        }
        int row = left;

        String colStr = string.substring(7);
        left = 0; right = 7;
        for (char c : colStr.toCharArray()) {
            if (c == 'L') {
                right = (left + right) / 2;
            } else {
                left = (left + right) / 2 + 1;
            }
        }
        int col = left;
        return new int[] {row, col};
    }

}
