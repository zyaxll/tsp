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
     * 使用种子初始化URL队列
     */
    private void initCrawlerWithSeeds(String[] seeds) {
        for (int i = 0; i < seeds.length; i++)
            SpiderQueue.addUnvisitedUrl(seeds[i]);
    }
    // 定义过滤器，提取以 http://www.xxxx.com开头的链接
    public void crawling(String[] seeds) {
        LinkFilter filter = new LinkFilter() {
            public boolean accept(String url) {
                if (url.startsWith("http://www."))
                    return true;
                else
                    return false;
            }
        };
        // 初始化 URL 队列
        initCrawlerWithSeeds(seeds);
        // 循环条件：待抓取的链接不空且抓取的网页不多于 1000
        while (!SpiderQueue.unVisitedUrlsEmpty()
                && SpiderQueue.getVisitedUrlNum() <= 1000) {
            // 队头 URL 出队列
            String visitUrl = (String) SpiderQueue.unVisitedUrlDeQueue();
            if (visitUrl == null)
                continue;
            DownTool downLoader = new DownTool();
            // 下载网页
            downLoader.downloadFile(visitUrl);
            // 该 URL 放入已访问的 URL 中
            SpiderQueue.addVisitedUrl(visitUrl);
            // 提取出下载网页中的 URL
            Set<String> links = HtmlParserTool.extracLinks(visitUrl, filter);
            // 新的未访问的 URL 入队
            for (String link : links) {
                SpiderQueue.addUnvisitedUrl(link);
            }
        }
    }
    // main 方法入口
    public static void main(String[] args) {
        BfsSpider crawler = new BfsSpider();
        crawler.crawling(new String[] { "http://www.jd.com" });
    }
}
