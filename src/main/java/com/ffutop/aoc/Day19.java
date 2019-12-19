package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day19 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part1. affected points: %d", val1));

        Part2 part2 = new Part2();
        int val2 = part2.solve();
        System.out.println(String.format("Part2. coordinate: %d", val2));
    }

    private static class Part1 {

        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day19.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            int count = 0;
            for (int i=0;i<50;i++) {
                for (int j=0;j<50;j++) {
                    Queue<Long> input = new LinkedList<>();
                    Queue<Long> output = new LinkedList<>();
                    intCode.init(data);
                    input.add((long) i);
                    input.add((long) j);
                    intCode.solve(input, output);
                    count += output.poll();
                }
            }
            return count;
        }
    }

    private static class Part2 {

        private String data;
        private IntCode intCode;
        private HashMap<Integer, Long> dump;

        private Queue<Long> input = new LinkedList<>();
        private Queue<Long> output = new LinkedList<>();

        private int squareLen = 100;
        private HashMap<Integer, int[]> IntervalMap = new HashMap<>();

        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day19.in");
            data = br.readLine();
            intCode = new IntCode();
            intCode.init(data);
            dump = new HashMap<>(intCode.getMemorys());
            int preCol = 0;
            for (int row=100;true;row++) {
                int col = preCol;
                for (;pulled(row, col) != 1;col++) {}

                int left = col, right = -1;
                for (col+=99; pulled(row, col) == 1; col++) {
                    right = col;
                }
                if (right - left + 1 >= 100) {
                    IntervalMap.put(row, new int[] {left, right});
                    int[] upperEdge = IntervalMap.getOrDefault(row-squareLen+1, new int[] {0, 0});
                    if (upperEdge[1] - left + 1 >= 100) {
                        return (row-squareLen+1) * 10000 + left;
                    }
                }
                preCol = left;
            }
        }

        private int pulled(int x, int y) {
            intCode.init(dump);
            input.add((long) x);
            input.add((long) y);
            intCode.solve(input, output);
            return Math.toIntExact(output.poll());
        }
    }
}