package com.b5m.crawler;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @description: //TODO
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: feiliu
 * @version: 1.0
 * @createdate: ${data}
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * ${data}        feiliu          1.0
 */
public class Spider {
    private static HttpClient httpClient = new HttpClient();
    /**
     * @param path
     *            Ŀ����ҳ������
     * @return ���ز���ֵ����ʾ�Ƿ���������Ŀ��ҳ��
     * @throws Exception
     *             ��ȡ��ҳ����д�뱾���ļ�����IO�쳣
     */
    public static boolean downloadPage(String path) throws Exception {
        // �������������
        InputStream input = null;
        OutputStream output = null;
        // �õ� post ����
        GetMethod getMethod = new GetMethod(path);
        // ִ�У�����״̬��
        int statusCode = httpClient.executeMethod(getMethod);
        // ���״̬����д���
        // �������ֻ������ֵΪ 200 ��״̬��
        if (statusCode == HttpStatus.SC_OK) {
            input = getMethod.getResponseBodyAsStream();
            // ͨ����URL�ĵõ��ļ���
            String filename = path.substring(path.lastIndexOf('/') + 1)
                    + ".html";
            // ����ļ������
            output = new FileOutputStream(filename);
            // ������ļ�
            int tempByte = -1;
            while ((tempByte = input.read()) > 0) {
                output.write(tempByte);
            }
            // �ر�������
            if (input != null) {
                input.close();
            }
            // �ر������
            if (output != null) {
                output.close();
            }
            return true;
        }
        return false;
    }
    public static void main(String[] args) {
        try {
            // ץȡ�ٶ���ҳ�����
            Spider.downloadPage("http://www.baidu.com");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
