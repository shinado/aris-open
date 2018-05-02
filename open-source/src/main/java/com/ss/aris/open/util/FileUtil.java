package com.ss.aris.open.util;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    public static void checkDir(String dir) {
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
    }

    public static String getFileName(String url){
        if (url == null) return "";

        int start = url.lastIndexOf("/");
        int end = url.length();
        try {
            return url.substring(start + 1, end);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static byte[] getBytes(File file){
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

    public static byte[] getBytes(String path){
        File file = new File(path);
        return getBytes(file);
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

    public static String getStringFromAssets(Context context, String name){
        try {
            InputStream in = context.getResources().getAssets().open(name);
            int length = in.available();
            byte[] buffer = new byte[length];
            in.read(buffer);
            in.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
