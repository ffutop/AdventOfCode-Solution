package com.ffutop.aoc;

import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class Day18 {

    private static final int len = 81;
    private static final int[][] direction = new int[][] {{-1,0},{1,0},{0,-1},{0,1}};
    private static Part1 part1 = new Part1();
    private static Part2 part2 = new Part2();

    public static void main(String[] args) throws IOException {
        int val1 = part1.solve();
        System.out.println(String.format("Part1. shortest path that collects all of the keys: %d", val1));

        int val2 = part2.solve();
        System.out.println(String.format("Part2. fewest steps necessary to collect all of the keys: %d", val2));
    }

    private static class Part1 {

        private char[][] board = new char[len][len];
        private static final int robotId = 0;

        private TreeSet<Status> visited = new TreeSet<>(new Comparator<Status>() {
            @Override
            public int compare(Status o1, Status o2) {
                return Day18.compare(o1, o2);
            }
        });

        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day18.in");
            Status start = new Status();
            int target = 0;
            for (int i=0;i<len;i++) {
                String data = br.readLine();
                for (int j=0;j<len;j++) {
                    board[i][j] = data.charAt(j);
                    if (board[i][j] == '@') {
                        start.x = new int[] {i, 0, 0, 0};
                        start.y = new int[] {j, 0, 0, 0};
                        start.path = "";
                    }
                    if ('a' <= board[i][j] && board[i][j] <= 'z') {
                        target |= (1 << (board[i][j] - 'a' + 1));
                    }
                }
            }
            PriorityQueue<Status> queue = new PriorityQueue<Status>(new Comparator<Status>() {
                @Override
                public int compare(Status o1, Status o2) {
                    if (o1.steps == o2.steps) {
                        return Day18.compare(o1, o2);
                    }
                    return o1.steps < o2.steps ? -1 : 1;
                }
            });
            visited.add(start);
            queue.add(start);
            while (queue.size() != 0) {
                Status current = queue.poll();
                for (int i=0;i<4;i++) {
                    Status next = new Status(current);
                    next.x[robotId] += direction[i][0];
                    next.y[robotId] += direction[i][1];
                    next.steps = current.steps + 1;
                    int nx = next.x[robotId];
                    int ny = next.y[robotId];
                    if (nx<0 || nx>=len || ny<0 || ny>=len || board[nx][ny] == '#') {
                        continue;
                    }
                    char c = board[nx][ny];
                    if ('A'<=c && c<='Z' && ((next.keys&(1<<(c-'A'+1))) == 0)) {
                        continue;
                    }
                    if ('a' <= c && c <= 'z') {
                        if ((next.keys & (1<<(c-'a'+1))) == 0) {
                            next.path = next.path + c;
                        }
                        next.keys |= (1 << (c - 'a' + 1));
                    }
                    if (next.keys == target) {
                        System.out.println("Path: " + next.path);
                        part2.targetPath = next.path;
                        return next.steps;
                    }
                    if (!visited.contains(next)) {
                        queue.add(next);
                        visited.add(next);
                    }
                }
            }
            return -1;
        }
    }

    private static class Part2 {
        public String targetPath;
        private char[][] board = new char[len][len];
        private int[][] position = new int[26][2];
        private int[] robotIds = new int[26];

        private TreeSet<Status> visited = new TreeSet<>(new Comparator<Status>() {
            @Override
            public int compare(Status o1, Status o2) {
                return Day18.compare(o1, o2);
            }
        });

        private int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day18.in");
            Status status = new Status();
            int sx = 0, sy = 0;
            int target = 0;
            for (int i=0;i<len;i++) {
                String data = br.readLine();
                for (int j = 0; j < len; j++) {
                    board[i][j] = data.charAt(j);
                    if (board[i][j] == '@') {
                        sx = i;
                        sy = j;
                    }
                    if ('a' <= board[i][j] && board[i][j] <= 'z') {
                        position[board[i][j] - 'a'][0] = i;
                        position[board[i][j] - 'a'][1] = j;
                        if (i<len/2 && j<len/2) {
                            robotIds[board[i][j]-'a'] = 0;
                        } else if (i<len/2 && j>=len/2) {
                            robotIds[board[i][j]-'a'] = 1;
                        } else if (i>=len/2 && j<len/2) {
                            robotIds[board[i][j]-'a'] = 2;
                        } else {
                            robotIds[board[i][j]-'a'] = 3;
                        }
                        target |= (1 << (board[i][j] - 'a' + 1));
                    }
                }
            }
            // change input map
            for (int i=-1;i<=1;i++) {
                for (int j=-1;j<=1;j++) {
                    if (Math.abs(i) == 1 && Math.abs(j) == 1) {
                        board[sx + i][sy + j] = '.';
                    } else {
                        board[sx + i][sy + j] = '#';
                    }
                }
            }
            status.x = new int[] {sx-1,sx-1,sx+1,sx+1};
            status.y = new int[] {sy-1,sy+1,sy-1,sy+1};

            PriorityQueue<Status> queue = new PriorityQueue<Status>(new Comparator<Status>() {
                @Override
                public int compare(Status o1, Status o2) {
                    if (o1.steps == o2.steps) {
                        return Day18.compare(o1, o2);
                    }
                    return o1.steps < o2.steps ? -1 : 1;
                }
            });
            queue.add(status);
            visited.add(status);
            while (queue.size() != 0) {
                Status current = queue.poll();
                for (int i=0;i<4;i++) {
                    Status next = new Status(current);
                    next.steps ++;
                    int robotId = robotIds[targetPath.charAt(next.cursor)-'a'];
                    next.x[robotId] += direction[i][0];
                    next.y[robotId] += direction[i][1];
                    int nx = next.x[robotId];
                    int ny = next.y[robotId];
                    if (nx<0 || nx>=len || ny<0 || ny>=len || board[nx][ny] == '#') {
                        continue;
                    }
                    char c = board[nx][ny];
                    if ('A'<=c && c<='Z' && ((next.keys&(1<<(c-'A'+1))) == 0)) {
                        continue;
                    }
                    if ('a' <= c && c <= 'z') {
                        if ((next.keys & (1<<(c-'a'+1))) == 0) {
                            next.cursor++;
                        }
                        next.keys |= (1 << (c - 'a' + 1));
                    }
                    if (next.keys == target) {
                        return next.steps;
                    }
                    if (!visited.contains(next)) {
                        queue.add(next);
                        visited.add(next);
                    }
                }
            }
            return -1;
        }
    }

    private static class Status {
        int[] x, y;
        int steps;
        int keys;
        int cursor;
        String path;

        Status() {}

        Status(Status status) {
            this.x = Arrays.copyOf(status.x, 4);
            this.y = Arrays.copyOf(status.y, 4);
            this.steps = status.steps;
            this.keys = status.keys;
            this.cursor = status.cursor;
            this.path = status.path;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Status{");
            sb.append("x=").append(Arrays.toString(x));
            sb.append(", y=").append(Arrays.toString(y));
            sb.append(", steps=").append(steps);
            sb.append(", keys=").append(keys);
            sb.append(", cursor=").append(cursor);
            sb.append(", path='").append(path).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }

    private static int compare(Status o1, Status o2) {
        if (o1.keys == o2.keys) {
            for (int i=0;i<4;i++) {
                if (o1.x[i] != o2.x[i]) {
                    return o1.x[i] < o2.x[i] ? -1 : 1;
                }
                if (o1.y[i] != o2.y[i]) {
                    return o1.y[i] < o2.y[i] ? -1 : 1;
                }
            }
            return 0;
        }
        return o1.keys < o2.keys ? 1 : -1;
    }
}