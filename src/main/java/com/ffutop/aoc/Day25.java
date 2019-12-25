package com.ffutop.aoc;

import com.ffutop.util.IntCode;
import com.ffutop.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

public class Day25 {

    private static final String[] MOVEMENTS = new String[] {"north", "south", "east", "west"};

    public static void main(String[] args) throws IOException {
        Part1 part1 = new Part1();
        String val1 = part1.solve();
        System.out.println(String.format("Part1.\n%s", val1));
    }

    private static class Part1 {

        private String data;
        private IntCode intCode = new IntCode();
        private Queue<Long> input = new LinkedList<>();
        private Queue<Long> output = new LinkedList<>();

        private Random random = new Random();
        private static HashSet<String> items = new HashSet<>();
        private static HashSet<String> toTakes = new HashSet<>();
        private static LinkedList<String> banItems = new LinkedList<String>() {{
            add("giant electromagnet");
            add("photons");
            add("infinite loop");
            add("molten lava");
            add("escape pod");
        }};

        private void move(String movement) {
            System.out.println("move to " + movement);
            for (int i=0;i<movement.length();i++) {
                input.add((long) movement.charAt(i));
            }
            input.add(10L);
        }

        private void take(String item) {
            if (banItems.contains(item)) {
                return;
            }
            for (int i=0;i<item.length();i++) {
                input.add((long) item.charAt(i));
            }
            input.add(10L);
            boolean end = intCode.solve(input, output);
            System.out.println(end ? "END" : "NOT END");
            while (output.size()!=0) {
                output.poll();
            }
            if (end) {
                System.out.println("Ban : " + item);
                banItems.add(item);
                intCode.init(data);
            }
        }

        public String solve() throws IOException {
            BufferedReader br = ReaderUtil.getBufferedReader("day25.in");
            data = br.readLine();
            intCode.init(data);

            while (true) {
                boolean end = intCode.solve(input, output);
                StringBuilder builder = new StringBuilder();
                while (output.size() != 0) {
                    builder.append((char) Math.toIntExact(output.poll()));
                }

                String mark = builder.toString();
                if (mark.contains("You can't go that way.")) {
                    mark = "south, north, east, west";
                }
                System.out.println(mark);
                if (mark.contains("== Pressure-Sensitive Floor ==")) {
                    if (!mark.contains("Alert!")) {
                        return mark;
                    }
                    toTakes = new HashSet<>();
                    for (String item : items) {
                        System.out.println(item);
                        if (random.nextBoolean()) {
                            toTakes.add(item);
                        }
                    }
                    // restart
                    intCode.init(data);
                    continue;
                } else if (mark.contains("Items here:")) {
                    String subMark = mark.substring(mark.indexOf("Items here:"));
                    String[] tokens = subMark.split("\n");
                    String item;
                    for (String token : tokens) {
                        if (token.length() != 0 && token.charAt(0) == '-') {
                            item = token.substring(1).trim();
                            items.add(item);
                            if (toTakes.contains(item) && banItems.contains(item) == false) {
                                take("take " + item);
                            }
                        }
                    }
                }
                int next = random.nextInt(4);
                while (mark.contains(MOVEMENTS[next]) == false) {
                    next = random.nextInt(4);
                }
                move(MOVEMENTS[next]);
            }
        }
    }
}