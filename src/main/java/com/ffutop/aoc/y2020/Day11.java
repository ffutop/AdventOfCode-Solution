package com.ffutop.aoc.y2020;

public class Day11 extends BasicDay {

    private static final int[][] DIRECTION = new int[][] {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
    private static final int DIRECTION_LENGTH = DIRECTION.length;

    public static void main(String[] args) {
        Day11 day11 = new Day11();
        System.out.println(day11.solve1());
        System.out.println(day11.solve2());
    }

    private int solve1() {
        int row = 98;
        int col = 96;
        char[][][] map = new char[2][row][col];
        readLists((line, lineNo) -> {
            char[] cs = line.toCharArray();
            int len = cs.length;
            for (int i=0;i<len;i++) {
                map[0][lineNo][i] = cs[i];
                map[1][lineNo][i] = cs[i];
            }
            return null;
        });

        int modified = -1;
        int occupied = 0;
        for (int step=1;modified != 0;step++) {
            int curr = step % 2;
            int prev = 1 - curr;
            modified = 0;
            occupied = 0;
            for (int x=0;x<row;x++) {
                for (int y=0;y<col;y++) {
                    if (map[prev][x][y] == '.') {
                        continue;
                    }
                    int nearOccupied = 0;
                    for (int z=0;z<DIRECTION_LENGTH;z++) {
                        int nx = x + DIRECTION[z][0];
                        int ny = y + DIRECTION[z][1];
                        if (0 <= nx && nx < row && 0 <= ny && ny < col && map[prev][nx][ny] == '#') {
                            nearOccupied++;
                        }
                    }
                    if (nearOccupied == 0) {
                        map[curr][x][y] = '#';
                    } else if (nearOccupied >= 4) {
                        map[curr][x][y] = 'L';
                    } else {
                        map[curr][x][y] = map[prev][x][y];
                    }

                    modified += (map[curr][x][y] == map[prev][x][y] ? 0 : 1);
                    occupied += (map[curr][x][y] == '#' ? 1 : 0);
                }
            }
        }
        return occupied;
    }

    private int solve2() {
        int row = 98;
        int col = 96;
        char[][][] map = new char[2][row][col];
        readLists((line, lineNo) -> {
            char[] cs = line.toCharArray();
            int len = cs.length;
            for (int i=0;i<len;i++) {
                map[0][lineNo][i] = cs[i];
                map[1][lineNo][i] = cs[i];
            }
            return null;
        });

        int modified = -1;
        int occupied = 0;
        for (int step=1;modified != 0;step++) {
            int curr = step % 2;
            int prev = 1 - curr;
            modified = 0;
            occupied = 0;
            for (int x=0;x<row;x++) {
                for (int y=0;y<col;y++) {
                    if (map[prev][x][y] == '.') {
                        continue;
                    }
                    int nearOccupied = 0;
                    for (int z=0;z<DIRECTION_LENGTH;z++) {
                        for (int w=1;true;w++) {
                            int nx = x + DIRECTION[z][0] * w;
                            int ny = y + DIRECTION[z][1] * w;
                            if (nx < 0 || nx >= row || ny < 0 || ny >= col) {
                                break;
                            }
                            if (map[prev][nx][ny] == '#') {
                                nearOccupied++;
                                break;
                            } else if (map[prev][nx][ny] == 'L') {
                                break;
                            } else {
                                continue;
                            }
                        }
                    }
                    if (nearOccupied == 0) {
                        map[curr][x][y] = '#';
                    } else if (nearOccupied >= 5) {
                        map[curr][x][y] = 'L';
                    } else {
                        map[curr][x][y] = map[prev][x][y];
                    }

                    modified += (map[curr][x][y] == map[prev][x][y] ? 0 : 1);
                    occupied += (map[curr][x][y] == '#' ? 1 : 0);
                }
            }
        }
        return occupied;
    }
}
