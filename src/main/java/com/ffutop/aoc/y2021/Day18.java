package com.ffutop.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangfeng
 * @since 2021-12-18
 */
public class Day18 extends BasicDay {

    public static void main(String[] args) {
        Day18 day18 = new Day18();
        System.out.println(day18.solve1());
        System.out.println(day18.solve2());
    }

    private long solve1() {
        List<String> list = readLists();
        Pair result = null;
        for (String str : list) {
            Pair pair = parse(str, 0, str.length()-1);
            if (result == null) {
                result = pair;
            } else {
                result = add(result, pair);
            }
        }
        return result.sum();
    }

    private long solve2() {
        List<String> list = readLists();
        long maxResult = 0;

        for (int i=0;i<list.size();i++) {
            for (int j=0;j<list.size();j++) {
                if (i == j) {
                    continue;
                }
                long result = add(parse(list.get(i), 0, list.get(i).length()-1), parse(list.get(j), 0, list.get(j).length()-1)).sum();
                maxResult = Math.max(maxResult, result);
            }
        }
        return maxResult;
    }

    public Pair parse(String str, int start, int stop) {
        AtomicInteger cursor = new AtomicInteger(start + 1);
        int pair = 0;
        while (cursor.get() <= stop) {
            char c = str.charAt(cursor.getAndIncrement());
            if (c == '[') {
                pair++;
            } else if (c == ']') {
                pair--;
            } else if (c == ',' && pair == 0) {
                return new Pair(parse(str, start + 1, cursor.get() - 2), parse(str, cursor.get(), stop - 1));
            }
        }

        long value = 0;
        for (int i=start;i<=stop;i++) {
            value *= 10;
            value += str.charAt(i) - '0';
        }
        return new Pair(value);
    }

    public Pair add(Pair x, Pair y) {
        Pair pair = new Pair(x, y);

        while (true) {
            List<Pair> pairList = new ArrayList<>();
            List<Integer> levelList = new ArrayList<>();
            dfs(null, pair, 0, pairList, levelList);

            int length = pairList.size();
            boolean exploded = false;
            for (int i=0;i<length;i++) {
                if (levelList.get(i) == 5) {
                    if (i != 0) {
                        pairList.get(i-1).value += pairList.get(i).value;
                    }
                    if (i+2 != length) {
                        pairList.get(i+2).value += pairList.get(i+1).value;
                    }
                    pairList.get(i).parent.explode();
                    exploded = true;
                    break;
                }
            }
            if (exploded) {
                continue;
            }

            boolean splitted = false;
            for (int i=0;i<length;i++) {
                if (pairList.get(i).value >= 10) {
                    pairList.get(i).split();
                    splitted = true;
                    break;
                }
            }
            if (splitted) {
                continue;
            }
            break;
        }
        return pair;
    }

    private void dfs(Pair parent, Pair pair, int level, List<Pair> list, List<Integer> levelList) {
        pair.parent = parent;
        if (pair.value != null) {
            list.add(pair);
            levelList.add(level);
        } else {
            dfs(pair, pair.left, level+1, list, levelList);
            dfs(pair, pair.right, level+1, list, levelList);
        }
    }

    private static class Pair {
        Pair parent;
        Pair left;
        Pair right;
        Long value;

        public Pair(Pair left, Pair right) {
            this.left = left;
            this.right = right;
        }

        public Pair(Long value) {
            this.value = value;
        }

        public void explode() {
            value = 0L;
            left = null;
            right = null;
        }

        public void split() {
            left = new Pair(value/2);
            right = new Pair((value+1)/2);
            value = null;
        }

        public long sum() {
            if (value == null) {
                return left.sum() * 3 + right.sum() * 2;
            } else {
                return value;
            }
        }

        public void print() {
            if (value != null) {
                System.out.printf("%d", value);
            } else {
                System.out.printf("[");
                left.print();
                System.out.printf(",");
                right.print();
                System.out.printf("]");
            }
        }
    }
}
