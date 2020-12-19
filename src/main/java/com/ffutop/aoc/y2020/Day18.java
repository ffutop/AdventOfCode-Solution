package com.ffutop.aoc.y2020;

import java.util.*;

public class Day18 extends BasicDay {

    private static final Set<Object> OPERATES = new HashSet<>(Arrays.asList(
            new Character('+'),
            new Character('*'),
            new Character('(')
    ));

    public static void main(String[] args) {
        Day18 day18 = new Day18();
        System.out.println(day18.solve1());
        System.out.println(day18.solve2());
    }

    private Long solve1() {
        List<String> list = readLists();
        Long result = 0L;
        for (String line : list) {
            Stack<Object> stack = new Stack<>();
            char[] cs = line.toCharArray();
            for (char c : cs) {
                if (c == ' ') {
                    continue;
                }
                if (OPERATES.contains(c)) {
                    stack.add(c);
                } else if (c == ')') {
                    Long value = (Long) stack.pop();
                    stack.pop();
                    if (!stack.isEmpty() && (Character) stack.peek() != '(') {
                        Character operate = (Character) stack.pop();
                        Long value1 = (Long) stack.pop();
                        stack.push(op(value1, operate, value));
                    } else {
                        stack.add(value);
                    }
                } else {
                    Long value2 = Long.valueOf(c-'0');
                    if (!stack.isEmpty() && (Character) stack.peek() != '(') {
                        Character operate = (Character) stack.pop();
                        Long value1 = (Long) stack.pop();
                        stack.push(op(value1, operate, value2));
                    } else {
                        stack.add(value2);
                    }
                }
            }
            result += (Long) stack.pop();
        }
        return result;
    }

    private Long solve2() {
        List<String> list = readLists();
        Long result = 0L;
        for (String line : list) {
            Stack<Object> stack = new Stack<>();
            char[] cs = line.toCharArray();
            for (char c : cs) {
                if (c == ' ') {
                    continue;
                }
                if (OPERATES.contains(c)) {
                    stack.add(c);
                } else if (c == ')') {
                    Long value = (Long) stack.pop();
                    while (!stack.isEmpty() && (Character) stack.peek() != '(') {
                        Character operate = (Character) stack.pop();
                        Long value1 = (Long) stack.pop();
                        value = op(value1, operate, value);
                    }
                    stack.pop();
                    stack.add(value);
                } else {
                    Long value2 = Long.valueOf(c-'0');
                    if (!stack.isEmpty() && (Character) stack.peek() == '*') {
                        Character operate = (Character) stack.pop();
                        Long value1 = (Long) stack.pop();
                        stack.push(op(value1, operate, value2));
                    } else {
                        stack.add(value2);
                    }
                }
            }
            Long value = (Long) stack.pop();
            while (!stack.isEmpty() && (Character) stack.peek() != '(') {
                Character operate = (Character) stack.pop();
                Long value1 = (Long) stack.pop();
                value = op(value1, operate, value);
            }
            stack.add(value);
            result += (Long) stack.pop();
            if (!stack.isEmpty()) {
                System.out.println(stack.pop());
            }
        }
        return result;
    }

    private Long op(Long value1, Character operate, Long value2) {
        if (operate == '+') {
            return value1 + value2;
        } else if (operate == '-') {
            return value1 - value2;
        } else if (operate == '*') {
            return value1 * value2;
        } else {
            System.out.println("ERROR");
            return 0L;
        }
    }
}
