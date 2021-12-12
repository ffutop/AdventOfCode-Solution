package com.ffutop.aoc.y2021;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day4 extends BasicDay {

    private List<Integer> numbers;
    private List<int[][]> gridList;

    public static void main(String[] args) {
        Day4 day4 = new Day4();

        day4.parseInput();
        System.out.println(day4.solve1(1));

        day4.parseInput();
        System.out.println(day4.solve1(day4.gridList.size()));
    }

    private void parseInput() {
        List<String> list = readLists();
        numbers = Arrays.stream(list.get(0).split(",")).map(s->Integer.valueOf(s)).collect(Collectors.toList());

        gridList = new ArrayList<>();
        for (int i=2;i<list.size();i++) {
            int[][] grid = new int[5][5];
            for (int addon=0;addon<5;addon++,i++) {
                List<Integer> rowValues = Arrays.stream(list.get(i).trim().split(" +")).map(s->Integer.valueOf(s)).collect(Collectors.toList());
                for (int col=0;col<5;col++) {
                    grid[addon][col] = rowValues.get(col);
                }
            }
            gridList.add(grid);
        }
    }

    private long solve1(int target) {
        Map<Integer, List<Tuple>> map = new HashMap<>();
        int gridId = 0;
        for (int[][] grid : gridList) {
            for (int row=0;row<5;row++) {
                for (int col=0;col<5;col++) {
                    int finalGridId = gridId;
                    int finalRow = row;
                    int finalCol = col;
                    map.compute(grid[row][col], (key, value) -> {
                        if (value == null) {
                            value = new ArrayList<>();
                        }
                        value.add(new Tuple(finalGridId, finalRow, finalCol));
                        return value;
                    });
                }
            }
            gridId++;
        }

        int[][] gridRowCount = new int[gridList.size()][5];
        int[][] gridColCount = new int[gridList.size()][5];
        Set<Integer> winsGrid = new HashSet<>();
        for (int number : numbers) {
            List<Tuple> tupleList = map.get(number);
            for (Tuple tuple : tupleList) {
                gridList.get(tuple.gridId)[tuple.row][tuple.col] = 0;
                gridRowCount[tuple.gridId][tuple.row]++;
                gridColCount[tuple.gridId][tuple.col]++;
                if (gridRowCount[tuple.gridId][tuple.row] == 5 || gridColCount[tuple.gridId][tuple.col] == 5) {
                    winsGrid.add(tuple.gridId);
                    if (winsGrid.size() == target) {
                        return calcResult(tuple.gridId, number);
                    }
                }
            }
        }
        return -1;
    }

    private long calcResult(int gridId, int number) {
        int[][] grid = gridList.get(gridId);
        long unmarkedSum = 0;
        for (int row=0;row<5;row++) {
            for (int col=0;col<5;col++) {
                unmarkedSum += grid[row][col];
            }
        }
        return unmarkedSum * number;
    }

    private static class Tuple {
        int gridId;
        int row;
        int col;

        public Tuple(int gridId, int row, int col) {
            this.gridId = gridId;
            this.row = row;
            this.col = col;
        }
    }
}
