package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class Day10 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int maxAsteroids = part1.solve();
        System.out.println(String.format("Part1. max asteroids: %d", maxAsteroids));

        Part2 part2 = new Part2();
        int vaporized = part2.solve();
        System.out.println(String.format("Part2. 200th vaporized: %d", vaporized));
    }

    private static int gcd(int x, int y) {
        return y != 0 ? gcd(y, x % y) : x;
    }

    private static class Part1 {
        static char[][] board = new char[21][21];
        static int bestCount = 0;
        static int row = 0;
        static int col = 0;

        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day10.in");
            String data;
            for (row = 0; (data = br.readLine()) != null; row++) {
                for (col = 0; col < data.length(); col++) {
                    board[row][col] = data.charAt(col);
                }
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '#') {
                        solve(i, j);
                    }
                }
            }
            return bestCount;
        }

        private static void solve(int x, int y) {
            char[][] map = copy();
            int count = 0;
            for (int j = 0; j < y; j++) {
                if (map[x][j] == '#') {
                    count++;
                    break;
                }
            }
            for (int j = y + 1; j < col; j++) {
                if (map[x][j] == '#') {
                    count++;
                    break;
                }
            }
            for (int i = x - 1; i >= 0; i--) {
                for (int j = 0; j < col; j++) {
                    if (map[i][j] == '#') {
                        count++;
                        int dx = x - i;
                        int dy = y - j;
                        int gcd = gcd(dx, dy);
                        gcd = Math.abs(gcd);
                        dx /= gcd;
                        dy /= gcd;
                        for (int times = 1; ; times++) {
                            int nx = x - dx * times;
                            int ny = y - dy * times;
                            if (nx < 0 || nx >= row || ny < 0 || ny >= col) {
                                break;
                            }
                            map[nx][ny] = '.';
                        }
                    }
                }
            }
            for (int i = x + 1; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (map[i][j] == '#') {
                        count++;
                        int dx = x - i;
                        int dy = y - j;
                        int gcd = gcd(dx, dy);
                        gcd = Math.abs(gcd);
                        dx /= gcd;
                        dy /= gcd;
                        for (int times = 1; ; times++) {
                            int nx = x - dx * times;
                            int ny = y - dy * times;
                            if (nx < 0 || nx >= row || ny < 0 || ny >= col) {
                                break;
                            }
                            map[nx][ny] = '.';
                        }
                    }
                }
            }
            // System.out.println(String.format("(%d,%d) = %d", x, y, count));
            bestCount = Math.max(bestCount, count);
        }

        private static char[][] copy() {
            char[][] c = new char[row][col];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    c[i][j] = board[i][j];
                }
            }
            return c;
        }
    }

    private static class Part2 {
        static char[][] board = new char[21][21];
        static int row = 0;
        static int col = 0;
        static Point prev = new Point(-1, -1);

        public static int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day10.in");
            String data;
            for (row = 0; (data = br.readLine()) != null; row++) {
                for (col = 0; col < data.length(); col++) {
                    board[row][col] = data.charAt(col);
                }
            }
            return vaporized(11, 13);
        }

        private static int vaporized(int x, int y) {
            TreeSet<Point> pointSet = new TreeSet<>(new Comparator<Point>() {
                @Override
                public int compare(Point o1, Point o2) {
                    int q1 = getQuadrant(o1);
                    int q2 = getQuadrant(o2);
                    if (q1 == q2) {
                        return o1.y * o2.x < o2.y * o1.x ? 1 : -1;
                    } else {
                        return q1 < q2 ? -1 : 1;
                    }
                }
            });
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (board[i][j] == '#') {
                        Point point = new Point(j - x, y - i);
                        pointSet.add(point);
                    }
                }
            }

            List<Point> points = new ArrayList<Point>();
            while (true) {
                if (points.size() >= 200) {
                    break;
                }
                for (Point point : pointSet) {
                    innerAdd(points, point);
                }
            }

            Point p = points.get(200);
            int cursor = 1;
            for (Point point : points) {
                // System.out.println(String.format("%03d : (%d, %d) (%d, %d)", cursor++, point.x, point.y, point.x + x, y - point.y));
            }
            return (p.x + x) * 100 + (y - p.y);
        }

        private static void innerAdd(List<Point> points, Point point) {
            if (point.used) {
                return;
            }
            if (!checkK(prev, point)) {
                points.add(point);
                point.used = true;
            }
            prev = point;
        }

        private static boolean checkK(Point prev, Point point) {
            if (prev.x == 0 && point.x == 0) {
                return (prev.y > 0 && point.y > 0) || (prev.y < 0 || point.y < 0);
            } else if (prev.y == 0 && point.y == 0) {
                return (prev.x > 0 && point.x > 0) || (prev.x < 0 || point.x < 0);
            } else if (prev.x == 0 || prev.y == 0 || point.x == 0 || point.y == 0) {
                return false;
            }
            int gcd = Math.abs(gcd(prev.x, prev.y));
            Point a = new Point(prev.x / gcd, prev.y / gcd);
            gcd = Math.abs(gcd(point.x, point.y));
            Point b = new Point(point.x / gcd, point.y / gcd);
            return a.x == b.x && a.y == b.y;
        }

        private static int getQuadrant(Point p) {
            if (p.x == 0 && p.y == 0) {
                return 0;
            }
            if (p.x >= 0 && p.y > 0) {
                return 1;
            } else if (p.x > 0 && p.y <= 0) {
                return 2;
            } else if (p.x <= 0 && p.y < 0) {
                return 3;
            } else if (p.x < 0 && p.y >= 0) {
                return 4;
            }
            System.err.println(String.format("invalid point: (%d, %d)", p.x, p.y));
            return -1;
        }
    }

    private static class Point {
        int x;
        int y;
        boolean used;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}