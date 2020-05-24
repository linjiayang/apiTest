package com.lin.test;

import com.lin.beans.knowledgecollection;
import com.lin.utils.SQLSessionUtil;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import com.lin.beans.knowledgecollection;

public class testMapper {
    @Test
    public void userFindByIdTest(){
        SqlSession sqlSession=SQLSessionUtil.getSqlSession("mybatis-config.xml");
        /*knowledgecollection k=new knowledgecollection();
        k.setUserId(161403);
        k.setKnowledgeId(5);*/
        Integer num=sqlSession.selectOne("findCollectionNum",161403);
        System.out.println("sun"+num);

    }}
