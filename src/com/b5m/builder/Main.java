package com.b5m.builder;

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
public class Main {





    public static void main(String[] args) {

        String s = new String();
        String s1 = new String("aasd");
        char[] c = {1,2,3,4,5};
        String s2 = new String(c);


        Builder builder = new Builder();
        builder.sendMail(10);
        System.out.print(s + " "+ s1);
    }
}
