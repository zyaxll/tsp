package com.b5m.AOP;

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
public class Test {
    static {
        System.out.println("11111");
    }
    {
        System.out.println("22222");
    }
    public Test(int id){
        System.out.println("tset(" + id + ")");
    }

    public static void main(String[] args) {
        Test test1 = new Test(1);
        Test test2 = new Test(2);
    }
}
