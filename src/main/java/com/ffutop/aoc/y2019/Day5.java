package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.IntCode;
import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day5 {

    private static int[] vals;

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val0 = part1.solve();
        System.out.println(String.format("Part1. diagnostic code: %d", val0));

        Part2 part2 = new Part2();
        long val1 = part2.solve();
        System.out.println(String.format("Part2. diagnostic code: %d", val1));
    }

    private static class Part1 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day5.in");
            IntCode intCode = new IntCode();
            intCode.init(br.readLine());
            br.close();

            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            input.add(1L);
            intCode.solve(input, output);
            while (output.peek() != null) {
                if (output.peek() != 0) {
                    return output.poll();
                } else {
                    output.poll();
                }
            }
            return -1;
        }
    }

    private static class Part2 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day5.in");
            IntCode intCode = new IntCode();
            intCode.init(br.readLine());
            br.close();

            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            input.add(5L);
            intCode.solve(input, output);
            return output.poll();
        }
    }
}