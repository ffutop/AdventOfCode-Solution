package com.ffutop.aoc.y2020;

import com.ffutop.aoc.y2020.util.ReaderUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author fangfeng
 * @since 2020-12-01
 */
public class BasicDay {

    protected List<String> readLists() {
        String fileName = this.getClass().getSimpleName().toLowerCase() + ".in";
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = ReaderUtil.getBufferedReader(fileName);
            String line ;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException iox) {
            System.out.println("read lists failed");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    protected <T> List<T> readLists(Function<String, T> function) {
        String fileName = this.getClass().getSimpleName().toLowerCase() + ".in";
        List<T> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = ReaderUtil.getBufferedReader(fileName);
            String line ;
            while ((line = br.readLine()) != null) {
                list.add(function.apply(line));
            }
        } catch (IOException iox) {
            System.out.println("read lists failed");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    protected <T> List<T> readLists(BiFunction<String, Integer, T> function) {
        String fileName = this.getClass().getSimpleName().toLowerCase() + ".in";
        List<T> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = ReaderUtil.getBufferedReader(fileName);
            String line ;
            int lineNo = 0;
            while ((line = br.readLine()) != null) {
                list.add(function.apply(line, lineNo));
                lineNo++;
            }
        } catch (IOException iox) {
            System.out.println("read lists failed");
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
