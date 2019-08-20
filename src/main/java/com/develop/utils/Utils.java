package com.develop.utils;

import java.io.File;
import java.io.FileNotFoundException;

public class Utils {

    public static File getFileByFilepath(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException("Not found file: " + filepath);
        }
        return file;
    }
}
