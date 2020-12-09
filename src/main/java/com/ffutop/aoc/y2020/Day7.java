package com.ffutop.aoc.y2020;

import java.util.*;

public class Day7 extends BasicDay {

    public static void main(String[] args) {
        Day7 day7 = new Day7();
        System.out.println(day7.solve1());
        System.out.println(day7.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        Map<String, Set<String>> outerMap = new HashMap<>();

        for (String line : list) {
            StringBuilder builder = new StringBuilder();
            for (char c : line.toCharArray()) {
                if (('0' <= c && c <= '9') || (c == '.')) {
                    continue;
                }
                builder.append(c);
            }
            line = builder.toString();
            String[] tokens = line.split("contain");
            String outerBag = tokens[0].trim();
            String[] innerBags = tokens[1].split(",");
            for (String innerBag : innerBags) {
                innerBag = innerBag.trim();
                if (innerBag.charAt(innerBag.length()-1) != 's') {
                    innerBag = innerBag + 's';
                }
                Set<String> outerSet;
                if (outerMap.containsKey(innerBag)) {
                    outerSet = outerMap.get(innerBag);
                } else {
                    outerSet = new HashSet<>();
                }
                outerSet.add(outerBag);
                outerMap.put(innerBag, outerSet);
            }
        }

        Queue<String> queue = new LinkedList<>();
        queue.add("shiny gold bags");
        Set<String> bags = new HashSet<>();
        while (!queue.isEmpty()) {
            String bag = queue.poll();
            if (outerMap.containsKey(bag)) {
                Set<String> outerBags = outerMap.get(bag);
                for (String outerBag : outerBags) {
                    queue.add(outerBag);
                    bags.add(outerBag);
                }
            } else {
                continue;
            }
        }

        return bags.size();
    }

    private int solve2() {
        List<String> list = readLists();
        Map<String, Set<String>> innerMap = new HashMap<>();

        for (String line : list) {
            line = line.substring(0, line.length()-1);
            String[] tokens = line.split("contain");
            String outerBag = tokens[0].trim();
            String[] innerBags = tokens[1].split(",");
            Set<String> innerSet = new HashSet<>();
            for (String innerBag : innerBags) {
                innerBag = innerBag.trim();
                if (innerBag.charAt(innerBag.length()-1) != 's') {
                    innerBag = innerBag + 's';
                }
                innerSet.add(innerBag);
            }
            innerMap.put(outerBag, innerSet);
        }


        Queue<String> queue = new LinkedList<>();
        Queue<Integer> intQue = new LinkedList<>();
        queue.add("shiny gold bags");
        intQue.add(1);
        int counter = 0;
        while (!queue.isEmpty()) {
            String bag = queue.poll();
            Integer num = intQue.poll();
            counter += num;
            Set<String> innerBags = innerMap.get(bag);
            for (String innerBag : innerBags) {
                if ("no other bags".equalsIgnoreCase(innerBag)) {
                    continue;
                }
                int idx = innerBag.indexOf(' ');
                Integer count = Integer.parseInt(innerBag.substring(0, idx));
                innerBag = innerBag.substring(idx+1);
                queue.add(innerBag);
                intQue.add(count * num);
            }
        }

        return counter-1;
    }
}
