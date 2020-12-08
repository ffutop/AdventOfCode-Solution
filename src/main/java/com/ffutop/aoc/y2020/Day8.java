package com.ffutop.aoc.y2020;

import java.util.HashSet;
import java.util.List;

/**
 * @author fangfeng
 * @since 2020-12-08
 */
public class Day8 extends BasicDay {

    private static boolean terminate = false;

    public static void main(String[] args) {
        Day8 day8 = new Day8();
        terminate = false;
        List<Instruction> instructions = day8.readLists(s -> {
            String[] tokens = s.split(" ");
            String operation = tokens[0];
            Integer argument = Integer.parseInt(tokens[1]);
            return new Instruction(operation, argument);
        });
        int len = instructions.size();

        // part 1
        System.out.println(solve(instructions, len));

        // part 2
        for (int i=0;i<len;i++) {
            Instruction instruction = instructions.get(i);
            int acc = 0;
            if ("nop".equals(instruction.operation)) {
                instruction.operation = "jmp";
                terminate = false;
                acc = solve(instructions, len);
                instruction.operation = "nop";
            } else if ("jmp".equals(instruction.operation)) {
                instruction.operation = "nop";
                terminate = false;
                acc = solve(instructions, len);
                instruction.operation = "jmp";
            }

            if (terminate) {
                System.out.println(acc);
                break;
            }
        }
    }

    private static int solve(List<Instruction> instructions, int len) {
        int pc = 0;
        int acc = 0;
        HashSet<Integer> visited = new HashSet<>();
        while (!visited.contains(pc)) {
            if (pc == len) {
                terminate = true;
                break;
            }
            visited.add(pc);
            Instruction instruction = instructions.get(pc);
            if ("nop".equals(instruction.operation)) {
                pc ++;
            } else if ("acc".equals(instruction.operation)) {
                pc ++;
                acc += instruction.argument;
            } else if ("jmp".equals(instruction.operation)) {
                pc += instruction.argument;
            }
        }
        return acc;
    }

    public static class Instruction {
        String operation;
        Integer argument;

        public Instruction(String operation, Integer argument) {
            this.operation = operation;
            this.argument = argument;
        }
    }
}
