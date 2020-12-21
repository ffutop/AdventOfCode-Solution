package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-20
 */
public class Day20Dup extends BasicDay {

    private List<Tile> tileList;

    public static void main(String[] args) {
        Day20Dup day20 = new Day20Dup();
        List<Tile> tileList = new ArrayList<>();
        day20.readLists((line, lineNo) -> {
            if (lineNo.intValue() % 12 == 0) {
                Tile tile = new Tile();
                tile.image = new char[10][10];
                tile.id = Integer.parseInt(line.substring(5, 9));
                tileList.add(tile);
            } else if (lineNo.intValue() % 12 == 11) {

            } else {
                Tile tile = tileList.get(tileList.size()-1);
                for (int i=0;i<10;i++) {
                    tile.image[lineNo%12-1][i] = line.charAt(i);
                }
            }
            return null;
        });
        day20.tileList = tileList;

        System.out.println(day20.solve1());
    }

    private Long solve1() {
        System.out.println(tileList.size());
        Map<Integer, Integer> map = new HashMap<>();
        int row = (int) Math.sqrt(tileList.size());
        int col = row;
        for (Tile topLeft : tileList) {
            Tile[][] tileGrid = new Tile[row][col];
            Set<Tile> used = new HashSet<>();

            tileGrid[0][0] = topLeft;
            getNextMatch(tileGrid, used, row, col, 0, 1);

            for (int i=0;i<row;i++) {
                for (int j=0;j<col;j++) {
                    if (i+j == 0) {
                        tileGrid[i][j] = topLeft;
                    } else {
                        for (Tile tile : tileList) {
                            if (used.contains(tile)) continue;

                        }
                    }

                    used.add(tileGrid[i][j]);
                }
            }
        }

        for (Tile tile : tileList) {
            int[] borders = tile.borderToInts();
            for (int border : borders) {
                map.compute(border, (key, oldValue) -> {
                    if (oldValue == null) {
                        oldValue = 0;
                    }
                    oldValue++;
                    return oldValue;
                });
                map.compute(1024-border, (key, oldValue) -> {
                    if (oldValue == null) {
                        oldValue = 0;
                    }
                    oldValue++;
                    return oldValue;
                });
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 2) {
                System.out.println(entry.getKey() + " == " + entry.getValue());
            } else if (entry.getValue() == 3) {
                System.out.println(entry.getKey() + " === " + entry.getValue());
            } else if (entry.getValue() == 4) {
                System.out.println(entry.getKey() + " ==== " + entry.getValue());
            }
        }

        long result = 1L;
        int[] cc = new int[5];
        for (Tile tile : tileList) {
            int count = 0;
            for (int border : tile.borderToInts()) {
                count += map.get(border) == 1 ? 1 : 0;
            }
            cc[count]++;
            if (count == 2) {
                System.out.println("YES " + tile.id);
                result *= tile.id;
                for (int border : tile.borderToInts()) {
                    System.out.print(map.get(border) + " ");
                }
                System.out.println();
            }
        }
        System.out.println(cc[1] + " " + cc[2] + " " + cc[3] + " " + cc[4]);
        return result;
    }

    private void getNextMatch(Tile[][] tileGrid, Set<Tile> used, int row, int col, int x, int y) {
        if (y == col) { x++;    y=0; }
        if (x == row) { return; }

        for (Tile tile : tileList) {
            if (used.contains(tile))    continue;
            int[] borders = tile.borderToInts();
            if (x==0) {
                int leftBorder = tileGrid[x][y-1].borderToInts()[1];
                for (int border : borders) {
                }
            } else if (y==0) {

            } else {

            }


        }

    }

    private static class Tile {
        int id;
        char[][] image;
        int[] borders;

        private int[] borderToInts() {
            if (borders != null) {
                return borders;
            }
            int row = image.length;
            int col = image[0].length;
            int[] ints = new int[4];
            char[] row0 = image[0];
            char[] rowN = image[row-1];
            for (int j=0;j<col;j++) {
                ints[0] = (ints[0] << 1) | (row0[j] == '#' ? 1 : 0);
                ints[2] = (ints[2] << 1) | (rowN[col-j-1] == '#' ? 1 : 0);
            }

            for (int i=0;i<row;i++) {
                ints[3] = (ints[3] << 1) | (image[row-i-1][0] == '#' ? 1 : 0);
                ints[1] = (ints[1] << 1) | (image[i][col-1] == '#' ? 1 : 0);
            }
            borders = ints;
            return borders;
        }
    }
}
