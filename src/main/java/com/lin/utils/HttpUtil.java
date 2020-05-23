package com.lin.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lin.beans.variable;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static DefaultHttpClient httpClient = new DefaultHttpClient();
    private static String charset="utf-8";
    private static Map<String,String> variable=variableUtil.getVariableAndValueMap();

    public static HttpResponse request(Map<String,String> heads,String url,String jsonParams,String method){
        HttpResponse response=null;
        try {
            response= doGet(url,jsonParams);
            /*if(method.equals("get")){
                response= doGet(url,jsonParams);
            }else{
            response= doPost(heads,url,jsonParams);}*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  response;
    }

    /*
    * 简单的post请求接口
    * */
    public static HttpResponse doPost(Map<String,String> heads,String url,String jsonParams) throws Exception{

            HttpPost post=new HttpPost(url);
            for(Map.Entry<String,String> entry:heads.entrySet()){
                post.setHeader(entry.getKey(),entry.getValue());
            }
            //未处理请求数据
            StringEntity stringEntity=new StringEntity(jsonParams,charset);
            post.setEntity(stringEntity);
            HttpResponse httpResponse=httpClient.execute(post);
            return httpResponse;

    }
    /*
    * 简单的get请求接口
    * */
    public static HttpResponse doGet(String baseUrl,String jsonParams) throws Exception{
        //将请求参数拼接到url

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            JSONObject obj=JSON.parseObject(jsonParams);
            for(Map.Entry<String,Object> entry:obj.entrySet()){
                String value=(String)entry.getValue();
                if(value.startsWith("${")&&value.endsWith("}")){
                    value=variable.get(value);
                }
                params.add(new BasicNameValuePair(entry.getKey(),value));

            }
            String url = baseUrl +'?'+ URLEncodedUtils.format(params, HTTP.UTF_8);
            System.out.println(url);
            HttpGet httpGet=new HttpGet(url);
        /*for(Map.Entry<String,String> entry:heads.entrySet()){
            httpGet.setHeader(entry.getKey(),entry.getValue());
        }*/

            HttpResponse httpResponse=httpClient.execute(httpGet);
            return httpResponse;

    }
    @Test
    public void testGet(){
        /*try {
            HttpResponse response=doGet("https://www.zengdashuai.cn/jia/findByRubbishName","{\"name\":\"${haveType}\"}");
            System.out.println( EntityUtils.toString(response.getEntity(),"utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }
}
