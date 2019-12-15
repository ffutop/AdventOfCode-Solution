package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Day11 {

    private static int[][] direction = new int[][] {{-1,0}, {0,1}, {1,0}, {0,-1}};
    private static int currentDirection = 0;
    private static int x = 0;
    private static int y = 0;
    private static Queue<Long> intCodeQue = new LinkedList<>();
    private static Queue<Long> robotQue = new LinkedList<>();

    private static HashSet<Integer> visited = new HashSet<>();
    private static HashMap<Integer, Integer> map = new HashMap<>();

    private static int toInt(int x, int y) {
        return x * 10000 + y;
    }

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part1. painted panels: %d", val1));

        Part2 part2 = new Part2();
        System.out.println("Part2. images:");
        part2.solve();
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day11.in");
            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            map.put(toInt(0, 0), 1);
            intCodeQue.add(0L);
            while (intCode.solve(intCodeQue, robotQue) == false) {
                robot();
            }
            return visited.size();
        }

        private static void robot() {
            long color = robotQue.poll();
            long turn = robotQue.poll();
            visited.add(toInt(x, y));
            map.put(toInt(x, y), (int) color);
            currentDirection = (currentDirection + (turn == 0 ? -1 : 1) + 4) % 4;
            x += direction[currentDirection][0];
            y += direction[currentDirection][1];
            intCodeQue.add(Long.valueOf(map.getOrDefault(toInt(x, y), 0)));
        }
    }

    private static class Part2 {
        public void solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day11.in");
            x = 0;
            y = 0;
            currentDirection = 0;
            visited = new HashSet<>();
            map = new HashMap<>();
            intCodeQue = new LinkedList<>();
            robotQue = new LinkedList<>();

            String data = br.readLine();
            IntCode intCode = new IntCode();
            intCode.init(data);
            map.put(toInt(0, 0), 1);
            intCodeQue.add(1L);
            while (intCode.solve(intCodeQue, robotQue) == false) {
                robot();
            }

            int[][] board = new int[10][100];
            for (Integer value : visited) {
                board[value/10000][value%10000] = map.get(value);
            }
            for (int i=0;i<10;i++) {
                for (int j=0;j<100;j++) {
                    System.out.print(board[i][j] == 1 ? '0' : ' ');
                }
                System.out.println();
            }
        }

        private static void robot() {
            long color = robotQue.poll();
            long turn = robotQue.poll();
            visited.add(toInt(x, y));
            map.put(toInt(x, y), (int) color);
            currentDirection = (currentDirection + (turn == 0 ? -1 : 1) + 4) % 4;
            x += direction[currentDirection][0];
            y += direction[currentDirection][1];
            intCodeQue.add(Long.valueOf(map.getOrDefault(toInt(x, y), 0)));
        }
    }

}
