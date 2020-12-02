package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;

public class Day1 {
    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int sumFuel = part1.solve();
        System.out.println(String.format("Part1. sum of required fule: %d", sumFuel));

        Part2 part2 = new Part2();
        sumFuel = part2.solve();
        System.out.println(String.format("Part2. sum of required fule: %d", sumFuel));
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day1.in");
            int sumFuel = 0;
            for (String mass; (mass = br.readLine()) != null; ) {
                sumFuel += Integer.valueOf(mass) / 3 - 2;
            }
            return sumFuel;
        }
    }

    private static class Part2 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day1.in");
            int sumFuel = 0;
            for (String mass; (mass = br.readLine()) != null; ) {
                int fuel = Integer.valueOf(mass) / 3 - 2;
                do {
                    sumFuel += fuel;
                    fuel = fuel / 3 - 2;
                } while (fuel > 0);
            }
            return sumFuel;
        }
    }

}