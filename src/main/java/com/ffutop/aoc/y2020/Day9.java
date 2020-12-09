package com.ffutop.aoc.y2020;

import sun.nio.cs.ext.MacHebrew;

import java.util.*;

public class Day9 extends BasicDay {

    public static void main(String[] args) {
        Day9 day9 = new Day9();
        long solution = day9.solve1();
        System.out.println(solution);

        System.out.println(day9.solve2(solution));
    }

    private long solve1() {
        List<Long> list = readLongList();
        Queue<Long> queue = new LinkedList<>();
        Set<Long> values = new HashSet<>();
        int len = list.size();
        for (int i=0;i<len;i++) {
            long nextVal = list.get(i);
            if (i < 25) {
                queue.add(nextVal);
                values.add(nextVal);
                continue;
            } else {
                boolean valid = false;
                for (Long value : values) {
                    if (nextVal - value != value && values.contains(nextVal - value)) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    return nextVal;
                }
                queue.add(nextVal);
                values.add(nextVal);
                long prevVal = queue.poll();
                values.remove(prevVal);
            }
        }
        return -1;
    }

    private long solve2(long target) {
        List<Long> list = readLongList();
        long sum = 0;
        int leftCursor = 0, rightCursor = 0;
        int len = list.size();
        while (rightCursor < len) {
            sum += list.get(rightCursor);
            while (sum > target) {
                sum -= list.get(leftCursor);
                leftCursor++;
            }
            if (sum == target) {
                long min = list.get(leftCursor), max = list.get(leftCursor);
                for (int i=leftCursor;i<=rightCursor;i++) {
                    long val = list.get(i);
                    min = Math.min(min, val);
                    max = Math.max(max, val);
                }
                return min + max;
            }
            rightCursor ++;
        }
        return -1;
    }
}
