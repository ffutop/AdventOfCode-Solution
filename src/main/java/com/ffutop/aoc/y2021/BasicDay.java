package com.ffutop.aoc.y2021;

import com.ffutop.aoc.y2020.util.ReaderUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author fangfeng
 * @since 2021-12-11
 */
public class BasicDay {

    private static String relativeDirectoryPath = "/src/main/resources/2021/";

    public static BufferedReader getBufferedReader(String fileName) throws IOException {
        String projectDirectory = System.getProperty("user.dir");
        String filePath = projectDirectory + relativeDirectoryPath + fileName;
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

    protected char[][] readGrid() {
        List<String> list = readLists();
        int rows = list.size();
        int cols = list.get(0).length();
        char[][] grid = new char[rows][cols];
        for (int row=0;row<rows;row++) {
            for (int col=0;col<cols;col++) {
                grid[row][col] = list.get(row).charAt(col);
            }
        }
        return grid;
    }

    protected List<String> readLists() {
        String fileName = this.getClass().getSimpleName().toLowerCase() + ".in";
        List<String> list = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = getBufferedReader(fileName);
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
            br = getBufferedReader(fileName);
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
            br = getBufferedReader(fileName);
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
