package com.lin.test;

import com.lin.beans.knowledgecollection;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.Reader;
import com.lin.beans.knowledgecollection;

public class testMapper {
    @Test
    public void userFindByIdTest(){
        //�����ȡ�ļ���
        String resources = "mybatis-config.xml";
        //������
        Reader reader=null;
        try {
            //��ȡmybatis-config.xml�ļ���reader������
            reader= Resources.getResourceAsReader(resources);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //��ʼ��mybatis,����SqlSessionFactory���ʵ��
        SqlSessionFactory sqlMapper=new SqlSessionFactoryBuilder().build(reader);
        //����sessionʵ��
        SqlSession session=sqlMapper.openSession();
        //���������ѯ�����ؽ��
        knowledgecollection knowlegdecollection=session.selectOne("findById",232);
        System.out.println(knowlegdecollection);
    }}
