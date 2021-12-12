package com.ffutop.aoc.y2021;

import java.util.List;

/**
 * @author fangfeng
 * @since 2021-12-11
 */
public class Day1 extends BasicDay {

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        System.out.println(day1.solve1());
        System.out.println(day1.solve2());
    }

    private int solve1() {
        List<Integer> list = readLists(s->Integer.valueOf(s));
        int prev = list.get(0);
        int increments = 0;
        for (Integer curr : list) {
            if (curr > prev) {
                increments ++;
            }
            prev = curr;
        }
        return increments;
    }

    private int solve2() {
        List<Integer> list = readLists(s->Integer.valueOf(s));
        int increments = 0;
        for (int i=3;i<list.size();i++) {
            if (list.get(i) > list.get(i-3)) {
                increments++;
            }
        }
        return increments;
    }
}
