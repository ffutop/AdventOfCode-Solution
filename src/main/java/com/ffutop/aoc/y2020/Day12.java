package com.ffutop.aoc.y2020;

import java.util.List;

public class Day12 extends BasicDay {

    private int[][] DIRECTION = new int[][] {{0,1},{1,0},{0,-1},{-1,0}};

    public static void main(String[] args) {
        Day12 day12 = new Day12();
        System.out.println(day12.solve1());
        System.out.println(day12.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        int x = 0, y = 0;
        int face = 0;
        for (String line : list) {
            char op = line.charAt(0);
            int dist = Integer.parseInt(line.substring(1));
            if (op == 'E') {
                y += dist;
            } else if (op == 'W') {
                y -= dist;
            } else if (op == 'N') {
                x -= dist;
            } else if (op == 'S') {
                x += dist;
            } else if (op == 'L') {
                face = (face - dist/90%4 + 4) % 4;
            } else if (op == 'R') {
                face += (dist / 90);
                face %= 4;
            } else if (op == 'F') {
                x += DIRECTION[face][0] * dist;
                y += DIRECTION[face][1] * dist;
            } else {
                System.out.println("error");
            }
        }
        return Math.abs(x)+Math.abs(y);
    }

    private int solve2() {
        List<String> list = readLists();
        int x = 0, y = 0;
        int waypointX = 10, waypointY = -1;
        for (String line : list) {
            char op = line.charAt(0);
            int dist = Integer.parseInt(line.substring(1));
            if (op == 'E') {
                waypointX += dist;
            } else if (op == 'W') {
                waypointX -= dist;
            } else if (op == 'N') {
                waypointY -= dist;
            } else if (op == 'S') {
                waypointY += dist;
            } else if (op == 'L') {
                for (int round=dist/90;round!=0;round--) {
                    int tmp = waypointX;
                    waypointX = waypointY;
                    waypointY = -1 * tmp;
                }
            } else if (op == 'R') {
                for (int round=dist/90;round!=0;round--) {
                    int tmp = waypointX;
                    waypointX = -1 * waypointY;
                    waypointY = tmp;
                }
            } else if (op == 'F') {
                x += waypointX * dist;
                y += waypointY * dist;
            } else {
                System.out.println("error");
            }
        }
        return Math.abs(x)+Math.abs(y);
    }
}
