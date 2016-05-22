package com.b5m.java;

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
public class NumTest {
    public static void main(String[] args) {
        int a = 1;
        int b;
        int sum = 0;
        for(int i = 0; i< 1000; i++){
            b = a+i;
            sum += b;
        }
        System.out.print(sum);
    }
}
