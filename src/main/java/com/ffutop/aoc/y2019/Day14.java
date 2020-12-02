package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.*;
import java.util.*;

public class Day14 {

    private static HashMap<String, List<String>> reactionMap;
    private static HashMap<String, Long> quantityMap;

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        long val1 = part1.solve();
        System.out.println(String.format("Part1. the minimum amount of ORE required to produce exactly 1 FUEL: %d", val1));

        Part2 part2 = new Part2();
        long val2 = part2.solve();
        System.out.println(String.format("Part2. maximum amount of FUEL: %d", val2));
    }

    private static class Part1 {
        public long solve() throws IOException {
            init();
            return findMinimalORE(1);
        }
    }

    private static class Part2 {
        public long solve() throws IOException {
            init();
            long left = 0, right = 100000000000L, mid, answer = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                long ore = findMinimalORE(mid);
                if (ore > 1000000000000L) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                    answer = mid;
                }
            }
            return answer;
        }
    }

    private static void init() throws IOException {
        BufferedReader br = ReaderUtil.getBufferedReader("day14.in");
        String data;

        reactionMap = new HashMap<>();
        quantityMap = new HashMap<>();
        while ((data = br.readLine()) != null) {
            String[] reaction = data.split(" => ");
            List<String> inputChemicals = new ArrayList<>();
            String chemicals = reaction[1].split(" ")[1];
            Long quantity = Long.valueOf(reaction[1].split(" ")[0]);
            reactionMap.put(chemicals, inputChemicals);
            quantityMap.put(chemicals, quantity);
            for (String spt : reaction[0].split(", ")) {
                chemicals = spt.split(" ")[1];
                String quantityStr = spt.split(" ")[0];
                inputChemicals.add(chemicals);
                inputChemicals.add(quantityStr);
            }
        }
    }

    private static long findMinimalORE(long fuel) {
        long ore = 0;
        Queue<String> que = new LinkedList<>();
        Queue<Long> intQue = new LinkedList<>();
        que.add("FUEL");
        intQue.add(fuel);
        HashMap<String, Long> extra = new HashMap<>();
        while (que.size() != 0) {
            String name = que.poll();
            long cnt = intQue.poll();

            long val = extra.getOrDefault(name, 0L);
            if (val != 0) {
                if (val >= cnt) {
                    extra.put(name, val - cnt);
                    continue;
                } else {
                    extra.put(name, 0L);
                    cnt -= val;
                }
            }
            if ("ORE".equalsIgnoreCase(name)) {
                ore += cnt;
                continue;
            }
            List<String> list = reactionMap.get(name);
            long times = (cnt + quantityMap.get(name) - 1) / quantityMap.get(name);
            for (int i = 0; i < list.size(); i += 2) {
                que.add(list.get(i));
                intQue.add(Long.valueOf(list.get(i + 1)) * times);
            }
            extra.put(name, extra.getOrDefault(name, 0L) + quantityMap.get(name) * times - cnt);
        }
        return ore;
    }
}
