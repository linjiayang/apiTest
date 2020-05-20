package com.lin.utils;

import com.lin.beans.Case;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtill {
    /*   加载excel数据*/
    public static <T> List<T> load(String url,String sheetName,Class<T> clazz){
          if(null==url||"".equals(url)){
              return null;
          }
         Workbook excel;
        InputStream is;
        try {
            is=new FileInputStream(url);
            if(url.endsWith(".xls")){

                excel = new HSSFWorkbook(is);
                } else {
                excel = new XSSFWorkbook(is);
                }
                is.close();
            return transToObject(clazz, excel, sheetName);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("转换excel文件失败：" + e.getMessage());
        }


    }

    private static <T> List<T> transToObject(Class<T> clazz, Workbook xssfWorkbook, String sheetName){
        List<T> list=new ArrayList<T>();
        try {
            Sheet sheet= xssfWorkbook.getSheet(sheetName);
            //先读取列名
            Row Firstrow=sheet.getRow(0);
            int num=Firstrow.getLastCellNum();
            System.out.println(num+"-------------------");
            String[] field=new String[num+1];
            for(int i=0;i<num;i++){
                Cell cell=Firstrow.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                String name=getValue(cell);
                field[i]=name;
            }
            //for循环扫描数据
            int lastRow=sheet.getLastRowNum();
            for(int i=1;i<=lastRow;i++){
                Row rowValue=sheet.getRow(i);
                T cs=clazz.newInstance();
                if(rowValue==null||isEmptyRow(rowValue)){
                    continue;
                }
                for(int j=0;j<num;j++){
                   Cell cell= rowValue.getCell(j,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                   cell.setCellType(CellType.STRING);
                   String value=getValue(cell);
                   Method method=getMethod(field[j],clazz);
                   method.invoke(cs,value);
                }
                list.add(cs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("加载数据错误");
        }

         return list;
    }
    /*
    * 获取单元格的值
    *
    *
    * */
    private static String getValue(Cell cell) {
        if (null == cell) {
            return "";
        } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(cell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private static boolean isEmptyRow(Row row){
        int lastNum=row.getLastCellNum();
        for(int i=0;i<lastNum;i++){
            Cell cell=row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
            cell.setCellType(CellType.STRING);
            String value=cell.getStringCellValue();
            if(value!=null&&value.trim().length()>0){
                return false;
            }
        }
        return true;
    }
/*
* 获取反射方法
* */
    private static Method getMethod(String Field,Class<?> clazz){
        Method[] methods=clazz.getMethods();
        String name="set"+Field.toLowerCase();
        for(Method m:methods){
            if((m.getName().toLowerCase().equals(name))&&m.getParameterTypes().length == 1){
                return m;
            }

        }
        return null;
    }
  @Test
    public void testLoad(){
        List<Case> list=load("src/main/resources/test.xlsx","type",Case.class);
       Iterator<Case> i= list.iterator();
       while (i.hasNext()){
          Case c= i.next();
           System.out.println(c);
       }
  }
}
