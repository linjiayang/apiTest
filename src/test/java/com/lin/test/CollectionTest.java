package com.lin.test;

import com.lin.beans.Case;
import com.lin.beans.knowledgecollection;
import com.lin.utils.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import sun.net.www.ParseUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CollectionTest {
    private static Integer userId;
    private SqlSession sqlSession=SQLSessionUtil.getSqlSession("mybatis-config.xml");
    private Map<String,String> variableAndValue=variableUtil.getVariableAndValueMap();
    private Map<String,String> writeBack=new HashMap<String, String>();
    @BeforeClass
    public void beforeClass(){
        userId=161403;
    }

    @DataProvider(name = "Cases")
    public Object[][] cases(){
        String[] s={"id","method","url","param","verify","preCheck","preResult","afterCheck","afterResult"};
        Object[][] data=CaseUtil.getCaseData(s,"searchCollectin");
        return data;
    }

    @Test(dataProvider = "Cases")
    public void testCollection(String id, String method,String url,String param,String verify,String preCheck,String preResult,String afterCheck,String afterResult){
        Map<String, String> headers = new HashMap<String, String>();
        HttpResponse response = HttpUtil.request(headers, url, param, method);
        Map<String,String> params= parseUtil.toMap(param);
        Assert.assertEquals(200, response.getStatusLine().getStatusCode(), "×´Ì¬Âë´íÎó");


        HttpEntity entity = response.getEntity();

			if(entity!=null){
                try {
                    entity.consumeContent();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        knowledgecollection k=new knowledgecollection();
        k.setUserId(Integer.parseInt(variableAndValue.get(params.get("userId"))));

        k.setKnowledgeId(Integer.parseInt(variableAndValue.get(params.get("kId"))));

        knowledgecollection knowledgecollection=sqlSession.selectOne(preCheck,k);
        writeBack.put(id,knowledgecollection.toString());
    }

    @AfterClass
    public void afterClass(){

        for(Map.Entry<String,String> entry:writeBack.entrySet() ){
            System.out.println(entry.getKey()+entry.getValue());
        }
        ExcelUtill.save(Case.class,"src/main/resources/test.xlsx","searchCollectin",writeBack,"afterResult");
    }

}
