package com.ffutop.aoc.y2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangfeng
 * @since 2020-12-14
 */
public class Day14 extends BasicDay {

    public static void main(String[] args) {
        Day14 day14 = new Day14();
        System.out.println(day14.solve1());
        System.out.println(day14.solve2());
    }

    private long solve1() {
        List<String> list = readLists();
        Map<Integer, Long> mem = new HashMap<>();
        String mask = "";
        for (String command : list) {
            String[] tokens = command.split(" = ");
            if ("mask".equals(tokens[0])) {
                mask = tokens[1];
            } else {
                Integer memOffset = Integer.parseInt(tokens[0].substring(4, tokens[0].length()-1));
                String value = toBinary(Long.parseLong(tokens[1]));
                Long result = toDecimal(mask1(value, mask));
                mem.put(memOffset, result);
            }
        }

        Long sum = 0L;
        for (Long value : mem.values()) {
            sum += value;
        }
        return sum;
    }

    private long solve2() {
        List<String> list = readLists();
        Map<Long, Integer> mem = new HashMap<>();
        String mask = "";
        for (String command : list) {
            String[] tokens = command.split(" = ");
            if ("mask".equals(tokens[0])) {
                mask = tokens[1];
            } else {
                Integer memOffset = Integer.parseInt(tokens[0].substring(4, tokens[0].length()-1));
                String value = toBinary(memOffset.longValue());
                String result = mask2(value, mask);
                long xCount = result.chars().filter(x->x=='X').count();
                int maxValue = 1<<xCount;
                for (int i=0;i<maxValue;i++) {
                    mem.put(toDecimal(findOneOffset(result, i)), Integer.parseInt(tokens[1]));
                }
            }
        }

        Long sum = 0L;
        for (Integer value : mem.values()) {
            sum += value;
        }
        return sum;
    }

    private String toBinary(Long decimal) {
        StringBuilder builder = new StringBuilder();
        while (decimal != 0) {
            builder.append(decimal&1);
            decimal>>=1;
        }
        while (builder.length() != 36) {
            builder.append(0);
        }
        return builder.reverse().toString();
    }

    private Long toDecimal(String binary) {
        long val = 0L;
        for (char c : binary.toCharArray()) {
            val = (val << 1) | (c-'0');
        }
        return val;
    }

    private String mask1(String value, String mask) {
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<36;i++) {
            if (mask.charAt(i) != 'X') {
                builder.append(mask.charAt(i));
            } else {
                builder.append(value.charAt(i));
            }
        }
        return builder.toString();
    }

    private String mask2(String value, String mask) {
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<36;i++) {
            if (mask.charAt(i) == '0') {
                builder.append(value.charAt(i));
            } else {
                builder.append(mask.charAt(i));
            }
        }
        return builder.toString();
    }

    private String findOneOffset(String memOffset, int random) {
        StringBuilder builder = new StringBuilder();
        for (char c : memOffset.toCharArray()) {
            if (c == 'X') {
                builder.append(random&1);
                random>>=1;
            } else {
                builder.append(c);
            }
        }
        return builder.reverse().toString();
    }
}
