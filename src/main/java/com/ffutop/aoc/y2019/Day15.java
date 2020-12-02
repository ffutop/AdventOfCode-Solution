package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.IntCode;
import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day15 {

    private static int[][] direction = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static int[] rev = new int[]{1, 0, 3, 2};
    private static Part1 part1 = new Part1();
    private static Part2 part2 = new Part2();

    public static void main(String[] args) throws IOException {
        int val1 = part1.solve();
        System.out.println(String.format("Part1. fewest number of movement commands: %d", val1));

        int val2 = part2.solve();
        System.out.println(String.format("Part2. minutes will it take to fill with oxygen: %d", val2));
    }

    private static class Part1 {

        private HashMap<Integer, Integer> visited = new HashMap<>();
        Queue<Long> input = new LinkedList<>();
        Queue<Long> output = new LinkedList<>();
        IntCode intCode = new IntCode();
        private int minStep = Integer.MAX_VALUE;

        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day15.in");
            String data = br.readLine();
            intCode.init(data);

            intCode.solve(input, output);
            dfs(21, 21, 0);
            return minStep;
        }

        private void dfs(int x, int y, int step) {
            int mark = x * 100000 + y;
            if (visited.containsKey(mark)) {
                if (visited.get(mark) <= step) {
                    return;
                }
            }
            visited.put(mark, step);
            for (int i = 0; i < 4; i++) {
                input.add((long) (i + 1));
                intCode.solve(input, output);
                int status = Math.toIntExact(output.poll());
                int nx = x + direction[i][0];
                int ny = y + direction[i][1];
                part2.drawBoard(nx, ny, status);
                if (status == 2) {
                    minStep = Math.min(minStep, step + 1);
                } else if (status == 1) {
                    dfs(nx, ny, step + 1);
                } else {
                    continue;
                }
                input.add((long) (rev[i] + 1));
                intCode.solve(input, output);
                output.poll();
            }
        }
    }

    private static class Part2 {

        private int[][] board = new int[41][41];
        private int length = 40;

        public int solve() {
            Queue<Integer> queue = new LinkedList<>();
            // start at point (35, 35)
            queue.add(35);
            queue.add(35);
            queue.add(0);
            int[][] visited = new int[41][41];
            for (int i = 0; i <= length; i++) {
                Arrays.fill(visited[i], Integer.MAX_VALUE);
            }
            visited[35][35] = 0;
            while (queue.size() != 0) {
                int x = queue.poll();
                int y = queue.poll();
                int step = queue.poll();
                for (int i = 0; i < 4; i++) {
                    int nx = x + direction[i][0];
                    int ny = y + direction[i][1];
                    if (board[nx][ny] == 0 || visited[nx][ny] <= step + 1) {
                        continue;
                    }
                    queue.add(nx);
                    queue.add(ny);
                    queue.add(step + 1);
                    board[nx][ny] = step + 1;
                    visited[nx][ny] = step + 1;
                }
            }
            int max = 0;
            for (int i = 0; i <= length; i++) {
                for (int j = 0; j <= length; j++) {
                    if (visited[i][j] == Integer.MAX_VALUE) {
                        continue;
                    }
                    max = Math.max(max, visited[i][j]);
                }
            }
            return max;
        }

        public void drawBoard(int x, int y, int status) {
            board[x][y] = status;
        }
    }
}