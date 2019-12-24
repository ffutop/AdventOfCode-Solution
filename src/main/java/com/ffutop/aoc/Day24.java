package com.ffutop.aoc;

import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day24 {

    private static final int[][] directory = new int[][] {{-1,0}, {1,0}, {0,1}, {0,-1}};
    private static final int ROW=5;
    private static final int COL=5;
    private static final char BUG = '#';
    private static final char EMPTY= 0;
    private static final char QUES = '?';

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val1 = part1.solve();
        System.out.println(String.format("Part1. biodiversity rating: %d", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part2. bugs are present after 200 minutes: %d", val2));
    }

    private static char BugsOrEmpty(char current, int bugs) {
        if (current == BUG && bugs != 1) {
            return EMPTY;
        } else if (current == EMPTY && (bugs == 1 || bugs == 2)) {
            return BUG;
        }
        return current;
    }

    private static class Part1 {

        private char[][][] map = new char[2][ROW][COL];
        private HashSet<Integer> points = new HashSet<>();

        private int countBugs(int row, int col, int cursor) {
            int count = 0;
            for (int i=0;i<4;i++) {
                int x = row + directory[i][0];
                int y = col + directory[i][1];
                if (x<0||x>=ROW || y<0||y>=COL) {
                    continue;
                }
                count += map[cursor][x][y] == '#' ? 1 : 0;
            }
            return count;
        }

        private int toInt(int cursor) {
            int point = 0;
            for (int row=ROW-1;row>=0;row--) {
                for (int col=COL-1;col>=0;col--) {
                    point <<= 1;
                    point |= (map[cursor][row][col] == '#' ? 1 : 0);
                }
            }
            return point;
        }

        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day24.in");
            String data;
            int cursor = 0;
            for (int row=0;(data = br.readLine()) != null;row++) {
                for (int col=0;col<data.length();col++) {
                    map[cursor][row][col] = data.charAt(col) == '.' ? EMPTY : BUG;
                }
            }

            while (true) {
                int prev = cursor;
                cursor++;
                for (int row=0;row<ROW;row++) {
                    for (int col=0;col<COL;col++) {
                        int bugs = countBugs(row, col, prev%2);
                        map[cursor%2][row][col] = BugsOrEmpty(map[prev%2][row][col], bugs);
                    }
                }
                int point = toInt(cursor%2);
                if (points.contains(point)) {
                    return point;
                }
                points.add(point);
            }
        }
    }

    private static class Part2 {

        private static final int TIMES = 200;
        private HashMap<Integer, char[][]>[] grids = new HashMap[] {new HashMap(), new HashMap()};
        private int minute = 0;
        private static final int SPECIFIC_ROW = 2;
        private static final int SPECIFIC_COL = 2;

        private void init() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day24.in");
            String data;
            char[][] grid = new char[ROW][COL];
            for (int row=0;(data = br.readLine()) != null;row++) {
                for (int col=0;col<data.length();col++) {
                    grid[row][col] = data.charAt(col);
                    if (grid[row][col] == '.') {
                        grid[row][col] = EMPTY;
                    }
                }
            }
            grids[(minute&1)].put(0, grid);
        }

        private char getGrid(int depth, int row, int col) {
            if (grids[(minute&1)].containsKey(depth) == false) {
                grids[(minute&1)].put(depth, new char[ROW][COL]);
                grids[(minute&1)].get(depth)[SPECIFIC_ROW][SPECIFIC_COL] = QUES;
            }
            return grids[(minute&1)].get(depth)[row][col];
        }

        private void setGrid(int depth, int row, int col, char symbol) {
            if (grids[(minute&1)^1].containsKey(depth) == false) {
                grids[(minute&1)^1].put(depth, new char[ROW][COL]);
                grids[(minute&1)^1].get(depth)[SPECIFIC_ROW][SPECIFIC_COL] = QUES;
            }
            grids[(minute&1)^1].get(depth)[row][col] = symbol;
        }

        private int countBugs(int row, int col, int depth) {
            int count = 0;
            for (int i=0;i<4;i++) {
                int x = row + directory[i][0];
                int y = col + directory[i][1];
                if (x<0 || x>=ROW || y<0 || y>=COL) {
                    // has outer tiles
                    count += BUG == getGrid(depth-1, SPECIFIC_ROW+directory[i][0], SPECIFIC_COL+directory[i][1]) ? 1 : 0;
                } else if (x == SPECIFIC_ROW && y == SPECIFIC_COL) {
                    // has inner tiles
                    if (i < 2) {
                        for (int innerCol=0;innerCol<COL;innerCol++) {
                            count += BUG == getGrid(depth+1, row<SPECIFIC_ROW?0:ROW-1, innerCol) ? 1 : 0;
                        }
                    } else {
                        for (int innerRow=0;innerRow<ROW;innerRow++) {
                            count += BUG == getGrid(depth+1, innerRow, col<SPECIFIC_COL?0:COL-1) ? 1 : 0;
                        }
                    }
                } else {
                    count += BUG == getGrid(depth, x, y) ? 1 : 0;
                }
            }
            return count;
        }

        public int solve() throws IOException {
            init();

            for (;minute<TIMES;minute++) {
                for (int depth=-minute-1;depth<=minute+1;depth++) {
                    for (int row=0;row<ROW;row++) {
                        for (int col=0;col<COL;col++) {
                            if (row == SPECIFIC_ROW && col == SPECIFIC_COL) {
                                continue;
                            }
                            int bugs = countBugs(row, col, depth);
                            setGrid(depth, row, col, BugsOrEmpty(getGrid(depth, row, col), bugs));
                        }
                    }
                }
            }

            int count = 0;
            for (int depth=-TIMES;depth<=TIMES;depth++) {
                for (int row=0;row<ROW;row++) {
                    for (int col=0;col<COL;col++) {
                        count += getGrid(depth, row, col) == BUG ? 1 : 0;
                    }
                }
            }
            return count;
        }
    }
}