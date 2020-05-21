package com.lin.utils;
import com.lin.beans.variable;
import org.testng.annotations.Test;

import java.util.*;
public class variableUtil {
    public static Map<String,String> variableAndValue=new HashMap<String, String>();
    /*
    * 获取variable表的数据并转化为哈希表
    * */
    public static  Map<String,String> getVariableAndValueMap(){
        List<variable> list=ExcelUtill.load("variable",variable.class);
        for(variable v:list){
            variableAndValue.put(v.getName(),v.getValue());
            System.out.println(v);
        }
        return variableAndValue;
    }
    @Test
    public void testMap(){
        Map<String,String > map=getVariableAndValueMap();
        for(Map.Entry<String,String> e:map.entrySet()){
            System.out.println(e.getKey()+e.getValue());
        }
    }
}
