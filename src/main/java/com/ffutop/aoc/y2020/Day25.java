package com.ffutop.aoc.y2020;

/**
 * @author fangfeng
 * @date 2020-12-25
 */
public class Day25 extends BasicDay {

    private static final Long MOD = 20201227L;
    private static final Long CARD_PUB_KEY = 11239946L;
    private static final Long DOOR_PUB_KEY = 10464955L;

    public static void main(String[] args) {
        long cardLoop = calcLoop(7, CARD_PUB_KEY);
        System.out.println(encrypt(DOOR_PUB_KEY, cardLoop));
    }

    private static int calcLoop(long subject, long target) {
        long current = 1L;
        for (int round=0;true;round++) {
            if (current == target) {
                return round;
            }
            current = (current * subject) % MOD;
        }
    }

    private static long encrypt(long subject, long rounds) {
        long current = 1;
        for (int round=0;round<rounds;round++) {
            current = (current * subject) % MOD;
        }
        return current;
    }
}
