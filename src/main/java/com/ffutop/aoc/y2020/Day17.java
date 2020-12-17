package com.ffutop.aoc.y2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day17 extends BasicDay {

    public static void main(String[] args) {
        Day17 day17 = new Day17();
        System.out.println(day17.solve1());
        System.out.println(day17.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        Map<Point, Character> cube = new HashMap<>();
        int[][] range = new int[][] {{0, list.get(0).length()-1}, {0, list.size()}, {0, 0}};
        for (int y=0;y<list.size();y++) {
            String line = list.get(y);
            for (int x=0;x<line.length();x++) {
                cube.put(new Point(x, y, 0), line.charAt(x));
            }
        }

        for (int round=0;round<6;round++) {
            Map<Point, Character> nextCube = new HashMap<>();
            for (int i=0;i<range.length;i++) {
                range[i][0]--;
                range[i][1]++;
            }

            for (int x=range[0][0];x<=range[0][1];x++) {
                for (int y=range[1][0];y<=range[1][1];y++) {
                    for (int z=range[2][0];z<=range[2][1];z++) {
                        int count = 0;
                        for (int xo=-1;xo<=1;xo++) {
                            for (int yo=-1;yo<=1;yo++) {
                                for (int zo=-1;zo<=1;zo++) {
                                    if (xo==0&&yo==0&&zo==0) {
                                        continue;
                                    }
                                    count += cube.getOrDefault(new Point(x+xo,y+yo,z+zo), '.') == '.' ? 0 : 1;
                                }
                            }
                        }
                        char current = cube.getOrDefault(new Point(x,y,z), '.');
                        if ((current == '#' && 2<=count && count<=3)
                        || (current == '.' && count == 3)) {
                            nextCube.put(new Point(x,y,z), '#');
                        } else {
                            nextCube.put(new Point(x,y,z), '.');
                        }
                    }
                }
            }
            cube = nextCube;
        }

        int count = 0;
        for (Character c : cube.values()) {
            count += c == '#' ? 1 : 0;
        }
        return count;
    }

    private int solve2() {
        List<String> list = readLists();
        Map<Point, Character> cube = new HashMap<>();
        int[][] range = new int[][] {{0, list.get(0).length()-1}, {0, list.size()}, {0, 0}, {0, 0}};
        for (int y=0;y<list.size();y++) {
            String line = list.get(y);
            for (int x=0;x<line.length();x++) {
                cube.put(new Point(x, y, 0, 0), line.charAt(x));
            }
        }

        for (int round=0;round<6;round++) {
            Map<Point, Character> nextCube = new HashMap<>();
            for (int i=0;i<range.length;i++) {
                range[i][0]--;
                range[i][1]++;
            }

            for (int x=range[0][0];x<=range[0][1];x++) {
                for (int y=range[1][0];y<=range[1][1];y++) {
                    for (int z=range[2][0];z<=range[2][1];z++) {
                        for (int w=range[3][0];w<=range[3][1];w++) {
                            int count = 0;
                            for (int xo = -1; xo <= 1; xo++) {
                                for (int yo = -1; yo <= 1; yo++) {
                                    for (int zo = -1; zo <= 1; zo++) {
                                        for (int wo=-1;wo<=1;wo++) {
                                            if (xo == 0 && yo == 0 && zo == 0&&wo==0) {
                                                continue;
                                            }
                                            count += cube.getOrDefault(new Point(x + xo, y + yo, z + zo, w + wo), '.') == '.' ? 0 : 1;
                                        }
                                    }
                                }
                            }
                            char current = cube.getOrDefault(new Point(x, y, z, w), '.');
                            if ((current == '#' && 2 <= count && count <= 3)
                                    || (current == '.' && count == 3)) {
                                nextCube.put(new Point(x, y, z, w), '#');
                            } else {
                                nextCube.put(new Point(x, y, z, w), '.');
                            }
                        }
                    }
                }
            }
            cube = nextCube;
        }

        int count = 0;
        for (Character c : cube.values()) {
            count += c == '#' ? 1 : 0;
        }
        return count;
    }

    private static class Point {
        int x, y, z, w;

        public Point(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Point(int x, int y, int z, int w) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.w = w;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y &&
                    z == point.z &&
                    w == point.w;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z, w);
        }
    }
}
