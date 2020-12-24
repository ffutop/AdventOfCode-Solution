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

    public static void main(String[] args) {
        Day24 day24 = new Day24();
        System.out.println(day24.solve());
    }

    private int solve() {
        List<String> lineList = readLists();
        HashMap<Point, Integer> pointMap = new HashMap<>();
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
        int counter = 0;
        for (Map.Entry<Point, Integer> entry : pointMap.entrySet()) {
            counter += entry.getValue() % 2;
        }
        return counter;
    }

    private class Point {
        int x, y;

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
