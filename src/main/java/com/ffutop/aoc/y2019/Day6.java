package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day6 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val0 = part1.solve();
        System.out.println(String.format("Part1. total number of direct and indirect orbits: %d", val0));

        Part2 part2 = new Part2();
        long val1 = part2.solve();
        System.out.println(String.format("Part2. minimum number of orbital transfers required: %d", val1));
    }

    private static class Part1 {

        private static Map<String, Integer> identityMap = new HashMap<>();
        private static int identityRegister = 0;
        private static Map<Integer, List<Integer>> link = new HashMap<Integer, List<Integer>>();
        private static List<Integer> degree = new ArrayList<Integer>() {{
            add(0);
        }};
        private int count = 0;

        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day6.in");
            for (String data; (data = br.readLine()) != null; ) {
                String[] tokens = data.split("\\)");
                int[] ids = new int[2];
                for (int i = 0; i < 2; i++) {
                    ids[i] = identityMap.getOrDefault(tokens[i], 0);
                    if (ids[i] == 0) {
                        ids[i] = ++identityRegister;
                        link.put(ids[i], new ArrayList<>());
                        degree.add(0);
                        identityMap.put(tokens[i], ids[i]);
                    }
                }
                degree.set(ids[1], degree.get(ids[1]) + 1);
                link.get(ids[0]).add(ids[1]);
                link.get(ids[1]).add(ids[0]);
            }
            dfs(identityMap.get("COM"), -1, 0);
            return count;
        }

        private void dfs(int identity, int parentId, int step) {
            count += step;
            for (Integer child : link.get(identity)) {
                if (child == parentId) {
                    continue;
                }
                dfs(child, identity, step + 1);
            }
        }
    }

    private static class Part2 {

        private static Map<String, Integer> identityMap = new HashMap<>();
        private static int identityRegister = 0;
        private static Map<Integer, List<Integer>> link = new HashMap<Integer, List<Integer>>();
        private static List<Integer> degree = new ArrayList<Integer>() {{
            add(0);
        }};

        public long solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day6.in");
            for (String data; (data = br.readLine()) != null; ) {
                String[] tokens = data.split("\\)");
                int[] ids = new int[2];
                for (int i = 0; i < 2; i++) {
                    ids[i] = identityMap.getOrDefault(tokens[i], 0);
                    if (ids[i] == 0) {
                        ids[i] = ++identityRegister;
                        link.put(ids[i], new ArrayList<>());
                        degree.add(0);
                        identityMap.put(tokens[i], ids[i]);
                    }
                }
                degree.set(ids[1], degree.get(ids[1]) + 1);
                link.get(ids[0]).add(ids[1]);
                link.get(ids[1]).add(ids[0]);
            }
            return bfs(identityMap.get("YOU"));
        }

        private static int bfs(Integer you) {
            int target = identityMap.get("SAN");
            Queue<Integer> que = new LinkedList<>();
            que.add(you);
            que.add(1);
            HashSet<Integer> visited = new HashSet<>();
            while (que.size() != 0) {
                int current = que.poll();
                int step = que.poll();
                for (Integer value : link.get(current)) {
                    if (value == target) {
                        return step - 2;
                    }
                    if (visited.contains(value) == false) {
                        que.add(value);
                        que.add(step + 1);
                        visited.add(value);
                    }
                }
            }
            return -1;
        }
    }
}