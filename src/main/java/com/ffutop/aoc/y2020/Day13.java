package com.ffutop.aoc.y2020;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fangfeng
 * @since 2020-12-13
 */
public class Day13 extends BasicDay {

    public static void main(String[] args) {
        Day13 day13 = new Day13();
        System.out.println(day13.solve1());
        System.out.println(day13.solve2());
    }

    private int solve1() {
        List<String> list = readLists();
        int time = Integer.parseInt(list.get(0));
        String[] tokens = list.get(1).split(",");
        int multi = -1;
        int wait = time;
        for (String token : tokens) {
            if ("x".equals(token.trim())) {
                continue;
            }
            int id = Integer.parseInt(token);
            int thisWait = time % id == 0 ? 0 : id - (time % id);
            if (thisWait < wait) {
                wait = thisWait;
                multi = wait * id;
            }
        }
        return multi;
    }

    private long solve2() {
        List<String> list = readLists();
        String[] tokens = list.get(1).split(",");
        List<Integer> ids = new ArrayList<>();
        List<Integer> positions = new ArrayList<>();
        int cursor = -1;
        for (String token : tokens) {
            cursor++;
            if ("x".equals(token)) {
                continue;
            }
            Integer id = Integer.parseInt(token);
            ids.add(id);
            positions.add(cursor);
        }
        int[] a = new int[ids.size()];
        int[] m = new int[ids.size()];
        for (int i=0;i<ids.size();i++) {
            int id = ids.get(i);
            int position = positions.get(i);
            a[i] = id - position;
            m[i] = id;
        }
        return crt(a, m, ids.size());
    }

    private long crt(int[] a, int[] m, int n) {
        long M = 1;
        for (int i=0;i<n;i++) {
            M *= m[i];
        }
        long ret = 0;
        for (int i=0;i<n;i++) {
            long tm = M / m[i];
            long[] result = extendGcd(tm, m[i]);
            ret = (ret + tm * result[1] * a[i]) % M;
        }
        return (ret + M) % M;
    }

    public static long[] extendGcd(long a,long b) {
        long ans;
        long[] result;
        if (b == 0) {
            result = new long[] {a, 1, 0};
        } else {
            long[] temp = extendGcd(b, a % b);
            ans = temp[0];
            result = new long[]{ans, temp[2], temp[1] - (a / b) * temp[2]};
        }
        return result;
    }
}
