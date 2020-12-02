package com.ffutop.aoc.y2020.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

public class ReaderUtil {

    private static String relativeDirectoryPath = "/src/main/resources/2020/";

    public static BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
        String projectDirectory = System.getProperty("user.dir");
        String filePath = projectDirectory + relativeDirectoryPath + fileName;
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

}
