package com.ffutop.util;

import java.util.HashMap;
import java.util.Queue;

public class IntCode {

    private static final int[] cursorAddon = new int[] {0,4,4,2,2,3,3,4,4,2};
    private HashMap<Integer, Long> memorys;
    private long relativeBase = 0;
    int cursor = 0;
    String instruction;

    public void init(String data) {
        cursor = 0;
        relativeBase = 0;
        String[] tokens = data.split(",");
        int length = tokens.length;
        memorys = new HashMap<>();
        for (int index=0;index<length;index++) {
            memorys.put(index, Long.valueOf(tokens[index]));
        }
    }

    public void init(HashMap<Integer, Long> memorys) {
        this.memorys = new HashMap<Integer, Long>(memorys);
        cursor = 0;
        relativeBase = 0;
    }

    public boolean solve(Queue<Long> input, Queue<Long> output) {
        while (true) {
            instruction = String.format("%05d", memorys.getOrDefault(cursor, 0L));
            int opcode = (int) (memorys.getOrDefault(cursor,0L)%100);
            switch (opcode) {
                case 1:
                    memorys.put(par(3), memorys.getOrDefault(par(1), 0L) + memorys.getOrDefault(par(2), 0L));
                    break;
                case 2:
                    memorys.put(par(3), memorys.getOrDefault(par(1), 0L) * memorys.getOrDefault(par(2), 0L));
                    break;
                case 3:
                    if (input.size() == 0) {
                        return false;
                    }
                    memorys.put(par(1), input.poll());
                    break;
                case 4:
                    output.add((memorys.getOrDefault(par(1), 0L)));
                    break;
                case 5:
                    if (memorys.getOrDefault(par(1), 0L) != 0) {
                        cursor = Math.toIntExact(memorys.getOrDefault(par(2), 0L));
                        continue;
                    }
                    break;
                case 6:
                    if (memorys.getOrDefault(par(1), 0L) == 0) {
                        cursor = Math.toIntExact(memorys.getOrDefault(par(2), 0L));
                        continue;
                    }
                    break;
                case 7:
                    memorys.put(par(3), memorys.getOrDefault(par(1), 0L).longValue() < memorys.getOrDefault(par(2), 0L).longValue() ? 1L : 0L);
                    break;
                case 8:
                    memorys.put(par(3), memorys.getOrDefault(par(1), 0L).longValue() == memorys.getOrDefault(par(2), 0L).longValue() ? 1L : 0L);
                    break;
                case 9:
                    relativeBase += memorys.getOrDefault(par(1), 0L);
                    break;
                case 99:
                    return true;
                default:
                    System.err.println(String.format("Invalid instruction: %s", instruction));
                    return true;
            }
            cursor += cursorAddon[opcode];
        }
    }

    private int par(int addon) {
        switch (instruction.charAt(3-addon)) {
            case '0':
                return Math.toIntExact(memorys.getOrDefault(cursor + addon, 0L));
            case '1':
                return cursor+addon;
            case '2':
                return Math.toIntExact(memorys.getOrDefault(cursor + addon, 0L) + relativeBase);
            default:
                System.err.println(String.format("Invalid instruction: %s", instruction));
                return -1;
        }
    }

    public HashMap<Integer, Long> getMemorys() {
        return memorys;
    }
}
