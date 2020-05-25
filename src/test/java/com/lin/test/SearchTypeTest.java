package com.lin.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.beans.Case;
import com.lin.beans.variable;
import com.lin.utils.CaseUtil;
import com.lin.utils.ExcelUtill;
import com.lin.utils.HttpUtil;
import com.lin.utils.parseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SearchTypeTest {
    private Map<String,String> writeBack=new HashMap<String, String>();

    @Test(dataProvider ="Cases" )
    public void testSearchType(String id, String method,String url,String param,String verify) {
        Reporter.log("这是testSearchType第"+id+"个");
        Map<String, String> headers = new HashMap<String, String>();
        HttpResponse response = HttpUtil.request(headers, url, param, method);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode(), "状态码错误");
        String result = null;
        try {
            result = EntityUtils.toString(response.getEntity(), "utf-8");
            if(response.getEntity()!=null){
            response.getEntity().consumeContent();}
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String,String> check = parseUtil.toMap(verify);
        JSONObject obj = JSON.parseObject(result);
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            String key =  entry.getKey();
            if(key.equals("empty")){
                writeBack.put(id,key+":"+entry.getValue().toString());
            }
            if(check.containsKey(key)){
                Assert.assertEquals(check.get(key),entry.getValue().toString(),key+"校验错误");
            }

        }
    }
    @DataProvider(name = "Cases")
    public Object[][] cases(){
        String[] s={"id","method","url","param","verify"};
        Object[][] data=CaseUtil.getCaseData(s,"searchType");
        return data;
    }
    @AfterClass
    public void afterClass(){

        for(Map.Entry<String,String> entry:writeBack.entrySet() ){
            System.out.println(entry.getKey()+entry.getValue());
        }
        ExcelUtill.save(Case.class,"src/main/resources/test.xlsx","searchType",writeBack,"save");
    }
}
