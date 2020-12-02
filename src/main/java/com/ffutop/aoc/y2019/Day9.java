package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.IntCode;
import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day9 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val1 = part1.solve();
        System.out.println(String.format("Part 1. BOOST keycode: %d", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part 2. distress signal: %d", val2));
    }

    private static class Part1 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day9.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            input.add(1L);
            intCode.solve(input, output);
            return output.poll();
        }
    }

    private static class Part2 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day9.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            Queue<Long> input = new LinkedList<>();
            Queue<Long> output = new LinkedList<>();
            input.add(2L);
            intCode.solve(input, output);
            return output.poll();
        }
    }
}