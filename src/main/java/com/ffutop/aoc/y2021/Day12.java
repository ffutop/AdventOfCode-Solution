package com.ffutop.aoc.y2021;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangfeng
 * @since 2021-12-12
 */
public class Day12 extends BasicDay {

    public static void main(String[] args) {
        Day12 day12 = new Day12();
        System.out.println(day12.solve1());
        System.out.println(day12.solve2());
    }

    private Cave initCave() {
        List<String[]> list = readLists(s->s.split("-"));
        Map<String, List<String>> edgeMap = new HashMap<>();
        Cave cave = new Cave(edgeMap);
        for (String[] uv : list) {
            edgeMap.compute(uv[0], (key, value) -> {
                if (value == null)  {   value = new ArrayList<>();  }
                value.add(uv[1]);
                return value;
            });
            edgeMap.compute(uv[1], (key, value) -> {
                if (value == null)  {   value = new ArrayList<>();  }
                value.add(uv[0]);
                return value;
            });
            if (Character.isLowerCase(uv[0].charAt(0))) {
                cave.lowerCaseUnVisited.add(uv[0]);
            }
            if (Character.isLowerCase(uv[1].charAt(0))) {
                cave.lowerCaseUnVisited.add(uv[1]);
            }
        }
        cave.lowerCaseUnVisited.remove("start");
        return cave;
    }

    private int solve1() {
        Cave cave = initCave();
        AtomicInteger counter = new AtomicInteger();
        dfs1("start", cave, counter);
        return counter.get();
    }

    private void dfs1(String current, Cave cave, AtomicInteger counter) {
        if ("end".equals(current)) {
            counter.incrementAndGet();
            return;
        }
        for (String next : cave.edgeLink.getOrDefault(current, new ArrayList<>())) {
            if (Character.isLowerCase(next.charAt(0))) {
                if (!cave.lowerCaseUnVisited.contains(next)) {
                    continue;
                }
                cave.lowerCaseUnVisited.remove(next);
                dfs1(next, cave, counter);
                cave.lowerCaseUnVisited.add(next);
            } else {
                dfs1(next, cave, counter);
            }
        }
    }

    private int solve2() {
        Cave cave = initCave();
        AtomicInteger counter = new AtomicInteger();
        dfs2("start", cave, counter);
        return counter.get();
    }

    private void dfs2(String current, Cave cave, AtomicInteger counter) {
        if ("end".equals(current)) {
            counter.incrementAndGet();
            return;
        }
        for (String next : cave.edgeLink.getOrDefault(current, new ArrayList<>())) {
            if (Character.isLowerCase(next.charAt(0))) {
                if (!cave.lowerCaseUnVisited.contains(next)) {
                    if (!"start".equals(next) && cave.specialLowerCase == null) {
                        cave.specialLowerCase = next;
                        dfs2(next, cave, counter);
                        cave.specialLowerCase = null;
                    }
                    continue;
                }
                cave.lowerCaseUnVisited.remove(next);
                dfs2(next, cave, counter);
                cave.lowerCaseUnVisited.add(next);
            } else {
                dfs2(next, cave, counter);
            }
        }
    }

    private class Cave {
        Map<String, List<String>> edgeLink;
        Set<String> visited;
        Set<String> lowerCaseUnVisited;
        String specialLowerCase;

        public Cave(Map<String, List<String>> edgeLink) {
            this.edgeLink = edgeLink;
            this.visited = new HashSet<>();
            this.lowerCaseUnVisited = new HashSet<>();
        }
    }
}
