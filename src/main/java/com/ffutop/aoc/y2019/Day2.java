package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.IntCode;
import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class Day2 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val0 = part1.solve();
        System.out.println(String.format("Part1. Value at position 0: %d", val0));

        Part2 part2 = new Part2();
        long val1 = part2.solve();
        System.out.println(String.format("Part2. 100 * noun + verb = %d", val1));
    }

    private static class Part1 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day2.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            intCode.getMemorys().put(1, 12L);
            intCode.getMemorys().put(2, 2L);

            intCode.solve(new LinkedList<>(), new LinkedList<>());
            return intCode.getMemorys().get(0);
        }
    }

    private static class Part2 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day2.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            for (int noun = 0; noun <= 99; noun++) {
                for (int verb = 0; verb <= 99; verb++) {
                    intCode.init(data);
                    intCode.getMemorys().put(1, (long) noun);
                    intCode.getMemorys().put(2, (long) verb);

                    intCode.solve(new LinkedList<>(), new LinkedList<>());
                    if (intCode.getMemorys().get(0) == 19690720) {
                        return 100 * noun + verb;
                    }
                }
            }
            return -1;
        }
    }
}
