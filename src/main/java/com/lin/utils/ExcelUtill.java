package com.lin.utils;

import com.lin.beans.Case;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Iterator;
import java.util.List;

public class ExcelUtill {
    public static Map<String,Integer> caseIdRowNum=new HashMap<String,Integer>();
    public static Map<String,Integer> cellNameCellNum=new HashMap<String,Integer>();
    public static Workbook workbook;
    static {
        LoadMapping("src/main/resources/test.xlsx","type");
    }
    private static void LoadMapping(String Path,String sheetName){
        InputStream inputStream=null;
        try {
            inputStream=new FileInputStream(Path);
             workbook=WorkbookFactory.create(inputStream);
            Sheet sheet=workbook.getSheet(sheetName);
            Row first=sheet.getRow(0);
            if(first!=null&&!isEmptyRow(first)){
                int cellNum=first.getLastCellNum();
                for(int i=0;i<cellNum;i++){
                    Cell cell=first.getCell(i,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String title=cell.getStringCellValue();
                    int index=cell.getAddress().getColumn();
                    cellNameCellNum.put(title,index);
                }
                for(int i=1;i<=sheet.getLastRowNum();i++){
                    Row row=sheet.getRow(i);
                    Cell cell= row.getCell(0,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    cell.setCellType(CellType.STRING);
                    String CaseId=cell.getStringCellValue();
                    int rowNum=row.getRowNum();
                    caseIdRowNum.put(CaseId,rowNum);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(inputStream!=null){
                    inputStream.close();}
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*   加载excel数据*/
    public static <T> List<T> load(String url,String sheetName,Class<T> clazz){
          if(null==url||"".equals(url)){
              return null;
          }
         Workbook excel;
        InputStream is;
        try {
            is=new FileInputStream(url);
            /*if(url.endsWith(".xls")){

                excel = new HSSFWorkbook(is);
                } else {
                excel = new XSSFWorkbook(is);
                }
                is.close();*/
            return transToObject(clazz, workbook, sheetName);
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
    * 回显数据，Map的key为caseId，value为写入数据
    * */
    public static <T> void save(Class<T> clazz,String url,String sheetName,Map<String,String> map,String field ){

        OutputStream outputStream=null;
        try {
            for(Map.Entry<String,String> entry:map.entrySet()){
                int rowNum=caseIdRowNum.get(entry.getKey());
                int cellNum=cellNameCellNum.get(field);
                outputStream=new FileOutputStream(url);

                Sheet sheet=workbook.getSheet(sheetName);
                Row row=sheet.getRow(rowNum);
                Cell cell=row.getCell(cellNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                cell.setCellType(CellType.STRING);
                cell.setCellValue(entry.getValue());
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }}

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
      /* Map<String,String> map=new HashMap<String,String>();
       map.put("1","1");
      map.put("2","1");
       save(Case.class,"src/main/resources/test.xlsx","type",map,"save");*/

  }
}
