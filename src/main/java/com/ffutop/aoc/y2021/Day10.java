package com.ffutop.aoc.y2021;

import java.util.*;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day10 extends BasicDay {

    private static final String leftBound = "([{<";
    private static final String rightBound = ")]}>";
    private static final Set<Character> leftBoundSet = new HashSet<>();
    private static final Set<Character> rightBoundSet = new HashSet<>();
    private static final Map<Character, Character> pair = new HashMap<>();
    private static final int[] valuesPart1 = new int[] {3, 57, 1197, 25137};
    private static final Map<Character, Integer> valuePart1Map = new HashMap<>();
    private static final Map<Character, Integer> valuePart2Map = new HashMap<>();

    static {
        for (char c : leftBound.toCharArray()) {
            leftBoundSet.add(c);
        }
        for (char c : rightBound.toCharArray()) {
            rightBoundSet.add(c);
        }
        for (int i=0;i<leftBound.length();i++) {
            pair.put(rightBound.charAt(i), leftBound.charAt(i));
            valuePart1Map.put(rightBound.charAt(i), valuesPart1[i]);
            valuePart2Map.put(leftBound.charAt(i), i+1);
        }
    }

    public static void main(String[] args) {
        Day10 day10 = new Day10();
        System.out.println(day10.solve1());
        System.out.println(day10.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        int sum = 0;
        for (String line : list) {
            Stack<Character> stack = new Stack<>();
            for (char c : line.toCharArray()) {
                if (leftBoundSet.contains(c)) {
                    stack.add(c);
                } else if (stack.peek().charValue() == pair.get(c)) {
                    stack.pop();
                } else {
                    sum += valuePart1Map.get(c);
                    break;
                }
            }
        }
        return sum;
    }

    private Long solve2() {
        List<String> list = readLists();
        List<Long> scoreList = new ArrayList<>();
        for (String line : list) {
            Stack<Character> stack = new Stack<>();
            boolean corrupted = false;
            for (char c : line.toCharArray()) {
                if (leftBoundSet.contains(c)) {
                    stack.add(c);
                } else if (stack.peek().charValue() == pair.get(c)) {
                    stack.pop();
                } else {
                    corrupted = true;
                    break;
                }
            }
            if (corrupted) {
                continue;
            }
            long score = 0;
            while (!stack.isEmpty()) {
                score *= 5;
                score += valuePart2Map.get(stack.pop());
            }
            scoreList.add(score);
        }
        scoreList.sort((x,y)-> (x-y>0?1:-1));
        return scoreList.get(scoreList.size()/2);
    }
}
