package com.ffutop.aoc.y2021;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fangfeng
 * @since 2021-12-18
 */
public class Day16 extends BasicDay {

    public static void main(String[] args) {
        Day16 day16 = new Day16();
        day16.solve();
    }

    private void solve() {
        String input = readLists().get(0);
        String decoded = decodeBase64(input);
        BITS bits = new BITS();
        bits.parse(decoded, new AtomicInteger(0));
        // solve 1
        System.out.println(bits.sumVersion());
        // solve 2
        System.out.println(bits.calc());
    }

    private String decodeBase64(String encoded) {
        StringBuilder builder = new StringBuilder();
        for (char c : encoded.toCharArray()) {
            int value = 0;
            if ('0' <= c && c <= '9') {
                value = c-'0';
            } else if ('A' <= c && c <= 'F') {
                value = c - 'A' + 10;
            }
            builder.append((value&8)!=0?1:0).append((value&4)!=0?1:0).append((value&2)!=0?1:0).append((value&1)!=0?1:0);
        }
        return builder.toString();
    }

    private static class BITS {
        private int initCursor;
        private int version;
        private int typeId;
        private int lengthTypeId;
        private int length;
        private String literal;
        private long literalValue;
        private List<BITS> sub;

        private void parseVersion(String string, AtomicInteger cursor) {
            for (int i=0;i<3;i++) {
                version <<= 1;
                version |= string.charAt(cursor.getAndIncrement())-'0';
            }
        }

        private void parseTypeId(String string, AtomicInteger cursor) {
            for (int i=0;i<3;i++) {
                typeId <<= 1;
                typeId |= string.charAt(cursor.getAndIncrement())-'0';
            }
        }

        private void parseLiteral(String string, AtomicInteger cursor) {
            StringBuilder builder = new StringBuilder();
            while (string.charAt(cursor.getAndIncrement()) != '0') {
                for (int i=0;i<4;i++) {
                    builder.append(string.charAt(cursor.getAndIncrement()));
                }
            }
            for (int i=0;i<4;i++) {
                builder.append(string.charAt(cursor.getAndIncrement()));
            }
            literal = builder.toString();
            for (char c : literal.toCharArray()) {
                literalValue <<= 1;
                literalValue |= c-'0';
            }
        }

        private void parseLengthTypeId(String string, AtomicInteger cursor) {
            lengthTypeId = string.charAt(cursor.getAndIncrement()) - '0';
        }

        public void parse(String str, AtomicInteger cursor) {
            initCursor = cursor.get();
            parseVersion(str, cursor);
            parseTypeId(str, cursor);

            if (typeId == 4) {
                parseLiteral(str, cursor);
            } else {
                parseLengthTypeId(str, cursor);
                if (lengthTypeId == 0) {
                    for (int i=0;i<15;i++) {
                        length <<= 1;
                        length |= str.charAt(cursor.getAndIncrement()) - '0';
                    }
                    sub = new ArrayList<>();
                    int subStarter = cursor.get();
                    while (cursor.get() - subStarter < length) {
                        BITS subBits = new BITS();
                        sub.add(subBits);
                        subBits.parse(str, cursor);
                    }
                } else {
                    for (int i=0;i<11;i++) {
                        length <<= 1;
                        length |= str.charAt(cursor.getAndIncrement()) - '0';
                    }
                    sub = new ArrayList<>(length);
                    for (int index=0;index<length;index++) {
                        BITS subBits = new BITS();
                        sub.add(subBits);
                        subBits.parse(str, cursor);
                    }
                }
            }
        }

        public int sumVersion() {
            int sum = version;
            if (sub != null) {
                for (BITS subBits : sub) {
                    sum += subBits.sumVersion();
                }
            }
            return sum;
        }

        public long calc() {
            if (typeId == 0) {
                return sub.stream().mapToLong(bits -> bits.calc()).sum();
            } else if (typeId == 1) {
                long result = 1;
                for (BITS subBits : sub) {
                    result *= subBits.calc();
                }
                return result;
            } else if (typeId == 2) {
                return sub.stream().mapToLong(bits -> bits.calc()).min().getAsLong();
            } else if (typeId == 3) {
                return sub.stream().mapToLong(bits -> bits.calc()).max().getAsLong();
            } else if (typeId == 4) {
                return literalValue;
            } else if (typeId == 5) {
                return sub.get(0).calc() > sub.get(1).calc() ? 1 : 0;
            } else if (typeId == 6) {
                return sub.get(0).calc() < sub.get(1).calc() ? 1 : 0;
            } else if (typeId == 7) {
                return sub.get(0).calc() == sub.get(1).calc() ? 1 : 0;
            }
            return -1;
        }
    }

}
