package com.b5m.designModel;

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
public class Light implements IElictrical{
    @Override
    public void powerOn() {
        System.out.println("Light  dakai");
    }

    @Override
    public void powerDown() {
        System.out.println("Light  guanbi");
    }
}
