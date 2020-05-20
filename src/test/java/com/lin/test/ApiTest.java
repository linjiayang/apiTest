package com.lin.test;

import com.lin.utils.CaseUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ApiTest {
    @Test(dataProvider ="Cases" )
    public void testCase(String id, String desc){
        System.out.println(id+desc);
    }

    @DataProvider(name = "Cases")
    public Object[][] cases(){
        String[] s={"id","desc"};
        Object[][] o=CaseUtil.getCaseData(s,"type");
        return o;
    }
}
