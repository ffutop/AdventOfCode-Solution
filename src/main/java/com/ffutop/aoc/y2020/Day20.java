package com.ffutop.aoc.y2020;

import java.util.*;

public class Day20 extends BasicDay {

    private static final Map<Integer, int[]> ROTATE_AND_FLIP_MAPS = new HashMap<Integer, int[]>() {{
        put(0, new int[] {1,2,3,4});
        put(1, new int[] {-4,1,-2,3});
        put(2, new int[] {-3,-4,-1,-2});
        put(3, new int[] {2,-3,4,-1});
        put(4, new int[] {-1,4,-3,2});
        put(5, new int[] {4,3,2,1});
        put(6, new int[] {3,-2,1,4});
        put(7, new int[] {-2,-1,-4,-3});
    }};

    private static LinkedList<Tile> tiles = new LinkedList<>();
    private static final int TILE_GRID_ROWS = 10;
    private static final int TILE_GRID_COLS = 10;
    private static int tileCount;

    private static Tile[][] tileGrid;
    private static int tileGridRows;
    private static int tileGridCols;
    private static Set<Integer> used;

    public static void main(String[] args) {
        Day20 day20 = new Day20();
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

    private long solve1() {
        tileCount = tiles.size();
        tileGridRows = (int) Math.sqrt(tileCount);
        tileGridCols = tileGridRows;
        for (Tile topLeft : tiles) {
            System.out.println("topLeft.id = " + topLeft.id);
            for (int[] rotateAndFlipMap : ROTATE_AND_FLIP_MAPS.values()) {
                int[] rotateAndFlipBorders = topLeft.getBorders(rotateAndFlipMap);
                System.out.println(Arrays.toString(rotateAndFlipBorders));

                tileGrid = new Tile[tileGridRows][tileGridCols];
                tileGrid[0][0] = new Tile(topLeft.id, rotateAndFlipBorders);
                used = new HashSet<>();
                used.add(topLeft.id);
                if (nextGrid(0, 1)) {
                    System.out.println("YES");
                    break;
                }
            }
        }

        return 1L * tileGrid[0][0].id * tileGrid[0][tileGridCols-1].id * tileGrid[tileGridRows-1][0].id * tileGrid[tileGridRows-1][tileGridCols-1].id;
    }

    private boolean nextGrid(int row, int col) {
        System.out.println(row + " " + col);
        if (col == tileGridCols)    {   row=row+1;  col=0;  }
        if (row == tileGridRows)    {   return true;    }

        for (Tile tile : tiles) {
            if (used.contains(tile.id)) continue;
            Tile leftTile = col==0 ? null : tileGrid[row][col-1];
            Tile topTile = row==0 ? null : tileGrid[row-1][col];

            used.add(tile.id);
            for (int[] rotateAndFlipMap : ROTATE_AND_FLIP_MAPS.values()) {
                int[] rotateAndFlipBorders = tile.getBorders(rotateAndFlipMap);

                if (!isMatch(leftTile, topTile, rotateAndFlipBorders))  continue;
                tileGrid[row][col] = new Tile(tile.id, rotateAndFlipBorders);
                if (nextGrid(row, col+1))   return true;
            }

            used.remove(tile.id);
        }
        return false;
    }

    private boolean isMatch(Tile leftTile, Tile topTile, int[] tileBorders) {
        return (leftTile == null || leftTile.borders[1] == tileBorders[3])
                && (topTile == null || topTile.borders[2] == tileBorders[0]);
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

        public int[] getBorders(int[] rotateAndFlip) {
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

            if (rotateAndFlip == null) {
                rotateAndFlip = new int[] {1,2,3,4};
            }
            int[] rotateAndFlipBorders = new int[4];
            for (int i=0;i<4;i++) {
                rotateAndFlipBorders[i] = rotateAndFlip[i] < 0 ? borders[8+rotateAndFlip[i]] : borders[rotateAndFlip[i]-1];
            }
            return rotateAndFlipBorders;
        }
    }
}