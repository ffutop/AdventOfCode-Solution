package com.ffutop.aoc.y2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day5 extends BasicDay {

    public static void main(String[] args) {
        Day5 day5 = new Day5();

        System.out.println(day5.solve1());
        System.out.println(day5.solve2());
    }

    private List<Rectangle> parseInput() {
        return readLists(s->{
            List<Integer> points = Arrays.stream(s.split(" -> ")).flatMap(part->Arrays.stream(part.split(","))).map(str->Integer.valueOf(str)).collect(Collectors.toList());
            Rectangle rectangle = new Rectangle(points);
            return rectangle;
        });
    }

    private int solve1() {
        List<Rectangle> rectangleList = parseInput();
        int maxValue = rectangleList.stream().map(Rectangle::max).max((x,y)->x-y).get();
        int[][] grid = new int[maxValue+1][maxValue+1];

        int counter = 0;
        for (Rectangle rectangle : rectangleList) {
            if (rectangle.x1 != rectangle.x2 && rectangle.y1 != rectangle.y2) {
                continue;
            }

            int dx = rectangle.x1 - rectangle.x2;
            int dy = rectangle.y1 - rectangle.y2;
            for (int x=0;x<=Math.abs(dx);x++) {
                for (int y=0;y<=Math.abs(dy);y++) {
                    int row = rectangle.x2 + move(x, dx);
                    int col = rectangle.y2 + move(y, dy);
                    grid[row][col]++;
                    if (grid[row][col] == 2) {
                        counter++;
                    }
                }
            }
        }

        return counter;
    }

    private int solve2() {
        List<Rectangle> rectangleList = parseInput();
        int maxValue = rectangleList.stream().map(Rectangle::max).max((x,y)->x-y).get();
        int[][] grid = new int[maxValue+1][maxValue+1];

        int counter = 0;
        for (Rectangle rectangle : rectangleList) {
            int dx = rectangle.x1 - rectangle.x2;
            int dy = rectangle.y1 - rectangle.y2;
            if (rectangle.x1 != rectangle.x2 && rectangle.y1 != rectangle.y2 && Math.abs(dx) != Math.abs(dy)) {
                continue;
            }

            int max = Math.max(Math.abs(dx), Math.abs(dy));
            for (int z=0;z<=max;z++) {
                int row = rectangle.x2 + move(z, dx);
                int col = rectangle.y2 + move(z, dy);
                grid[row][col]++;
                if (grid[row][col] == 2) {
                    counter++;
                }
            }
        }

        return counter;
    }

    private int move(int x, int dx) {
        if (dx == 0) {
            return 0;
        }
        return x * dx / Math.abs(dx);
    }

    private static class Rectangle {
        int x1, x2, y1, y2;

        public Rectangle(List<Integer> points) {
            this.x1 = points.get(0);
            this.x2 = points.get(2);
            this.y1 = points.get(1);
            this.y2 = points.get(3);
        }

        public int max() {
            return Math.max(x1, Math.max(x2, Math.max(y1, y2)));
        }
    }
}
