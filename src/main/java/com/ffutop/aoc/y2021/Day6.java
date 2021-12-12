package com.ffutop.aoc.y2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day6 extends BasicDay {

    public static void main(String[] args) {
        Day6 day6 = new Day6();
        System.out.println(day6.solve(80));
        System.out.println(day6.solve(256));
    }

    private long solve(int days) {
        List<Integer> lanternFishList = Arrays.stream(readLists().get(0).split(",")).map(s->Integer.valueOf(s)).collect(Collectors.toList());
        long[] timer = new long[9];
        for (Integer lanternFish : lanternFishList) {
            timer[lanternFish]++;
        }

        for (int day=1;day<=days;day++) {
            long timer0 = timer[0];
            for (int i=0;i<8;i++) {
                timer[i] = timer[i+1];
            }
            timer[6] += timer0;
            timer[8] = timer0;
        }

        return Arrays.stream(timer).sum();
    }
}
