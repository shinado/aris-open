package com.ss.aris.open.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Administrator on 2015/11/9.
 */
public class FileUtil {

    public static void checkDir(String dir) {
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
    }

    public static byte[] getBytes(String path){
        File file = new File(path);
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bytes;
    }

    public static String getName(String path){
        if (path.contains("/")){
            int i = path.lastIndexOf("/");
            return path.substring(i).replace("/", "");
        }

        return path;
    }

    public static String readFile(String fileName) {
        //Get the text file
        File file = new File(fileName);

        //Read text from file
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        } catch (IOException e) {
            //You'll need to add proper error handling here
        }

        return text.toString();
    }

}
