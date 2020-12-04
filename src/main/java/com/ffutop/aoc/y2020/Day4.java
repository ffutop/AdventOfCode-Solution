package com.ffutop.aoc.y2020;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fangfeng
 * @since 2020-12-04
 */
public class Day4 extends BasicDay {

    private static final String[] FIELDS = new String[] {"byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"};
    private Map<String, Validator> validatorMap = new HashMap<String, Validator>() {{
        put("byr", (value) -> {
            Integer birth = Integer.parseInt(value);
            return 1920 <= birth && birth <= 2002;
        });
        put("iyr", (value) -> {
            Integer issue = Integer.parseInt(value);
            return 2010 <= issue && issue <= 2020;
        });
        put("eyr", (value) -> {
            Integer expiration = Integer.parseInt(value);
            return 2020 <= expiration && expiration <= 2030;
        });
        put("hgt", (value) -> {
            if (value.endsWith("cm")) {
                Integer hgt = Integer.valueOf(value.substring(0, value.length()-2));
                return 150 <= hgt && hgt <= 193;
            } else if (value.endsWith("in")) {
                Integer hgt = Integer.valueOf(value.substring(0, value.length()-2));
                return 59 <= hgt && hgt <= 76;
            } else {
                return false;
            }
        });
        put("hcl", (value) -> {
            if (!value.startsWith("#") || value.length() != 7) {
                return false;
            }
            char[] chars = value.toCharArray();
            for (int i=1;i<7;i++) {
                if (('0' <= chars[i] && chars[i] <= '9') || ('a' <= chars[i] && chars[i] <= 'f')) {

                } else {
                    return false;
                }
            }
            return true;
        });
        put("ecl", (value) -> {
            final String[] ecls = new String[] {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
            for (String ecl : ecls) {
                if (ecl.equals(value)) {
                    return true;
                }
            }
            return false;
        });
        put("pid", (value) -> value != null && value.length() == 9);
    }};

    public static void main(String[] args) {
        Day4 day4 = new Day4();
        System.out.println(day4.solve(false));
        System.out.println(day4.solve(true));
    }

    private int solve(boolean moreCheck) {
        List<String> list = readLists();
        list.add("");
        int len = list.size();
        int counter = 0;
        HashMap<String, String> kv = new HashMap<>();
        for (int cursor=0;cursor < len;cursor++) {
            String line = list.get(cursor);
            if ("".equals(line)) {
                counter ++;
                for (String field : FIELDS) {
                    if (!kv.containsKey(field)) {
                        counter --;
                        break;
                    }
                    if (moreCheck && (kv.get(field) == null || !validatorMap.get(field).valid(kv.get(field)))) {
                        counter--;
                        break;
                    }
                }
                kv = new HashMap<>();
            } else {
                String[] tokens = line.split(" ");
                for (String token : tokens) {
                    String[] kvs = token.split(":");
                    kv.put(kvs[0], kvs[1]);
                }
            }
        }
        return counter;
    }

    @FunctionalInterface
    private interface Validator {
        boolean valid(String value);
    }
}
