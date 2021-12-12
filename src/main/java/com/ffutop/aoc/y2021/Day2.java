package com.ffutop.aoc.y2021;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author fangfeng
 * @since 2021-12-11
 */
public class Day2 extends BasicDay {

    public static void main(String[] args) {
        Day2 day2 = new Day2();
        System.out.println(day2.solve1());
        System.out.println(day2.solve2());
    }

    private long solve1() {
        AtomicInteger posHorizontal = new AtomicInteger();
        AtomicInteger posDepth = new AtomicInteger();
        readLists(s -> {
            if (s.startsWith("forward ")) {
                posHorizontal.addAndGet(Integer.valueOf(s.substring("forward ".length())));
            } else if (s.startsWith("up ")) {
                posDepth.addAndGet(-Integer.valueOf(s.substring("up ".length())));
            } else {
                posDepth.addAndGet(Integer.valueOf(s.substring("down ".length())));
            }
            return null;
        });

        return posHorizontal.get() * posDepth.get();
    }

    private long solve2() {
        AtomicLong aim = new AtomicLong();
        AtomicLong posHorizontal = new AtomicLong();
        AtomicLong posDepth = new AtomicLong();
        readLists(s -> {
            if (s.startsWith("forward ")) {
                int X = Integer.valueOf(s.substring("forward ".length()));
                posHorizontal.addAndGet(X);
                posDepth.addAndGet(X * aim.get());
            } else if (s.startsWith("up ")) {
                aim.addAndGet(-Integer.valueOf(s.substring("up ".length())));
            } else {
                aim.addAndGet(Integer.valueOf(s.substring("down ".length())));
            }
            return null;
        });

        return posHorizontal.get() * posDepth.get();
    }
}
