package com.ss.aris.open.util;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;

public class JsonUtil {

    public static <T> java.util.List<T> parseArray(String json, Class<T> t){
        try {
            return JSON.parseArray(json, t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> t){
        try {
            return JSON.parseObject(json, t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String toJson(Object obj) {
        try {
            return JSON.toJSONString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}