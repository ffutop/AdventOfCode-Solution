package com.ffutop.aoc.y2020;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author fangfeng
 * @since 2020-12-16
 */
public class Day16 extends BasicDay {

    public static void main(String[] args) {
        Day16 day16 = new Day16();
        Notes notes = new Notes();
        day16.readLists(s -> {
            notes.append(s);
            return null;
        });
        System.out.println(day16.solve1(notes));
        System.out.println(day16.solve2(notes));
    }

    private long solve1(Notes notes) {
        List<Integer> limitSections = notes.limitMap.values().stream().flatMap(List::stream).collect(Collectors.toList());
        int len = limitSections.size();
        long sum = 0;
        for (String nearbyTicket : notes.nearbyTickets) {
            String[] values = nearbyTicket.split(",");
            for (String value : values) {
                Integer val = Integer.parseInt(value);
                boolean isValid = false;
                for (int i=0;i<len;i+=2) {
                    if (limitSections.get(i) <= val && val <= limitSections.get(i+1)) {
                        isValid = true;
                        break;
                    }
                }
                if (!isValid) {
                    sum += val;
                }
            }
        }
        return sum;
    }

    private long solve2(Notes notes) {
        Map<String, List<Integer>> limitMap = notes.limitMap;
        Set<Integer> invalidNearbyTickets = new HashSet<>();
        notes.nearbyTickets.add(notes.myTicket);
        int valuesSize = notes.myTicket.split(",").length;
        Set<String>[][] grid = new Set[notes.nearbyTickets.size()][valuesSize];
        Set<String>[] rows = new Set[valuesSize];
        for (int i = 0; i < notes.nearbyTickets.size(); i++) {
            String[] values = notes.nearbyTickets.get(i).split(",");
            for (int j = 0; j < values.length; j++) {
                Integer value = Integer.parseInt(values[j]);
                for (Map.Entry<String, List<Integer>> entry : limitMap.entrySet()) {
                    List<Integer> sections = entry.getValue();
                    for (int k = 0; k < sections.size(); k += 2) {
                        if (sections.get(k) <= value && value <= sections.get(k + 1)) {
                            if (grid[i][j] == null) {
                                grid[i][j] = new HashSet<>();
                            }
                            grid[i][j].add(entry.getKey());
                        }
                    }
                }
                if (grid[i][j] == null) {
                    invalidNearbyTickets.add(i);
                    break;
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int j = 0; j < valuesSize; j++) {
            Set<String> result = new HashSet<>(limitMap.keySet());
            for (int i = 0; i < notes.nearbyTickets.size(); i++) {
                if (invalidNearbyTickets.contains(i)) {
                    continue;
                }
                result.retainAll(grid[i][j]);
            }
            rows[j] = result;
            System.out.print(j + ": ");
            rows[j].forEach(x-> System.out.print(x + " "));
            System.out.println();
            queue.add(j);
        }

        while (!queue.isEmpty()) {
            int index = queue.poll();
            if (rows[index].size() == 1) {
                for (int i = 0; i < valuesSize; i++) {
                    if (i != index) {
                        rows[i].removeAll(rows[index]);
                    }
                }
            } else {
                queue.add(index);
            }
        }

        long answer = 1;
        for (int i = 0; i < valuesSize; i++) {
            if (rows[i].stream().findAny().orElse("").startsWith("departure")) {
                answer *= Integer.parseInt(notes.myTicket.split(",")[i]);
            }
        }
        return answer;
    }

    private static class Notes {
        private int index = 0;
        Map<String, List<Integer>> limitMap = new HashMap<>();
        String myTicket;
        List<String> nearbyTickets = new ArrayList<>();

        public void append(String line) {
            if ("".equals(line)) {
                index++;
                return;
            }

            switch (index) {
                case 0:
                    String[] tokens = line.split(":");
                    String key = tokens[0].trim();
                    String[] sections = tokens[1].split(" or ");
                    List<Integer> list = new ArrayList<>();
                    for (String section : sections) {
                        String[] lr = section.trim().split("-");
                        list.add(Integer.parseInt(lr[0]));
                        list.add(Integer.parseInt(lr[1]));
                    }
                    limitMap.put(key, list);
                    break;
                case 1:
                    if (!"your ticket:".equals(line)) {
                        myTicket = line;
                    }
                    break;
                case 2:
                    if (!"nearby tickets:".equals(line)) {
                        nearbyTickets.add(line);
                    }
                    break;
                default:
                    return;
            }
        }
    }
}
