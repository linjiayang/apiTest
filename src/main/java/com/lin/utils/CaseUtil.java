package com.lin.utils;
import com.lin.beans.*;
import org.apache.poi.ss.formula.functions.T;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class CaseUtil {
    private static List<Case> list;

    public static Object[][] getCaseData(String[] Fields, String sheetName){
        Class clazz=Case.class;
        list=ExcelUtill.load("type",Case.class);
        ArrayList<Case> csList=new ArrayList<Case>();
        for(Case c:list){
            if(c.getRun().trim().equals("1")){
                csList.add(c);
            }
        }
        Object[][] datas=new Object[csList.size()][Fields.length];
        for(int i=0;i<csList.size();i++){
            Case cs=csList.get(i);
            for(int j=0;j<Fields.length;j++){
                try {
                    Method method =getMethod(Fields[j],Case.class);
                    String value=(String)method.invoke(cs);
                    datas[i][j]=value;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return datas;
       /* Class clazz=Case.class;
        ArrayList<Case> csList=new ArrayList<>();
        for(Case c:cases){
            if(c.getApiId().equals(apiId)){
                csList.add(c);
            }
        }
        Object[][] datas=new Object[csList.size()][cellNames.length];
        for(int i=0;i<csList.size();i++){
            Case cs=csList.get(i);
            for(int j=0;j<cellNames.length;j++){
                String methodName="get"+cellNames[j];
                try {
                    Method method=clazz.getMethod(methodName);
                    String value=(String)method.invoke(cs);
                    datas[i][j]=value;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return datas;*/
    }
    public static Method getMethod(String Field,Class<?> clazz){
        Method[] methods=clazz.getMethods();
        String name="get"+Field.toLowerCase();
        for(Method m:methods){
            if((m.getName().toLowerCase().equals(name))){
                return m;
            }

        }
        return null;
    }
    @Test
    public static  void test(){
        String[] s={"desc","id"};
        Object[][] o=getCaseData(s,"type");
        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                System.out.println(o[i][j]);
            }
        }
    }
}
