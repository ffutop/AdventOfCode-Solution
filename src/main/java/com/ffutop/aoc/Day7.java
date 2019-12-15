package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.*;
import java.util.LinkedList;
import java.util.Queue;

public class Day7 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part 1. highest signal: %d", val1));

        Part2 part2 = new Part2();
        int val2 = part2.solve();
        System.out.println(String.format("Part 2. highest signal: %d", val2));
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day7.in");
            String data = br.readLine();
            int[] seq = new int[] {0,1,2,3,4};
            long maxSignal = 0;
            do {
                IntCode[] intCodes = new IntCode[5];
                long signal = 0;
                for (int i=0;i<5;i++) {
                    intCodes[i] = new IntCode();
                    intCodes[i].init(data);
                    Queue<Long> input = new LinkedList<>();
                    Queue<Long> output = new LinkedList<>();
                    input.add((long) seq[i]);
                    input.add(signal);
                    intCodes[i].solve(input, output);
                    signal = output.poll();
                }
                maxSignal = Math.max(maxSignal, signal);
            } while (nextPermute(seq));
            return (int) maxSignal;
        }
    }

    private static class Part2 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day7.in");
            String data = br.readLine();
            int[] seq = new int[]{5,6,7,8,9};
            long maxSignal = 0;
            do {
                IntCode[] intCodes = new IntCode[5];
                Queue<Long>[] inputs = new Queue[5];
                Queue<Long>[] outputs = new Queue[5];
                for (int i = 0; i < 5; i++) {
                    intCodes[i] = new IntCode();
                    intCodes[i].init(data);
                    inputs[i] = new LinkedList<>();
                    inputs[i].add((long) seq[i]);
                    outputs[i] = new LinkedList<>();
                }
                long signal = 0;
                for (int i = 0; true; i++) {
                    i = i == 5 ? 0 : i;
                    inputs[i].add(signal);
                    boolean flag = intCodes[i].solve(inputs[i], outputs[i]);
                    signal = outputs[i].poll();
                    if (i == 4 && flag == true) {
                        break;
                    }
                }
                maxSignal = Math.max(maxSignal, signal);
            } while (nextPermute(seq));
            return (int) maxSignal;
        }
    }

    private static void reserve(int[] nums, int left, int right) {
        while (left < right) {
            nums[left] ^= nums[right];
            nums[right] ^= nums[left];
            nums[left] ^= nums[right];
            left++;
            right--;
        }
    }

    public static boolean nextPermute(int[] nums) {
        int leftBound = -1;
        int rightBound = -1;
        for (int i=nums.length-2;i>=0;i--) {
            if (nums[i] < nums[i+1]) {
                leftBound = i;
                for (int j=nums.length-1;j>leftBound;j--) {
                    if (nums[j] > nums[leftBound]) {
                        rightBound = j;
                        nums[leftBound] ^= nums[rightBound];
                        nums[rightBound] ^= nums[leftBound];
                        nums[leftBound] ^= nums[rightBound];
                        reserve(nums, leftBound+1, nums.length-1);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}