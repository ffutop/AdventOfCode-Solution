package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Day23 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val1 = part1.solve();
        System.out.println(String.format("Part1. the Y value of the first packet sent to address 255: %d", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part2. the first Y value delivered by the NAT to the computer at address 0 twice in a row: %s", val2));
    }

    private static class Part1 {

        private int computers = 50;

        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day23.in");
            String data = br.readLine();
            IntCode[] intCodes = new IntCode[computers];
            Queue<Long>[] inputQues = new Queue[computers];
            Queue<Long>[] outputQues = new Queue[computers];
            Queue<Integer> workQue = new LinkedList<>();
            for (int i=0;i<computers;i++) {
                intCodes[i] = new IntCode();
                intCodes[i].init(data);

                inputQues[i] = new LinkedList<>();
                inputQues[i].add((long) i);

                outputQues[i] = new LinkedList<>();

                intCodes[i].solve(inputQues[i], outputQues[i]);
            }
            int cursor = 0;
            while (true) {
                if (workQue.size() != 0) {
                    int index = workQue.poll();
                    intCodes[index].solve(inputQues[index], outputQues[index]);
                    while (outputQues[index].size()!=0) {
                        long addr = outputQues[index].poll();
                        long x = outputQues[index].poll();
                        long y = outputQues[index].poll();
                        if (addr == 255) {
                            return y;
                        }
                        inputQues[(int) addr].add(x);
                        inputQues[(int) addr].add(y);
                        workQue.add((int) addr);
                    }
                } else {
                    inputQues[cursor].add(-1L);
                    workQue.add(cursor);
                    cursor = (cursor + 1) % computers;
                }
            }
        }
    }

    private static class Part2 {

        private int computers = 50;
        private boolean hasNat = false;
        private long natX = 0;
        private long natY = 0;
        private long prefixNatY = 0;

        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day23.in");
            String data = br.readLine();
            IntCode[] intCodes = new IntCode[computers];
            Queue<Long>[] inputQues = new Queue[computers];
            Queue<Long>[] outputQues = new Queue[computers];
            Queue<Integer> workQue = new LinkedList<>();
            for (int i=0;i<computers;i++) {
                intCodes[i] = new IntCode();
                intCodes[i].init(data);

                inputQues[i] = new LinkedList<>();
                inputQues[i].add((long) i);

                outputQues[i] = new LinkedList<>();

                intCodes[i].solve(inputQues[i], outputQues[i]);
            }
            int cursor = 3;
            while (true) {
                if (workQue.size() != 0) {
                    int index = workQue.poll();
                    boolean flag = intCodes[index].solve(inputQues[index], outputQues[index]);
                    while (outputQues[index].size()!=0) {
                        long addr = outputQues[index].poll();
                        long x = outputQues[index].poll();
                        long y = outputQues[index].poll();
                        if (addr == 255) {
                            hasNat = true;
                            natX = x;
                            natY = y;
                            continue;
                        }
                        inputQues[(int) addr].add(x);
                        inputQues[(int) addr].add(y);
                        workQue.add((int) addr);
                    }
                } else {
                    if (hasNat) {
                        if (prefixNatY == natY) {
                            return natY;
                        }
                        prefixNatY = natY;
                        inputQues[0].add(natX);
                        inputQues[0].add(natY);
                        workQue.add(0);
                    } else {
                        inputQues[cursor].add(-1L);
                        workQue.add(cursor);
                        cursor = (cursor + 1) % computers;
                    }
                }
            }
        }
    }
}