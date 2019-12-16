package com.ffutop.aoc;

import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;

public class Day16 {

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        String val1 = part1.solve();
        System.out.println(String.format("Part1. first eight digits: %s", val1));

        Part2 part2 = new Part2();
        String val2 = part2.solve(10000);
        System.out.println(String.format("Part2. embedded eight-digit message: %s", val2));
    }

    private static class Part1 {
        public String solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day16.in");
            String data = br.readLine();
            int[] pattern = new int[]{0, 1, 0, -1};
            int length = data.length();
            for (int repeat=1;repeat<=100;repeat++) {
                StringBuilder builder = new StringBuilder();
                for (int position = 0; position < length; position++) {
                    int result = 0;
                    for (int cursor=0;cursor<length;cursor++) {
                        result += (data.charAt(cursor) - '0') * pattern[(cursor+1)/(position+1)%4];
                    }
                    result = result < 0 ? -result : result;
                    builder.append(result%10);
                }
                data = builder.toString();
            }
            return data.substring(0, 8);
        }
    }

    private static class Part2 {
        /**
         * |  1  x  x  x  x  x  x  x
         * |  0  1  x  x  x  x  x  x
         * |  0  0  1  x  x  x  x  x
         * |  0  0  0  1  x  x  x  x
         * -------------------------
         * |  0  0  0  0  1  1  1  1
         * |  0  0  0  0  0  1  1  1
         * |  0  0  0  0  0  0  1  1
         * |  0  0  0  0  0  0  0  1
         *
         * 下半部分矩阵满足上三角矩阵部分全为 1
         * 即针对字符串后一半 data[repeat][index] = sum(data[repeat-1][index])
         * @param times
         * @return
         * @throws IOException
         */
        public String solve(int times) throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day16.in");
            String data = br.readLine();
            StringBuilder msgBuilder = new StringBuilder();
            for (int i=0;i<times;i++) {
                msgBuilder.append(data);
            }
            data = msgBuilder.toString();
            int offset = Integer.valueOf(data.substring(0, 7));
            data = data.substring(offset);
            int length = data.length();
            int[] suffix = new int[length];
            for (int i=0;i<length;i++) {
                suffix[i] = data.charAt(i) - '0';
            }
            for (int repeat=1;repeat<=100;repeat++) {
                int suffixSum = 0;
                for (int index=length-1;index>=0;index--) {
                    suffixSum = (suffixSum + suffix[index]) % 10;
                    suffix[index] = suffixSum;
                }
            }
            StringBuilder builder = new StringBuilder();
            for (int i=0;i<8;i++) {
                builder.append(suffix[i]);
            }
            return builder.toString();
        }
    }
}
