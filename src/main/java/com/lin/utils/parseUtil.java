package com.lin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class parseUtil {
    public static Map<String,String> toMap(String json){
        Map<String,String> map=new HashMap<String, String>();
        JSONObject jsonObject=JSON.parseObject(json);
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
        map.put(entry.getKey(),(String)entry.getValue());
        }
        return map;
    }
}
