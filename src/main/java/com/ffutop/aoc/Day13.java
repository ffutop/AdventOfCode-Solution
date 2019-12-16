package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day13 {

    public static void main(String[] args) throws IOException {

        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part1. block tiles: %d", val1));

        Part2 part2 = new Part2();
        int val2 = part2.solve();
        System.out.println(String.format("Part2. score after the last block is broken: %d", val2));
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day13.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            int counter = 0;
            while (true) {
                boolean flag = intCode.solve(input, output);
                while (output.size() != 0) {
                    long x = output.poll();
                    long y= output.poll();
                    long z = output.poll();
                    if (z == 2L) {
                        counter++;
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            return counter;
        }
    }

    private static class Part2 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day13.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            intCode.getMemorys().put(0, 2L);
            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            long paddle = 0;
            long score = 0;
            while (true) {
                boolean flag = intCode.solve(input, output);
                while (output.size() != 0) {
                    long x = output.poll();
                    long y= output.poll();
                    long z = output.poll();
                    if (x == -1 && y == 0) {
                        score = z;
                    } else if (z == 3) {
                        paddle = x;
                    } else if (z == 4) {
                        if (paddle < x) {
                            input.add(1L);
                        } else if (paddle == x) {
                            input.add(0L);
                        } else {
                            input.add(-1L);
                        }
                    }
                }
                if (flag == true) {
                    break;
                }
            }
            return (int) score;
        }
    }
}
