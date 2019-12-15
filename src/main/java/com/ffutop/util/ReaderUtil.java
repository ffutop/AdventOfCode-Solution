package com.ffutop.util;

import java.io.*;

public class ReaderUtil {

    private static String relativeDirectoryPath = "/src/main/resources/2019/";

    public static BufferedReader getBufferedReader(String fileName) throws FileNotFoundException {
        String projectDirectory = System.getProperty("user.dir");
        String filePath = projectDirectory + relativeDirectoryPath + fileName;
        return new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

}
