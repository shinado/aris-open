package indi.shinado.piping.util;


import com.alibaba.fastjson.JSON;

public class JsonUtil {

    public static <T> T fromJson(String json, Class<T> t){
        return JSON.parseObject(json, t);
    }

    public static String toJson(Object obj) {
        return JSON.toJSONString(obj);
    }
}
