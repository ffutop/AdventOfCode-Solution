package com.ffutop.aoc;

import com.ffutop.util.ReaderUtil;

import java.io.*;
import java.util.*;

public class Day12 {

    static int cmp(Point o1, Point o2) {
        for (int i=0;i<3;i++) {
            if (o1.x[i] != o2.x[i]) {
                return o1.x[i] < o2.x[i] ? -1 : 1;
            }
            if (o1.v[i] != o2.v[i]) {
                return o1.v[i] < o2.v[i] ? -1 : 1;
            }
        }
        return 0;
    }

    private static TreeSet<Point[]> set = new TreeSet<Point[]>(new Comparator<Point[]>() {
        @Override
        public int compare(Point[] o1, Point[] o2) {
            for (int i=0;i<4;i++) {
                int cmp = cmp(o1[i], o2[i]);
                if (cmp != 0) {
                    return cmp;
                }
            }
            return 0;
        }
    });

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve();
        System.out.println(String.format("Part1. total energy: %d", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part2. lcm steps: %d", val2));
    }

    private static class Part1 {
        public int solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day12.in");
            Point[] points = new Point[4];
            int cursor = 0;
            for (String data; (data = br.readLine()) != null; cursor++) {
                points[cursor] = new Point(data);
            }
            for (int mark = 0; mark < 3; mark++) {
                for (int step = 1; step <= 1000; step++) {
                    for (int i = 0; i < 4; i++) {
                        int counter = 0;
                        for (int j = 0; j < 4; j++) {
                            if (points[i].x[mark] < points[j].x[mark]) {
                                counter++;
                            } else if (points[i].x[mark] > points[j].x[mark]) {
                                counter--;
                            }
                        }
                        points[i].v[mark] += counter;
                    }
                    for (int i = 0; i < 4; i++) {
                        points[i].x[mark] += points[i].v[mark];
                    }
                }
            }
            int energy = 0;
            for (int i=0;i<4;i++) {
                int pot = 0;
                int kin = 0;
                for (int mark=0;mark<3;mark++) {
                    pot += Math.abs(points[i].x[mark]);
                    kin += Math.abs(points[i].v[mark]);
                }
                energy += pot * kin;
            }
            return energy;
        }
    }

    private static class Part2 {
        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day12.in");
            Point[] points = new Point[4];
            int cursor = 0;
            for (String data; (data = br.readLine()) != null; cursor++) {
                points[cursor] = new Point(data);
            }
            int[] steps = new int[3];
            for (int mark = 0; mark < 3; mark++) {
                int finalMark = mark;
                TreeSet<Point[]> appear = new TreeSet<Point[]>(new Comparator<Point[]>() {
                    @Override
                    public int compare(Point[] o1, Point[] o2) {
                        for (int i=0;i<4;i++) {
                            if (o1[i].x[finalMark] != o2[i].x[finalMark]) {
                                return o1[i].x[finalMark] < o2[i].x[finalMark] ? -1 : 1;
                            }
                            if (o1[i].v[finalMark] != o2[i].v[finalMark]) {
                                return o1[i].v[finalMark] < o2[i].v[finalMark] ? -1 : 1;
                            }
                        }
                        return 0;
                    }
                });
                appear.add(points);
                for (int step = 1; true; step++) {
                    Point[] copy = new Point[4];
                    for (int i = 0; i < 4; i++) {
                        copy[i] = new Point(points[i]);
                    }
                    for (int i = 0; i < 4; i++) {
                        int counter = 0;
                        for (int j = 0; j < 4; j++) {
                            if (copy[i].x[mark] < copy[j].x[mark]) {
                                counter++;
                            } else if (copy[i].x[mark] > copy[j].x[mark]) {
                                counter--;
                            }
                        }
                        copy[i].v[mark] += counter;
                    }
                    for (int i = 0; i < 4; i++) {
                        copy[i].x[mark] += copy[i].v[mark];
                    }
                    if (appear.contains(copy)) {
                        steps[mark] = step;
                        break;
                    } else {
                        appear.add(copy);
                    }
                    points = copy;
                }
            }

            long lcm = steps[0];
            for (int mark=1;mark<3;mark++) {
                long gcd = gcd(lcm, steps[mark]);
                lcm = lcm * steps[mark] / gcd;
            }
            return lcm;
        }

        private long gcd(long x, long y) {
            long temp;
            while (y != 0) {
                temp = x;
                x = y;
                y = temp % y;
            }
            return x;
        }
    }

    private static long toKey(int x, int v) {
        return x * (1L<<32) + v;
    }

    private static class Point {
        int[] x = new int[3];
        int[] v = new int[3];

        Point() {}

        Point(String data) {
            data = data.substring(1, data.length()-1);
            String[] tokens = data.split(", ");
            x[0] = Integer.valueOf(tokens[0].split("=")[1]);
            x[1] = Integer.valueOf(tokens[1].split("=")[1]);
            x[2] = Integer.valueOf(tokens[2].split("=")[1]);
        }

        Point(Point p) {
            this.x = Arrays.copyOf(p.x, 3);
            this.v = Arrays.copyOf(p.v, 3);
        }

        @Override
        public String toString() {
            return "Point{" +
                    "x=" + Arrays.toString(x) +
                    ", v=" + Arrays.toString(v) +
                    '}';
        }
    }
}
