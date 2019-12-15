package com.ffutop.aoc;

public class Day4 {

    public static void main(String[] args) {
        Part1 part1 = new Part1();
        int count = 0;
        for (int i=284639;i<=748759;i++) {
            if (part1.matchFacts(i)) {
                count ++;
            }
        }
        System.out.println(String.format("Part1. %d", count));

        Part2 part2 = new Part2();
        count = 0;
        for (int i=284639;i<=748759;i++) {
            if (part2.matchFacts(i)) {
                count ++;
            }
        }
        System.out.println(String.format("Part2. %d", count));
    }

    private static class Part1 {
        public boolean matchFacts(int number) {
            String digits = Integer.toString(number);
            boolean hasDouble = false;
            for (int i=1;i<6;i++) {
                if (digits.charAt(i) == digits.charAt(i-1)) {
                    hasDouble = true;
                }
                if (digits.charAt(i) < digits.charAt(i-1)) {
                    return false;
                }
            }
            return hasDouble;
        }
    }

    private static class Part2 {
        public boolean matchFacts(int number) {
            String digits = String.format("-%d-", number);
            boolean hasDouble = false;
            for (int i=2;i<=6;i++) {
                if (digits.charAt(i) == digits.charAt(i-1) && digits.charAt(i) != digits.charAt(i+1) && digits.charAt(i-1) != digits.charAt(i-2)) {
                    hasDouble = true;
                }
                if (digits.charAt(i) < digits.charAt(i-1)) {
                    return false;
                }
            }
            return hasDouble;
        }
    }

}
