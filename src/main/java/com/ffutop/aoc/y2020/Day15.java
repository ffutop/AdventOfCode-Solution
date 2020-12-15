package com.ffutop.aoc.y2020;

import java.util.*;

/**
 * @author fangfeng
 * @since 2020-12-15
 */
public class Day15 extends BasicDay {

    public static void main(String[] args) {
        Day15 day15 = new Day15();
        System.out.println(day15.solve(Arrays.asList(6,13,1,15,2,0), 2020));
        System.out.println(day15.solve(Arrays.asList(6,13,1,15,2,0), 30000000));
    }

    private int solve(List<Integer> startList, int end) {
        Map<Integer, Integer> prevMap = new HashMap<>();
        Map<Integer, Integer> lastMap = new HashMap<>();
        for (int i=0;i<startList.size();i++) {
            int val = startList.get(i);
            if (lastMap.containsKey(val)) {
                prevMap.put(val, lastMap.get(val));
            }
            lastMap.put(val, i+1);
        }
        int lastNumber = startList.get(startList.size()-1);
        int nextNumber ;
        for (int i=startList.size()+1;i<=end;i++) {
            if (prevMap.containsKey(lastNumber)) {
                nextNumber = lastMap.get(lastNumber) - prevMap.get(lastNumber);
            } else {
                nextNumber = 0;
            }
            if (lastMap.containsKey(nextNumber)) {
                prevMap.put(nextNumber, lastMap.get(nextNumber));
            }
            lastMap.put(nextNumber, i);
            lastNumber = nextNumber;
        }
        return lastNumber;
    }
}
