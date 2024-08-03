package com.chen.mingz.common.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

public class Establish {

    public static void createFile(String path, String content) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.createNewFile();
                FileUtils.writeStringToFile(file, content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDir(List<String> list) {
        for (String path : list) {
            createDir(path);
        }
    }

    public static void createDir(String path) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
