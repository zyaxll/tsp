package com.b5m.crawler.controller;

import com.b5m.crawler.model.LinkFilter;
import com.b5m.crawler.model.SpiderQueue;

import java.util.Set;

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
public class BfsSpider {
    /**
     * ʹ�����ӳ�ʼ��URL����
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            SpiderQueue.addUnvisitedUrl(seeds[i]);
    }
    // �������������ȡ�� http://www.xxxx.com��ͷ������
    public void crawling(String[] seeds) {
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("http://www."))
                    return true;
                else
                    return false;
            }
        };
        // ��ʼ�� URL ����
        initCrawlerWithSeeds(seeds);
        // ѭ����������ץȡ�����Ӳ�����ץȡ����ҳ������ 1000
        while (!SpiderQueue.unVisitedUrlsEmpty()
                && SpiderQueue.getVisitedUrlNum() <= 1000) {
            // ��ͷ URL ������
            String visitUrl = (String) SpiderQueue.unVisitedUrlDeQueue();
            if (visitUrl == null)
                continue;
            DownTool downLoader = new DownTool();
            // ������ҳ
            downLoader.downloadFile(visitUrl);
            // �� URL �����ѷ��ʵ� URL ��
            SpiderQueue.addVisitedUrl(visitUrl);
            // ��ȡ��������ҳ�е� URL
            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
            // �µ�δ���ʵ� URL ���
            for (String link : links) {
                SpiderQueue.addUnvisitedUrl(link);
            }
        }
    }
    // main �������
    public static void main(String[] args) {
        BfsSpider crawler = new BfsSpider();
        crawler.crawling(new String[] { "http://www.jd.com" });
    }
}
