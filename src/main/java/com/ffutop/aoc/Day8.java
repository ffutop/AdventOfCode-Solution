package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Day8 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part 1. the number of 1 digits multiplied by the number of 2 digits: %d", val1));

        Part2 part2 = new Part2();
        System.out.println(String.format("Part 2. image:"));
        part2.solve();
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day8.in");
            String data = br.readLine();
            int length = data.length();
            int[] count = new int[3];
            int layer = length / 150;
            int fewest = Integer.MAX_VALUE;
            int mul = 0;
            for (int i=0;i<layer;i++) {
                Arrays.fill(count, 0);
                for (int j=0;j<150;j++) {
                    count[data.charAt(i*150 + j) - '0'] ++;
                }
                if (fewest > count[0]) {
                    mul = count[1] * count[2];
                    fewest = count[0];
                }
            }
            return mul;
        }
    }

    private static class Part2 {
        public void solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day8.in");
            String data = br.readLine();
            int length = data.length();
            int[] count = new int[3];
            int layer = length / 150;
            int[] image = new int[150];
            Arrays.fill(image, 2);
            for (int i=0;i<layer;i++) {
                for (int j=0;j<150;j++) {
                    int c = data.charAt(i * 150 + j) - '0';
                    if (image[j] == 2) {
                        image[j] = c;
                    }
                }
            }
            for (int i=0;i<150;i++) {
                if (i % 25 == 0) {
                    System.out.println();
                }
                System.out.print(image[i]==0 ? ' ' : '1');
            }
        }
    }
}