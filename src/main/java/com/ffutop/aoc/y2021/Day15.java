package com.ffutop.aoc.y2021;

import java.util.PriorityQueue;

/**
 * @author fangfeng
 * @since 2021-12-18
 */
public class Day15 extends BasicDay {

    private static final int[][] DIRECTION = new int[][] {{-1,0},{1,0},{0,1},{0,-1}};

    public static void main(String[] args) {
        Day15 day15 = new Day15();

        int[][] map = day15.readIntGrid();
        System.out.println(day15.solve(map));

        map = day15.times5(map);
        System.out.println(day15.solve(map));
    }

    private int[][] times5(int[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        int[][] newMap = new int[rows*5][cols*5];
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                newMap[row][col] = map[row][col];
            }
        }
        for (int scaleRow=0;scaleRow<5;scaleRow++) {
            for (int scaleCol=0;scaleCol<5;scaleCol++) {
                if (scaleRow==0&&scaleCol==0) {
                    continue;
                }
                for (int row=0;row<rows;row++) {
                    for (int col=0;col<cols;col++) {
                        if (scaleRow == 0) {
                            newMap[scaleRow * rows + row][scaleCol * cols + col] = addon(newMap[scaleRow*rows+row][(scaleCol-1)*cols+col]);
                        } else {
                            newMap[scaleRow*rows+row][scaleCol*cols+col]=addon(newMap[(scaleRow-1)*rows+row][scaleCol*cols+col]);
                        }
                    }
                }
            }
        }
        return newMap;
    }

    private int solve(int[][] map) {
        int rows = map.length;
        int cols = map[0].length;
        int[][] riskMap = new int[rows][cols];
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                riskMap[row][col] = Integer.MAX_VALUE;
            }
        }
        PriorityQueue<Risk> queue = new PriorityQueue<Risk>((x,y)->x.risk-y.risk);
        queue.add(new Risk(0, 0, 0));
        riskMap[0][0]=0;
        while (!queue.isEmpty()) {
            Risk current = queue.poll();
            if (riskMap[current.x][current.y] != current.risk) {
                continue;
            }
            if (current.x+1==rows&&current.y+1==cols) {
                return current.risk;
            }
            for (int i=0;i<DIRECTION.length;i++) {
                int nx = current.x + DIRECTION[i][0];
                int ny = current.y + DIRECTION[i][1];
                if (nx>=0&&nx<rows&&ny>=0&&ny<cols&&current.risk + map[nx][ny] < riskMap[nx][ny]) {
                    riskMap[nx][ny] = current.risk + map[nx][ny];
                    queue.add(new Risk(nx, ny, riskMap[nx][ny]));
                }
            }
        }
        return -1;
    }

    private int addon(int x) {
        return x==9 ? 1 : x+1;
    }

    private static class Risk {
        int x, y, risk;

        public Risk(int x, int y, int risk) {
            this.x = x;
            this.y = y;
            this.risk = risk;
        }
    }

}
