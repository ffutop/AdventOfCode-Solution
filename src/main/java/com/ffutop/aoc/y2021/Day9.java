package com.ffutop.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day9 extends BasicDay {

    private static final int[][] DIRECTIONS = new int[][] {{1,0},{-1,0},{0,1},{0,-1}};

    public static void main(String[] args) {
        Day9 day9 = new Day9();
        System.out.println(day9.solve1());
        System.out.println(day9.solve2());
    }

    private int solve1() {
        char[][] map = readGrid();
        int rows = map.length;
        int cols = map[0].length;
        int sum = 0;
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                boolean notLower = false;
                for (int index=0;index<DIRECTIONS.length;index++) {
                    int nx=row+DIRECTIONS[index][0];
                    int ny=col+DIRECTIONS[index][1];
                    if (nx<0||nx>=rows||ny<0||ny>=cols) {
                        continue;
                    }
                    notLower |= map[row][col] >= map[nx][ny];
                }
                if (notLower == false) {
                    sum += (1+map[row][col]-'0');
                }
            }
        }
        return sum;
    }

    private int solve2() {
        Basin basin = new Basin();
        basin.map = readGrid();
        basin.rows = basin.map.length;
        basin.cols = basin.map[0].length;
        basin.visited = new boolean[basin.rows][basin.cols];
        List<Integer> counterList = new ArrayList<>();
        for (int row=0;row<basin.rows;row++) {
            for (int col=0;col<basin.cols;col++) {
                AtomicInteger counter = new AtomicInteger();
                if (!basin.visited[row][col] && basin.map[row][col] != '9') {
                    dfs(basin, row, col, counter);
                }
                counterList.add(counter.get());
            }
        }
        counterList.sort((x,y)->y-x);
        return counterList.get(0) * counterList.get(1) * counterList.get(2);
    }

    private void dfs(Basin basin, int x, int y, AtomicInteger counter) {
        counter.incrementAndGet();
        basin.visited[x][y] = true;
        for (int index=0;index<DIRECTIONS.length;index++) {
            int nx = x + DIRECTIONS[index][0];
            int ny = y + DIRECTIONS[index][1];
            if (nx<0||nx>=basin.rows||ny<0||ny>=basin.cols||basin.map[nx][ny]=='9'||basin.visited[nx][ny]) {
                continue;
            }
            dfs(basin, nx, ny, counter);
        }
    }

    private class Basin {
        char[][] map;
        int rows, cols;
        boolean[][] visited;
    }
}
