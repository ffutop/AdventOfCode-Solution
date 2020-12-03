package com.ffutop.aoc.y2020;

import java.util.List;

/**
 * @author fangfeng
 * @since 2020-12-03
 */
public class Day3 extends BasicDay {

    public static void main(String[] args) {
        Day3 day3 = new Day3();
        // part 1
        System.out.println(day3.solve(1, 3));

        // part 2
        long answer = 1L;
        int[][] add = new int[][] {{1,1},{1,3},{1,5},{1,7},{2,1}};
        for (int i=0;i<add.length;i++) {
            answer *= day3.solve(add[i][0], add[i][1]);
        }
        System.out.println(answer);
    }

    private int solve(int xAdd, int yAdd) {
        List<String> list = readLists();
        int row = list.size();
        int col = list.get(0).length();
        int x = 0, y = 0;
        int counter = 0;
        while (x != row-1) {
            x += xAdd;
            y += yAdd;
            counter += list.get(x).charAt(y%col) == '#' ? 1 : 0;
        }
        return counter;
    }
}
