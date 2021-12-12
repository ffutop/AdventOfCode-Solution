package com.ffutop.aoc.y2021;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author fangfeng
 * @since 2021-12-11
 */
public class Day3 extends BasicDay {

    public static void main(String[] args) {
        Day3 day3 = new Day3();
//        System.out.println(day3.solve1());
        System.out.println(day3.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        int length = list.get(0).length();
        int totalNumber = list.size();
        int gamma = 0;
        int epsilon = 0;
        for (int i=0;i<length;i++) {
            gamma<<=1;
            epsilon<<=1;
            int oneCounter = 0;
            for (String str : list) {
                oneCounter += str.charAt(i) == '1' ? 1 : 0;
            }
            if (oneCounter * 2 > totalNumber) {
                gamma += 1;
            } else {
                epsilon += 1;
            }
        }
        return gamma * epsilon;
    }

    private long solve2() {
        List<String> list = readLists();
        HashSet<String> set = new HashSet<>(list);
        long oxygen = filter(set, 0, true);
        long co2 = filter(set, 0, false);
        return oxygen * co2;
    }

    private int filter(Set<String> set, int position, boolean mostOrNot) {
        int total = set.size();
        if (total == 1) {
            return binToDex(set.stream().findFirst().get());
        }
        int oneCount = 0;
        Set<String> one = new HashSet<>();
        Set<String> zero = new HashSet<>();
        for (String str : set) {
            int oneOrNot = str.charAt(position) - '0';
            if (oneOrNot == 1) {
                oneCount ++;
                one.add(str);
            } else {
                zero.add(str);
            }
        }
        if (mostOrNot) {
            return oneCount*2>=total ? filter(one, position+1, mostOrNot) : filter(zero, position+1, mostOrNot);
        } else {
            return oneCount>=total-oneCount ? filter(zero, position+1, mostOrNot) : filter(one, position+1, mostOrNot);
        }
    }

    private int binToDex(String value) {
        int dex = 0;
        for (char c : value.toCharArray()) {
            dex <<= 1;
            dex |= (c-'0');
        }
        return dex;
    }
}
