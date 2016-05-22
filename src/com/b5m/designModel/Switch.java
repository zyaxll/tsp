package com.b5m.designModel;

/**
 * @description: ����
 * Copyright 2011-2015 B5M.COM. All rights reserved
 * @author: feiliu
 * @version: 1.0
 * @createdate: ${data}
 * Modification  History:
 * Date         Author        Version        Description
 * -----------------------------------------------------------------------------------
 * ${data}        feiliu          1.0
 */
//������
public class Switch {
    public IElictrical elictrical;

        public IElictrical getElictrical() {
            return elictrical;
        }

        public void setElictrical(IElictrical elictrical) {
            this.elictrical = elictrical;
        }

    //��
    public void turnOn(){
        elictrical.powerOn();
    }
    //��
    public void turnOff(){
        elictrical.powerDown();
    }
}
