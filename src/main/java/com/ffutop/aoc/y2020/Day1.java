package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-01
 */
public class Day1 extends BasicDay {

    private static final int target = 2020;

    public static void main(String[] args) {
        Day1 day1 = new Day1();
        System.out.println(day1.solve1());
        System.out.println(day1.solve2());
    }

    private int solve1() {
        List<Integer> list = readLists(s -> Integer.valueOf(s));
        Set<Integer> set = new HashSet<>(list);
        for (Integer val : list) {
            if (set.contains(target - val)) {
                return val * (target - val);
            }
        }
        return -1;
    }

    private int solve2() {
        List<Integer> singleValues = readLists(s -> Integer.valueOf(s));
        int len = singleValues.size();
        Map<Integer, Integer> doubleValueMultipleMap = new HashMap<>();
        for (int i=0;i<len;i++) {
            for (int j=i+1;j<len;j++) {
                Integer x = singleValues.get(i);
                Integer y = singleValues.get(j);
                doubleValueMultipleMap.put(x+y, x*y);
            }
        }
        for (Integer value : singleValues) {
            Integer mul = doubleValueMultipleMap.getOrDefault(target - value, -1);
            if (mul != -1) {
                return mul * value;
            }
        }
        return -1;
    }
}
