package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day21 {

    public static void main(String[] args) throws IOException {
        String instructions = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nWALK\n";
        long val1 = solve(instructions);
        System.out.println(String.format("Part1. amount of hull damage: %d", val1));

        instructions = "NOT A J\nNOT B T\nOR T J\nNOT C T\nOR T J\nAND D J\nNOT E T\nNOT T T\nOR H T\nAND T J\nRUN\n";
        long val2 = solve(instructions);
        System.out.println(String.format("Part2. amount of hull damage: %d", val2));
    }

    private static long solve(String instructions) throws IOException {
        BufferedReader br = ReaderUtil.getBufferedReader("day21.in");
        String data = br.readLine();
        IntCode intCode = new IntCode();
        intCode.init(data);
        Queue<Long> input = new LinkedList<>();
        Queue<Long> output = new LinkedList<>();
        for (int i=0;i<instructions.length();i++) {
            input.add((long) instructions.charAt(i));
        }
        intCode.solve(input, output);
        while (output.size() != 1) {
            output.poll();
        }
        return output.poll();
    }
}