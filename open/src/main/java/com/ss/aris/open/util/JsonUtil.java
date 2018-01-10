package com.ss.aris.open.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {

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
