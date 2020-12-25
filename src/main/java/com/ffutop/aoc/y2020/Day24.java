package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @date 2020-12-24
 */
public class Day24 extends BasicDay {

    private static final Map<String, int[]> DIRECTION_MAP = new HashMap<String, int[]>() {{
        put("e", new int[] {-2,0});
        put("w", new int[] {2,0});
        put("se", new int[] {-1,-1});
        put("nw", new int[] {1,1});
        put("sw", new int[] {1,-1});
        put("ne", new int[] {-1,1});
    }};

    private static final Integer ROUNDS = 100;

    public static void main(String[] args) {
        Day24 day24 = new Day24();
        day24.solve();
    }

    private void solve() {
        List<String> lineList = readLists();
        Map<Point, Integer> pointMap = new HashMap<>();
        for (String line : lineList) {
            Point point = new Point();
            for (int i=0;i<line.length();i++) {
                String direction;
                if (line.charAt(i) == 's' || line.charAt(i) == 'n') {
                    direction = line.substring(i, i+2);
                    i++;
                } else {
                    direction = line.substring(i, i+1);
                }
                point.x += DIRECTION_MAP.get(direction)[0];
                point.y += DIRECTION_MAP.get(direction)[1];
            }
            pointMap.compute(point, (key, oldValue) -> {
                if (oldValue == null)   oldValue = 0;
                oldValue ++;
                return oldValue;
            });
        }

        int leftBound = 0;
        int rightBound = 0;
        int topBound = 0;
        int bottomBound = 0;

        int counter = 0;
        for (Map.Entry<Point, Integer> entry : pointMap.entrySet()) {
            counter += entry.getValue() % 2;
            leftBound = Math.min(leftBound, entry.getKey().x-2);
            rightBound = Math.max(rightBound, entry.getKey().x+2);
            topBound = Math.min(topBound, entry.getKey().y-1);
            bottomBound = Math.max(bottomBound, entry.getKey().y+1);
        }
        System.out.println(counter);

        for (int round=0;round<ROUNDS;round++) {
            Map<Point, Integer> nextMap = new HashMap<>();
            for (int x=leftBound;x<=rightBound;x++) {
                for (int y=topBound;y<=bottomBound;y++) {
                    Point current = new Point(x, y);
                    int countBlack = 0;
                    for (int[] direction : DIRECTION_MAP.values()) {
                        Point neighbor = new Point();
                        neighbor.x = x + direction[0];
                        neighbor.y = y + direction[1];
                        countBlack += pointMap.getOrDefault(neighbor, 0) % 2 == 1 ? 1 : 0;
                    }
                    if (pointMap.getOrDefault(current, 0) % 2 == 0) {
                        nextMap.put(current, countBlack == 2 ? 1 : 0);
                    } else {
                        nextMap.put(current, (countBlack == 0 || countBlack > 2) ? 0 : 1);
                    }
                }
            }

            leftBound-=2;
            rightBound+=2;
            topBound--;
            bottomBound++;
            pointMap = nextMap;
        }

        counter = 0;
        for (Integer blackOrWhite : pointMap.values()) {
            counter += blackOrWhite % 2;
        }
        System.out.println(counter);

    }


    private class Point {
        int x, y;

        public Point() {
        }

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
