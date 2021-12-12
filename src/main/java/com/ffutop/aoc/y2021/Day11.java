package com.ffutop.aoc.y2021;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day11 extends BasicDay {

    private static final int[][] DIRECTIONS = new int[][] {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

    public static void main(String[] args) {
        Day11 day11 = new Day11();
        System.out.println(day11.solve1());
        System.out.println(day11.solve2());
    }

    private int solve1() {
        Grid grid = new Grid();
        grid.map = readIntGrid();
        grid.rows = grid.map.length;
        grid.cols = grid.map[0].length;
        grid.visited = new int[grid.rows][grid.cols];
        AtomicInteger counter = new AtomicInteger();
        for (int step=1;step<=100;step++) {
            for (int row=0;row<grid.rows;row++) {
                for (int col=0;col<grid.cols;col++) {
                    grid.map[row][col] ++;
                }
            }

            for (int row=0;row<grid.rows;row++) {
                for (int col=0;col<grid.cols;col++) {
                    if (grid.map[row][col]>9 && grid.visited[row][col] != step) {
                        dfs(grid, row, col, step, counter);
                    }
                }
            }
        }
        return counter.get();
    }

    private int solve2() {
        Grid grid = new Grid();
        grid.map = readIntGrid();
        grid.rows = grid.map.length;
        grid.cols = grid.map[0].length;
        grid.visited = new int[grid.rows][grid.cols];
        for (int step=1;true;step++) {
            for (int row=0;row<grid.rows;row++) {
                for (int col=0;col<grid.cols;col++) {
                    grid.map[row][col] ++;
                }
            }

            AtomicInteger counter = new AtomicInteger();
            for (int row=0;row<grid.rows;row++) {
                for (int col=0;col<grid.cols;col++) {
                    if (grid.map[row][col]>9 && grid.visited[row][col] != step) {
                        dfs(grid, row, col, step, counter);
                    }
                }
            }
            if (counter.get() == grid.rows * grid.cols) {
                return step;
            }
        }
    }

    private void dfs(Grid grid, int x, int y, int step, AtomicInteger counter) {
        counter.incrementAndGet();
        grid.visited[x][y] = step;
        grid.map[x][y] = 0;
        for (int index=0;index<DIRECTIONS.length;index++) {
            int nx = x + DIRECTIONS[index][0];
            int ny = y + DIRECTIONS[index][1];
            if (nx<0||nx>=grid.rows||ny<0||ny>=grid.cols||grid.visited[nx][ny]==step) {
                continue;
            }
            grid.map[nx][ny]++;
            if (grid.map[nx][ny] > 9) {
                dfs(grid, nx, ny, step, counter);
            }
        }
    }

    private static class Grid {
        int[][] map;
        int rows, cols;
        int[][] visited;
    }
}
