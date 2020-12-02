package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day20 {

    private static final int MULTIPLE = 1000;
    private static final int[][] direction = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    private static char[][] maze = new char[130][130];
    private static int row;
    private static int col;
    private static HashMap<String, int[][]> pairs;
    private static HashMap<Integer, Integer> teleport;
    private static HashMap<Integer, Boolean> out2In;

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part1. steps: %d", val1));

        Part2 part2 = new Part2();
        int val2 = part2.solve();
        System.out.println(String.format("Part2. steps: %d", val2));
    }

    private static void init() throws IOException {
        maze = new char[130][130];
        row = 0;
        col = 0;
        pairs = new HashMap<>();
        teleport = new HashMap<>();
        out2In = new HashMap<>();

        BufferedReader br = ReaderUtil.getBufferedReader("day20.in");
        String data;
        for (row = 0; (data = br.readLine()) != null; row++) {
            col = Math.max(col, data.length());
            for (int j = 0; j < data.length(); j++) {
                maze[row][j] = data.charAt(j);
            }
        }

        boolean[] out2in = new boolean[]{true, false, false, true};
        int[] spfRows = new int[]{1, 37, 91, 127};
        for (int spfIndex = 0; spfIndex < spfRows.length; spfIndex++) {
            int spfRow = spfRows[spfIndex];
            for (int j = 0; j < col; j++) {
                if (isAlpha(spfRow, j) && isAlpha(spfRow - 1, j)) {
                    add2Pairs("" + maze[spfRow - 1][j] + maze[spfRow][j], spfRow + 1, j);
                    out2In.put((spfRow + 1) * MULTIPLE + j, out2in[spfIndex]);
                } else if (isAlpha(spfRow, j) && isAlpha(spfRow + 1, j)) {
                    add2Pairs("" + maze[spfRow][j] + maze[spfRow + 1][j], spfRow - 1, j);
                    out2In.put((spfRow - 1) * MULTIPLE + j, out2in[spfIndex]);
                }
            }
        }

        int[] spfCols = new int[]{1, 37, 87, 123};
        for (int spfIndex = 0; spfIndex < spfCols.length; spfIndex++) {
            int spfCol = spfCols[spfIndex];
            for (int i = 0; i < row; i++) {
                if (isAlpha(i, spfCol) && isAlpha(i, spfCol - 1)) {
                    add2Pairs("" + maze[i][spfCol - 1] + maze[i][spfCol], i, spfCol + 1);
                    out2In.put(i * MULTIPLE + (spfCol + 1), out2in[spfIndex]);
                } else if (isAlpha(i, spfCol) && isAlpha(i, spfCol + 1)) {
                    add2Pairs("" + maze[i][spfCol] + maze[i][spfCol + 1], i, spfCol - 1);
                    out2In.put(i * MULTIPLE + (spfCol - 1), out2in[spfIndex]);
                }
            }
        }
    }

    private static void add2Pairs(String str, int x, int y) {
        int[][] lists;
        pairs.putIfAbsent(str, new int[2][2]);
        lists = pairs.get(str);
        int index = lists[0][0] == 0 ? 0 : 1;
        lists[index][0] = x;
        lists[index][1] = y;
    }

    private static boolean isAlpha(int x, int y) {
        return 'A' <= maze[x][y] && maze[x][y] <= 'Z';
    }

    private static class Part1 {

        public int solve() throws IOException {

            init();
            for (int[][] pair : pairs.values()) {
                teleport.put(pair[0][0] * MULTIPLE + pair[0][1], pair[1][0] * MULTIPLE + pair[1][1]);
                teleport.put(pair[1][0] * MULTIPLE + pair[1][1], pair[0][0] * MULTIPLE + pair[0][1]);
            }

            int tx = pairs.get("ZZ")[0][0];
            int ty = pairs.get("ZZ")[0][1];
            int sx = pairs.get("AA")[0][0];
            int sy = pairs.get("AA")[0][1];

            TreeSet<int[]> visited = new TreeSet<int[]>(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    for (int i = 0; i < 2; i++) {
                        if (o1[i] != o2[i]) {
                            return o1[i] < o2[i] ? -1 : 1;
                        }
                    }
                    return 0;
                }
            });

            PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    for (int i = 2; i >= 0; i--) {
                        if (o1[i] != o2[i]) {
                            return o1[i] < o2[i] ? -1 : 1;
                        }
                    }
                    return 0;
                }
            });

            queue.add(new int[]{sx, sy, 0});
            visited.add(new int[]{sx, sy, 0});
            while (queue.size() != 0) {
                int[] current = queue.poll();
                for (int i = 0; i < 4; i++) {
                    int[] next = new int[]{current[0] + direction[i][0], current[1] + direction[i][1], current[2] + 1};
                    if (next[0] < 0 || next[0] >= row || next[1] < 0 || next[1] >= col || maze[next[0]][next[1]] != '.') {
                        continue;
                    }
                    if (visited.contains(next)) {
                        continue;
                    }
                    if (next[0] == tx && next[1] == ty) {
                        return next[2];
                    }
                    visited.add(next);
                    queue.add(next);
                    // teleport
                    if (teleport.containsKey(next[0] * MULTIPLE + next[1])) {
                        int value = teleport.get(next[0] * MULTIPLE + next[1]);
                        next = new int[]{value / MULTIPLE, value % MULTIPLE, current[2] + 2};
                        if (visited.contains(next)) {
                            continue;
                        }
                        visited.add(next);
                        queue.add(next);
                    }
                }
            }
            return -1;
        }
    }

    private static class Part2 {

        public int solve() throws IOException {

            init();

            for (int[][] pair : pairs.values()) {
                teleport.put(pair[0][0] * MULTIPLE + pair[0][1], pair[1][0] * MULTIPLE + pair[1][1]);
                teleport.put(pair[1][0] * MULTIPLE + pair[1][1], pair[0][0] * MULTIPLE + pair[0][1]);
            }

            int tx = pairs.get("ZZ")[0][0];
            int ty = pairs.get("ZZ")[0][1];
            int sx = pairs.get("AA")[0][0];
            int sy = pairs.get("AA")[0][1];
            maze[tx][ty] = '#';
            maze[sx][sy] = '#';
            teleport.remove(sx * MULTIPLE + sy);
            teleport.remove(tx * MULTIPLE + ty);

            TreeSet<int[]> visited = new TreeSet<int[]>(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    for (int i = 0; i < 3; i++) {
                        if (o1[i] != o2[i]) {
                            return o1[i] < o2[i] ? -1 : 1;
                        }
                    }
                    return 0;
                }
            });

            PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    for (int i = 3; i >= 0; i--) {
                        if (o1[i] != o2[i]) {
                            return o1[i] < o2[i] ? -1 : 1;
                        }
                    }
                    return 0;
                }
            });

            queue.add(new int[]{sx, sy, 0, 0});
            visited.add(new int[]{sx, sy, 0, 0});
            while (queue.size() != 0) {
                int[] current = queue.poll();
                if (teleport.containsKey(current[0] * MULTIPLE + current[1])) {
                    int value = teleport.get(current[0] * MULTIPLE + current[1]);
                    boolean out2in = out2In.get(current[0] * MULTIPLE + current[1]);
                    int[] next = new int[]{value / MULTIPLE, value % MULTIPLE, out2in ? current[2] - 1 : current[2] + 1, current[3] + 1};
                    if (next[2] >= 0 && !visited.contains(next)) {
                        visited.add(next);
                        queue.add(next);
                    }
                }
                for (int i = 0; i < 4; i++) {
                    int[] next = new int[]{current[0] + direction[i][0], current[1] + direction[i][1], current[2], current[3] + 1};
                    if (next[0] < 0 || next[0] >= row || next[1] < 0 || next[1] >= col || visited.contains(next)) {
                        continue;
                    }
                    if (next[0] == tx && next[1] == ty && next[2] == 0) {
                        return next[3];
                    }
                    if (maze[next[0]][next[1]] != '.') {
                        continue;
                    }
                    visited.add(next);
                    queue.add(next);
                }
            }
            return -1;
        }
    }
}