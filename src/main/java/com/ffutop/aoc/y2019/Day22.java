package com.ffutop.aoc.y2019;

import com.ffutop.aoc.y2019.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class Day22 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        int val1 = part1.solve(10007);
        System.out.println(String.format("Part1. the position of card 2019: %d", val1));

        Part2 part2 = new Part2();
        String val2 = part2.solve(2020, 119315717514047L);
        System.out.println(String.format("Part2. number on the card that ends up in position 2020: %s", val2));
    }

    private static void newStack(int[] deck, int range) {
        int temp;
        for (int i = range / 2; i < range; i++) {
            temp = deck[i];
            deck[i] = deck[range - i - 1];
            deck[range - i - 1] = temp;
        }
    }

    private static void cut(int[] deck, int cut, int range) {
        int[] temp = new int[cut];
        for (int i = 0; i < cut; i++) {
            temp[i] = deck[i];
        }
        for (int i = cut; i < range; i++) {
            deck[i - cut] = deck[i];
        }
        for (int i = range - cut; i < range; i++) {
            deck[i] = temp[i - (range - cut)];
        }
    }

    private static int[] increment(int[] deck, int increment, int range) {
        int[] fin = new int[range];
        int finCursor = 0;
        int deckCursor = 0;
        for (deckCursor = 0; deckCursor < range; deckCursor++) {
            fin[finCursor] = deck[deckCursor];
            finCursor += increment;
            if (finCursor >= range) {
                finCursor -= range;
            }
        }
        return fin;
    }

    private static class Part1 {

        private int[] deck;

        public int solve(int range) throws IOException {
            deck = new int[range];
            for (int i = 0; i < range; i++) {
                deck[i] = i;
            }

            BufferedReader br = ReaderUtil.getBufferedReader("day22.in");
            String data;
            while ((data = br.readLine()) != null) {
                if (data.contains("stack")) {
                    newStack(deck, range);
                } else if (data.contains("cut")) {
                    int cut = Integer.valueOf(data.split(" ")[1]);
                    if (cut < 0) {
                        cut = range + cut;
                    }
                    cut(deck, cut, range);
                } else if (data.contains("increment")) {
                    int increment = Integer.valueOf(data.split(" ")[3]);
                    deck = increment(deck, increment, range);
                }
            }
            for (int position = 0; position < range; position++) {
                if (deck[position] == 2019) {
                    return position;
                }
            }
            return -1;
        }
    }

    private static class Part2 {

        private static long times = 101741582076661L;
//        private static long times = 2L;

        private long func(long specificPosition, long range) throws IOException {
            ArrayList<String> datas = new ArrayList<>();
            BufferedReader br = ReaderUtil.getBufferedReader("day22.in");
            String data;
            while ((data = br.readLine()) != null) {
                datas.add(data);
            }

            for (int i = datas.size() - 1; i >= 0; i--) {
                data = datas.get(i);
                if (data.contains("stack")) {
                    specificPosition = range - specificPosition - 1;
                } else if (data.contains("cut")) {
                    long cut = Integer.valueOf(data.split(" ")[1]);
                    specificPosition = (specificPosition + cut + range) % range;
                } else if (data.contains("increment")) {
                    long increment = Integer.valueOf(data.split(" ")[3]);
                    for (int j = 0; j < increment; j++) {
                        if ((specificPosition + range * j) % increment == 0) {
                            specificPosition = (specificPosition + range * j) / increment;
                            break;
                        }
                    }
                }
            }
            return specificPosition;
        }

        public String solve(long specificPosition, long range) throws IOException {
            long positionA = specificPosition;
            long positionB = func(positionA, range);
            long positionC = func(positionB, range);

            // B = (kA + b) % mod
            // C = (kB + b) % mod
            BigInteger A = BigInteger.valueOf(positionA);
            BigInteger B = BigInteger.valueOf(positionB);
            BigInteger C = BigInteger.valueOf(positionC);

            BigInteger mod = BigInteger.valueOf(range);

            BigInteger k = C.subtract(B).multiply(B.subtract(A).modInverse(mod)).mod(mod);
            BigInteger b = B.subtract(A.multiply(k)).mod(mod);

            // X = func(func(func...(func(2020))...))
            // X = k * ( k * ( k * ... ( k * 2020 + b) + b) ... + b) + b)
            // X = k^N * 2020 + k^(N-1) * b + k^(N-2) * b + ... k^1 * b + b;
            // X = k^N * 2020 + (k^N - 1) / (k-1) * b;
            BigInteger N = BigInteger.valueOf(times);
            BigInteger X =
                    k.modPow(N, mod).multiply(BigInteger.valueOf(2020))
                            .add(
                                    k.modPow(N, mod).subtract(BigInteger.ONE).multiply(k.subtract(BigInteger.ONE).modInverse(mod)).multiply(b)
                            );
            X = X.mod(mod);
            return X.toString();
        }
    }
}