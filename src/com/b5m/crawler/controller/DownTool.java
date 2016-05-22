package com.b5m.crawler.controller;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description: ���� URL ����ҳ����������Ҫ�������ҳ���ļ�����ȥ�� URL �еķ��ļ����ַ�
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: feiliu
 * @version: 1.0
 * @createdate: ${data}
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * ${data}        feiliu          1.0
 */
public class DownTool {
    private String getFileNameByUrl(String url, String contentType) {
        // �Ƴ� "http://" ���߸��ַ�
        url = url.substring(7);
        // ȷ��ץȡ����ҳ��Ϊ text/html ����
        if (contentType.indexOf("html") != -1) {
            // �����е�url�е��������ת�����»���
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
        } else {
            url = url.replaceAll("[\\?/:*|<>\"]", "_") + "."
                    + contentType.substring(contentType.lastIndexOf("/") + 1);
        }
        return url;
    }
    /**
     * ������ҳ�ֽ����鵽�����ļ���filePath ΪҪ������ļ�����Ե�ַ
     */
    private void saveToLocal(byte[] data, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(
                    new File(filePath)));
            for (int i = 0; i < data.length; i++)
                out.write(data[i]);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // ���� URL ָ�����ҳ
    public String downloadFile(String url) {
        String filePath = null;
        // 1.���� HttpClinet�������ò���
        HttpClient httpClient = new HttpClient();
        // ���� HTTP���ӳ�ʱ 5s
        httpClient.getHttpConnectionManager().getParams()
                .setConnectionTimeout(5000);
        // 2.���� GetMethod�������ò���
        GetMethod getMethod = new GetMethod(url);
        // ���� get����ʱ 5s
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // �����������Դ���
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
                new DefaultHttpMethodRetryHandler());
        // 3.ִ��GET����
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // �жϷ��ʵ�״̬��
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                        + getMethod.getStatusLine());
                filePath = null;
            }
            // 4.���� HTTP ��Ӧ����
            byte[] responseBody = getMethod.getResponseBody();// ��ȡΪ�ֽ�����
            // ������ҳ url ���ɱ���ʱ���ļ���
            filePath = "jd\\"
                    + getFileNameByUrl(url,
                    getMethod.getResponseHeader("Content-Type")
                            .getValue());
            /*filePath = getFileNameByUrl(url,
                    getMethod.getResponseHeader("Content-Type")
                            .getValue());*/
            saveToLocal(responseBody, filePath);
        } catch (HttpException e) {
            // �����������쳣��������Э�鲻�Ի��߷��ص�����������
            System.out.println("�������http��ַ�Ƿ���ȷ");
            e.printStackTrace();
        } catch (IOException e) {
            // ���������쳣
            e.printStackTrace();
        } finally {
            // �ͷ�����
            getMethod.releaseConnection();
        }
        return filePath;
    }
}
