package com.ffutop.aoc.y2021;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangfeng
 * @since 2021-12-13
 */
public class Day13 extends BasicDay {

    private static final int X = 0;
    private static final int Y = 1;

    public static void main(String[] args) {
        Day13 day13 = new Day13();
        System.out.println(day13.solve(1));
        System.out.println(day13.solve(Integer.MAX_VALUE));
    }

    private Paper parseInput() {
        List<String> list = readLists();
        Paper paper = new Paper();
        List<int[]> pointList = new ArrayList<>();
        for (String str : list) {
            if ("".equals(str)) {
                continue;
            } else if (str.startsWith("fold along ")) {
                String[] tokens = str.substring("fold along ".length()).split("=");
                paper.foldList.add(new int[] {"x".equals(tokens[0]) ? X : Y, Integer.valueOf(tokens[1])});
            } else {
                String[] tokens = str.split(",");
                pointList.add(new int[] {Integer.valueOf(tokens[1]), Integer.valueOf(tokens[0])});
            }
        }
        for (int[] point : pointList) {
            paper.rows = Math.max(paper.rows, point[0]);
            paper.cols = Math.max(paper.cols, point[1]);
        }

        paper.map = new boolean[paper.rows+1][paper.cols+1];
        for (int[] point : pointList) {
            paper.map[point[0]][point[1]] = true;
        }
        return paper;
    }

    private int solve(int fold) {
        Paper paper = parseInput();
        for (int i=0;i<fold && i<paper.foldList.size();i++) {
            if (paper.foldList.get(i)[0] == X) {
                int mid = paper.foldList.get(i)[1];
                for (int dist=1;true;dist++) {
                    if (mid + dist > paper.cols || mid - dist < 0) {
                        break;
                    }
                    for (int row=0;row<=paper.rows;row++) {
                        paper.map[row][mid - dist] |= paper.map[row][mid + dist];
                    }
                }
                paper.cols = mid-1;
            } else {
                int mid = paper.foldList.get(i)[1];
                for (int dist=1;true;dist++) {
                    if (mid + dist > paper.rows || mid - dist < 0) {
                        break;
                    }
                    for (int col=0;col<=paper.cols;col++) {
                        paper.map[mid-dist][col] |= paper.map[mid+dist][col];
                    }
                }
                paper.rows = mid-1;
            }
        }

        int count = 0;
        for (int row=0;row<=paper.rows;row++) {
            for (int col=0;col<=paper.cols;col++) {
                count += (paper.map[row][col] ? 1:0);
            }
        }
        paper.print();
        return count;
    }

    private static class Paper {
        int rows = 0, cols = 0;
        boolean[][] map;
        List<int[]> foldList = new ArrayList<>();

        public void print() {
            for (int row=0;row<=rows;row++) {
                for (int col=0;col<=cols;col++) {
                    System.out.print(map[row][col]?"##":"..");
                }
                System.out.println();
            }
        }
    }

}
