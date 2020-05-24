package com.lin.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

public class SQLSessionUtil {

    public static SqlSession getSqlSession(String fileName){
        //�����ȡ�ļ���
        String resources = fileName;
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
        return session;
    }
}
