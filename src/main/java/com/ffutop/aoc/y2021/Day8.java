package com.ffutop.aoc.y2021;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day8 extends BasicDay {

    public static void main(String[] args) {
        Day8 day8 = new Day8();
        System.out.println(day8.solve1());
        System.out.println(day8.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        int counter = 0;
        int[] addon = new int[] {0, 0, 1, 1, 1, 0, 0, 1};
        for (String str : list) {
            String[] numbers = str.split(" \\| ")[1].split(" ");
            for (String number : numbers) {
                counter += addon[number.length()];
            }
        }
        return counter;
    }

    private int solve2() {
        List<String> list = readLists();
        int sum = 0;
        for (String line : list) {
            String[] part = line.split(" \\| ");
            List<String> puzzleList = Arrays.stream(part).flatMap(str->Arrays.stream(str.split(" "))).collect(Collectors.toList());
            String one = puzzleList.stream().filter(puzzle->puzzle.length()==2).findAny().get();
            String four = puzzleList.stream().filter(puzzle->puzzle.length()==4).findAny().get();

            int value = 0;
            for (String puzzle : part[1].split(" ")) {
                value *= 10;
                int judge = judge(puzzle, one, four);
                System.out.printf("puzzle(%s) = %d, 1=%s, 4=%s\n", puzzle, judge, one, four);
                value += judge(puzzle, one, four);
            }
            sum += value;
        }
        return sum;
    }

    private int judge(String puzzle, String one, String four) {
        if (puzzle.length() == 5) {
            if (puzzle.indexOf(one.charAt(0)) != -1 && puzzle.indexOf(one.charAt(1)) != -1) {
                return 3;
            } else if (containsCount(puzzle, four) == 3) {
                return 5;
            } else {
                return 2;
            }
        } else if (puzzle.length() == 6) {
            if (containsCount(puzzle, one) == 1) {
                return 6;
            } else if (containsCount(puzzle, four) == 4) {
                return 9;
            } else {
                return 0;
            }
        } else if (puzzle.length() == 2) {
            return 1;
        } else if (puzzle.length() == 4) {
            return 4;
        } else if (puzzle.length() == 7) {
            return 8;
        } else if (puzzle.length() == 3) {
            return 7;
        }
        return -1;
    }

    private int containsCount(String puzzle, String standard) {
        int count = 0;
        for (char c : standard.toCharArray()) {
            if (puzzle.indexOf(c) != -1) {
                count++;
            }
        }
        return count;
    }
}
