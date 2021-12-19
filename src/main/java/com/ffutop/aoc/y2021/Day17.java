package com.ffutop.aoc.y2021;

import javax.swing.plaf.IconUIResource;

/**
 * @author fangfeng
 * @since 2021-12-19
 */
public class Day17 extends BasicDay {

    public static void main(String[] args) {
        Day17 day17 = new Day17();
        System.out.println(day17.solve1());
        System.out.println(day17.solve2());
    }

    private Trench parseInput() {
        String input = readLists().get(0);
        Trench trench = new Trench();
        String[] xy = input.substring("target area: ".length()).split(", ");
        String[] value = xy[0].substring(2).split("\\.\\.");
        trench.x = new int[] {Integer.valueOf(value[0]), Integer.valueOf(value[1])};
        value = xy[1].substring(2).split("\\.\\.");
        trench.y = new int[] {Integer.valueOf(value[0]), Integer.valueOf(value[1])};
        return trench;
    }

    private int solve1() {
        Trench trench = parseInput();

        // 仅从 y 向考虑，向上抛，并提供最大的初始速度，可保证最高
        // 同时，从 y 向看，起始速度 与 下降时 x=0 位置的速度 互为相反数
        // 故下降到 x=0 后下一秒，y 向速度 = -(起始速度+1)
        //
        // 横向 x 的速度持续减少并最终保持 0。观察输入可知 x=277..318
        // 起始速度 Vx=24 可保持横向速度减为 0 时，x = 300 。符合横向要求
        int vy = (-trench.y[0])-1;
        return (vy+1) * vy / 2;
    }

    private int solve2() {
        Trench trench = parseInput();
        int count = 0;
        for (int vx=0;vx<=trench.x[1];vx++) {
            for (int vy=trench.y[0];vy<=(-trench.y[0])-1;vy++) {
                count += simulate(trench, vx, vy) ? 1 : 0;
            }
        }
        return count;
    }

    private boolean simulate(Trench trench, int vx, int vy) {
        int x = 0, y = 0;
        for (int second=1;true;second++) {
            if (vx != 0) {
                x += vx--;
            }
            y += vy--;
            if (x > trench.x[1] || y < trench.y[0]) {
                return false;
            } if (trench.x[0] <= x && x <= trench.x[1] && trench.y[0] <= y && y <= trench.y[1]) {
                return true;
            }
        }
    }

    private static class Trench {
        int x[];
        int y[];
    }
}
