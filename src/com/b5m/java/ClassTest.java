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
public class ClassTest {

    int out_x = 0;

    public void method() {
        Inner1 inner1 = new Inner1();
        //在方法体内部定义的内部类
        /*public class Inner2 {
            public method() {
                out_x = 3;
            }
        }*/
//        Inner2 inner2 = new Inner2();
    }
    //在方法体外面定义的内部类
    public class Inner1{

    }


    public static void main(String[] args) {
        /*String username = "sss";
        if(username.equals("sss")){

        }
*/
    }


}
