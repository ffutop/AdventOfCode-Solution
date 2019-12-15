package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Day3 {

    private static final int[][] direction = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
    private static final HashMap<Character, Integer> directionMap = new HashMap<Character, Integer>() {{
        put('L', 0);
        put('R', 1);
        put('D', 2);
        put('U', 3);
    }};
    private static final TreeMap<int[], Integer> stepMap = new TreeMap<int[], Integer>(new Comparator<int[]>() {
        @Override
        public int compare(int[] o1, int[] o2) {
            for (int i=0;i<2;i++) {
                if (o1[i] != o2[i]) {
                    return o1[i] < o2[i] ? -1 : 1;
                }
            }
            return 0;
        }
    });

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val1 = part1.solve();
        System.out.println(String.format("Part1. Closest Manhattan distance: %d", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part2. fewest steps to wires: %d", val2));
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day3.in");
            String[] data = new String[] {br.readLine(), br.readLine()};

            stepMap.clear();
            int minManhattan = Integer.MAX_VALUE;
            for (int row=0;row<2;row++) {
                String[] tokens = data[row].split(",");
                int length = tokens.length;
                int x = 0, y = 0;
                for (int index=0;index<length;index++) {
                    int[] dVec = direction[directionMap.get(tokens[index].charAt(0))];
                    Integer distance = Integer.valueOf(tokens[index].substring(1));
                    while (distance-- != 0) {
                        x += dVec[0];
                        y += dVec[1];
                        int[] nextCoord = new int[] {x, y};
                        if (stepMap.containsKey(nextCoord) && stepMap.getOrDefault(nextCoord, 1) == 0) {
                            minManhattan = Math.min(minManhattan, Math.abs(x) + Math.abs(y));
                        }
                        stepMap.put(nextCoord, row);
                    }
                }
            }
            return minManhattan;
        }
    }

    private static class Part2 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day3.in");
            String[] data = new String[] {br.readLine(), br.readLine()};

            stepMap.clear();
            int minStep = Integer.MAX_VALUE;
            for (int row=0;row<2;row++) {
                String[] tokens = data[row].split(",");
                int length = tokens.length;
                int x = 0, y = 0;
                int steps = 0;
                for (int index=0;index<length;index++) {
                    int[] dVec = direction[directionMap.get(tokens[index].charAt(0))];
                    Integer distance = Integer.valueOf(tokens[index].substring(1));
                    while (distance-- != 0) {
                        steps++;
                        x += dVec[0];
                        y += dVec[1];
                        int[] nextCoord = new int[] {x, y};
                        if (row == 1 && stepMap.containsKey(nextCoord)) {
                            minStep = Math.min(minStep, steps + stepMap.get(nextCoord));
                        } else if (row == 0) {
                            stepMap.put(nextCoord, steps);
                        }
                    }
                }
            }
            return minStep;
        }
    }

}
