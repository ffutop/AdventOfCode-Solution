package com.ffutop.aoc.y2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day7 extends BasicDay {

    public static void main(String[] args) {
        Day7 day7 = new Day7();
        System.out.println(day7.solve1());
        System.out.println(day7.solve2());
    }

    private int solve1() {
        List<Integer> list = Arrays.stream(readLists().get(0).split(",")).map(s->Integer.valueOf(s)).collect(Collectors.toList());
        list.sort((x,y)->x-y);
        int min = list.get(0);
        int max = list.get(list.size()-1);
        int sumUpper = list.stream().mapToInt(x->x).sum();
        int minFuel = Integer.MAX_VALUE;
        int sumLower = 0;
        int countUpper = list.size();
        int countLower = 0;
        for (int position=min;position<=max;position++) {
            while (countLower < list.size() && list.get(countLower) < position) {
                sumLower += list.get(countLower);
                sumUpper -= list.get(countLower);
                countLower++;
                countUpper--;
            }
            minFuel = Math.min(minFuel, (sumUpper-countUpper*position) + (countLower*position-sumLower));
        }

        return minFuel;
    }

    private int solve2() {
        List<Integer> list = Arrays.stream(readLists().get(0).split(",")).map(s->Integer.valueOf(s)).collect(Collectors.toList());
        list.sort((x,y)->x-y);
        int min = list.get(0);
        int max = list.get(list.size()-1);
        int minFuel = Integer.MAX_VALUE;
        for (int position=min;position<=max;position++) {
            int fuel = 0;
            for (Integer current : list) {
                fuel += fuel(Math.abs(position-current));
            }
            minFuel = Math.min(fuel, minFuel);
        }

        return minFuel;
    }

    private int fuel(int dist) {
        return (1+dist) * dist / 2;
    }
}
