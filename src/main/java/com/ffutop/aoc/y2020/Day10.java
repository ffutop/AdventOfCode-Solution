package com.ffutop.aoc.y2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 extends BasicDay {

    public static void main(String[] args) {
        Day10 day10 = new Day10();
        System.out.println(day10.solve1());
        System.out.println(day10.solve2());
    }

    private int solve1() {
        List<Integer> list = readLists(s -> Integer.valueOf(s));
        list.sort((x, y) -> x-y);
        int[] diffs = new int[4];
        int prev = 0;
        for (Integer val : list) {
            diffs[val-prev]++;
            prev = val;
        }
        return diffs[1] * (diffs[3]+1);
    }

    private long solve2() {
        List<Integer> list = readLists(s -> Integer.valueOf(s));
        list.add(0);
        list.sort((x, y) -> x-y);
        int outlet = list.get(list.size()-1) + 3;
        list.add(outlet);
        Map<Integer, Long> map = new HashMap<>();
        map.put(0, 1L);
        for (Integer val : list) {
            final long count = map.get(val);
            for (int i=1;i<=3;i++) {
                map.compute(val + i, (key, oldValue) -> count + (oldValue == null ? 0L : oldValue));
            }
        }
        return map.get(outlet);
    }
}
