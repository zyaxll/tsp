package com.b5m.crawler.model;

import java.util.LinkedList;

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
public class Queue {
    /**
     * ����һ�����У�ʹ��LinkedListʵ��
     */
    private LinkedList<Object> queue = new LinkedList<Object>(); // �����
    /**
     * ��t���뵽������
     */
    public void enQueue(Object t) {
        queue.addLast(t);
    }
    /**
     * �Ƴ������еĵ�һ����䷵��
     */
    public Object deQueue() {
        return queue.removeFirst();
    }
    /**
     * ���ض����Ƿ�Ϊ��
     */
    public boolean isQueueEmpty() {
        return queue.isEmpty();
    }
    /**
     * �жϲ����ض����Ƿ����t
     */
    public boolean contians(Object t) {
        return queue.contains(t);
    }
    /**
     * �жϲ����ض����Ƿ�Ϊ��
     */
    public boolean empty() {
        return queue.isEmpty();
    }
}
