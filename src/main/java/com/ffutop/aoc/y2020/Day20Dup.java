package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-20
 */
public class Day20Dup extends BasicDay {

    private static final int TILE_GRID_ROWS = 10;
    private static final int TILE_GRID_COLS = 10;

    private static LinkedList<Tile> tiles = new LinkedList<>();

    public static void main(String[] args) {
        Day20Dup day20 = new Day20Dup();
        day20.readLists((line, lineNo) -> {
            if ((lineNo.intValue()) % 12 == 0) {
                tiles.add(new Tile(Integer.parseInt(line.substring(5, 9))));
            } else {
                Tile tile = tiles.getLast();
                int rowNo = lineNo.intValue() % 12 - 1;
                tile.appendRow(rowNo, line);
            }
            return null;
        });

        System.out.println(day20.solve1());
    }

    private Long solve1() {
        Map<Integer, Integer> map = new HashMap<>();
        int row = (int) Math.sqrt(tiles.size());
        int col = row;

        for (Tile tile : tiles) {
            int[] borders = tile.getBorders();
            for (int border : borders) {
                map.compute(border, (key, oldValue) -> {
                    oldValue = oldValue == null ? 1 : oldValue+1;
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

        Map<Integer, Set<Integer>> neighborMap = new HashMap<>();
        for (Tile u : tiles) {
            for (Tile v : tiles) {
                if (u.id == v.id)   continue;
                for (int uBorder : u.getBorders()) {
                    for (int vBorder : v.getBorders()) {
                        if (uBorder == vBorder) {
                            neighborMap.compute(u.id, (key, oldValue) -> {
                                if (oldValue == null)   {   oldValue = new HashSet<>(); }
                                oldValue.add(v.id);
                                return oldValue;
                            });
                        }
                    }
                }
            }
        }

        long result = 1L;
        for (Map.Entry<Integer, Set<Integer>> entry : neighborMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                result *= entry.getKey();
            }
        }

//        long result = 1L;
//        int[] cc = new int[5];
//        for (Tile tile : tiles) {
//            int count = 0;
//            for (int border : tile.borderToInts()) {
//                count += map.get(border) == 1 ? 1 : 0;
//            }
//            cc[count]++;
//            if (count == 2) {
//                System.out.println("YES " + tile.id);
//                result *= tile.id;
//                for (int border : tile.borderToInts()) {
//                    System.out.print(map.get(border) + " ");
//                }
//                System.out.println();
//            }
//        }
//        System.out.println(cc[1] + " " + cc[2] + " " + cc[3] + " " + cc[4]);
//        return result;
        return result;
    }

    private static class Tile {
        int id;
        char[][] grid;
        int[] borders;

        public Tile(int id) {
            this.id = id;
            this.grid = new char[TILE_GRID_ROWS][TILE_GRID_COLS];
        }

        public Tile(int id, int[] borders) {
            this.id = id;
            this.borders = borders;
        }

        public void appendRow(int rowNo, String msg) {
            if ("".equals(msg.trim()))  return;
            for (int colNo=0;colNo<TILE_GRID_COLS;colNo++) {
                grid[rowNo][colNo] = msg.charAt(colNo);
            }
        }

        public int[] getBorders() {
            if (borders == null) {
                borders = new int[8];
                for (int col = 0; col < TILE_GRID_COLS; col++) {
                    borders[0] = (borders[0] << 1) | (grid[0][col] == '#' ? 1 : 0);
                    borders[7] = (borders[7] << 1) | (grid[0][TILE_GRID_COLS - col - 1] == '#' ? 1 : 0);
                    borders[2] = (borders[2] << 1) | (grid[TILE_GRID_ROWS - 1][col] == '#' ? 1 : 0);
                    borders[5] = (borders[5] << 1) | (grid[TILE_GRID_ROWS - 1][TILE_GRID_COLS - col - 1] == '#' ? 1 : 0);
                }

                for (int row = 0; row < TILE_GRID_ROWS; row++) {
                    borders[1] = (borders[1] << 1) | (grid[row][TILE_GRID_COLS - 1] == '#' ? 1 : 0);
                    borders[6] = (borders[6] << 1) | (grid[TILE_GRID_ROWS - row - 1][TILE_GRID_COLS - 1] == '#' ? 1 : 0);
                    borders[3] = (borders[3] << 1) | (grid[row][0] == '#' ? 1 : 0);
                    borders[4] = (borders[4] << 1) | (grid[TILE_GRID_ROWS - row - 1][0] == '#' ? 1 : 0);
                }
            }

            return borders;
        }
    }
}
