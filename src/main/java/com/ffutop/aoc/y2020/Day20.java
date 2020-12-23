package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-20
 */
public class Day20 extends BasicDay {

    private static final String[] SEA_MONSTERS = new String[] {
        "                  # ",
        "#    ##    ##    ###",
        " #  #  #  #  #  #   "
    };

    private static final int TILE_GRID_ROWS = 10;
    private static final int TILE_GRID_COLS = 10;

    private static LinkedList<Tile> tiles = new LinkedList<>();
    private static Map<Integer, Set<Integer>> neighborMap = new HashMap<>();
    private static Map<Integer, Tile> tileMap = new HashMap<>();
    private static Tile[][] tilesGrid;
    private static int tilesGridRows;
    private static int tilesGridCols;
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
        for (Tile tile : tiles) {
            tileMap.put(tile.id, tile);
        }

        day20.solve();
    }

    private void solve() {
        tilesGridRows = (int) Math.sqrt(tiles.size());
        tilesGridCols = tilesGridRows;

        for (Tile u : tiles) {
            for (Tile v : tiles) {
                if (u.id == v.id)   continue;
                for (int uBorder : u.getBorders()) {
                    for (int vBorder : v.getBorders()) {
                        if (uBorder != vBorder) continue;
                        neighborMap.compute(u.id, (key, oldValue) -> {
                            if (oldValue == null)   {   oldValue = new HashSet<>(); }
                            oldValue.add(v.id);
                            return oldValue;
                        });
                    }
                }
            }
        }

        // part 1 result
        long result = 1L;
        for (Map.Entry<Integer, Set<Integer>> entry : neighborMap.entrySet()) {
            if (entry.getValue().size() == 2) {
                result *= entry.getKey();
            }
        }
        System.out.println(result);

        // part 2 result
        tilesGrid = new Tile[tilesGridRows][tilesGridCols];
        boolean matched = false;
        for (Tile topLeft : tiles) {
            if (neighborMap.get(topLeft.id).size() != 2) {
                continue;
            }

            used = new HashSet<>();
            used.add(topLeft.id);
            for (Tile rotateAndFlipTopLeft : topLeft.rotateAndFlip()) {
                tilesGrid[0][0] = rotateAndFlipTopLeft;
                if (nextGrid(0, 1)) {
                    matched = true;
                    break;
                }
            }
            if (matched) {
                break;
            }
        }

        Image image = new Image(tilesGrid);
        System.out.println(image.countSeaMonster());
    }

    private boolean nextGrid(int row, int col) {
        if (col == tilesGridCols)    {   row=row+1;  col=0;  }
        if (row == tilesGridRows)    {   return true;    }

        Tile leftTile = col==0 ? null : tilesGrid[row][col-1];
        Tile topTile = row==0 ? null : tilesGrid[row-1][col];
        int previousId = leftTile==null ? topTile.id : leftTile.id;
        for (Integer neighborId : neighborMap.get(previousId)) {
            if (used.contains(neighborId))  continue;
            Tile currentTile = tileMap.get(neighborId);

            used.add(neighborId);
            for (Tile rotateAndFlipTile : currentTile.rotateAndFlip()) {
                int[] rotateAndFlipBorders = rotateAndFlipTile.getBorders();

                if (!isMatch(leftTile, topTile, rotateAndFlipBorders))  continue;
                tilesGrid[row][col] = rotateAndFlipTile;
                if (nextGrid(row, col+1))   return true;
            }
            used.remove(neighborId);
        }
        return false;
    }

    private boolean isMatch(Tile leftTile, Tile topTile, int[] tileBorders) {
        return (leftTile == null || leftTile.getBorders()[1] == tileBorders[3])
                && (topTile == null || topTile.getBorders()[2] == tileBorders[0]);
    }

    private static class Tile {
        int id;
        char[][] grid;
        int[] borders;
        Tile[] rotateAndFlipTiles;

        public Tile(int id) {
            this.id = id;
            this.grid = new char[TILE_GRID_ROWS][TILE_GRID_COLS];
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

        public Tile[] rotateAndFlip() {
            if (rotateAndFlipTiles == null) {
                rotateAndFlipTiles = new Tile[8];
                rotateAndFlipTiles[0] = this;
                for (int i=1;i<4;i++) {
                    rotateAndFlipTiles[i] = new Tile(this.id);
                    for (int row=0;row<TILE_GRID_ROWS;row++) {
                        for (int col=0;col<TILE_GRID_COLS;col++) {
                            rotateAndFlipTiles[i].grid[row][col] = rotateAndFlipTiles[i-1].grid[TILE_GRID_COLS-col-1][row];
                        }
                    }
                }
                for (int i=4;i<8;i++) {
                    rotateAndFlipTiles[i] = new Tile(this.id);
                    for (int row=0;row<TILE_GRID_ROWS;row++) {
                        for (int col=0;col<TILE_GRID_COLS;col++) {
                            rotateAndFlipTiles[i].grid[row][col] = rotateAndFlipTiles[i-4].grid[TILE_GRID_ROWS-row-1][col];
                        }
                    }
                }
            }
            return rotateAndFlipTiles;
        }
    }

    private static class Image {
        char[][] grid;
        int rows;
        int cols;

        public Image(Tile[][] tilesGrid) {
            rows = (TILE_GRID_ROWS-2) * tilesGridRows;
            cols = (TILE_GRID_COLS-2) * tilesGridCols;
            this.grid = new char[rows][cols];
            for (int tilesRow=0;tilesRow<tilesGridRows;tilesRow++) {
                for (int tilesCol=0;tilesCol<tilesGridCols;tilesCol++) {
                    for (int tileRow=0;tileRow<TILE_GRID_ROWS-2;tileRow++) {
                        for (int tileCol=0;tileCol<TILE_GRID_COLS-2;tileCol++) {
                            this.grid[tilesRow*(TILE_GRID_ROWS-2) + tileRow][tilesCol*(TILE_GRID_COLS-2) + tileCol] = tilesGrid[tilesRow][tilesCol].grid[tileRow+1][tileCol+1];
                        }
                    }
                }
            }
        }

        public int countSeaMonster() {
            for (int rotateAndFlip=0;rotateAndFlip<8;rotateAndFlip++) {
                for (int row=0;row<rows;row++) {
                    for (int col=0;col<cols;col++) {
                        if (matchSeaMonster(row, col)) {
                            markSeaMonster(row, col);
                        }
                    }
                }

                char[][] nextGrid = new char[rows][cols];
                if (rotateAndFlip == 3) {
                    for (int row=0;row<rows;row++) {
                        for (int col=0;col<cols;col++) {
                            nextGrid[row][col] = grid[rows-row-1][col];
                        }
                    }
                } else {
                    for (int row = 0; row < rows; row++) {
                        for (int col = 0; col < cols; col++) {
                            nextGrid[row][col] = grid[cols-col-1][row];
                        }
                    }
                }
                grid = nextGrid;
            }

            int result = 0;
            for (int row=0;row<rows;row++) {
                for (int col=0;col<cols;col++) {
                    result += grid[row][col] == '#' ? 1 : 0;
                }
            }
            return result;
        }

        private boolean matchSeaMonster(int x, int y) {
            for (int row=0;row<SEA_MONSTERS.length;row++) {
                for (int col=0;col<SEA_MONSTERS[row].length();col++) {
                    if (x+row>=rows || y+col>=cols) return false;
                    if (SEA_MONSTERS[row].charAt(col) == '#' && grid[x+row][y+col] == '.')  return false;
                }
            }
            return true;
        }

        private void markSeaMonster(int x, int y) {
            for (int row=0;row<SEA_MONSTERS.length;row++) {
                for (int col=0;col<SEA_MONSTERS[row].length();col++) {
                    if (SEA_MONSTERS[row].charAt(col) == '#') {
                        grid[x+row][y+col] = 'O';
                    }
                }
            }
        }
    }
}
