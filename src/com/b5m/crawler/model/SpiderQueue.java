package com.b5m.crawler.model;

import com.b5m.crawler.model.Queue;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: /�Զ����� ����Visited���unVisited��
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: feiliu
 * @version: 1.0
 * @createdate: ${data}
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * ${data}        feiliu          1.0
 */
public class SpiderQueue {
    /**
     * �ѷ��ʵ�url���ϣ���Visited��
     */
    private static Set<Object> visitedUrl = new HashSet<>();
    /**
     * ��ӵ����ʹ��� URL ������
     */
    public static void addVisitedUrl(String url) {
        visitedUrl.add(url);
    }
    /**
     * �Ƴ����ʹ��� URL
     */
    public static void removeVisitedUrl(String url) {
        visitedUrl.remove(url);
    }
    /**
     * ����Ѿ����ʵ� URL ��Ŀ
     */
    public static int getVisitedUrlNum() {
        return visitedUrl.size();
    }
    /**
     * �����ʵ�url���ϣ���unVisited��
     */
    private static Queue unVisitedUrl = new Queue();
    /**
     * ���UnVisited����
     */
    public static Queue getUnVisitedUrl() {
        return unVisitedUrl;
    }
    /**
     * δ���ʵ�unVisitedUrl������
     */
    public static Object unVisitedUrlDeQueue() {
        return unVisitedUrl.deQueue();
    }
    /**
     * ��֤���url��unVisitedUrl��ʱ��ÿ�� URLֻ������һ��
     */
    public static void addUnvisitedUrl(String url) {
        if (url != null && !url.trim().equals("") && !visitedUrl.contains(url)
                && !unVisitedUrl.contians(url))
            unVisitedUrl.enQueue(url);
    }
    /**
     * �ж�δ���ʵ� URL�������Ƿ�Ϊ��
     */
    public static boolean unVisitedUrlsEmpty() {
        return unVisitedUrl.empty();
    }
}
