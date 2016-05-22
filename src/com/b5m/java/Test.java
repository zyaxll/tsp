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
public class Test {
    public static void main(String[] args) {
        Human human = new Human();
        human.repeatBreath(10);
//        System.out.println();
    }
}

class Human{
    private int height;


    void breath(){
        System.out.println("......");
    }

    void repeatBreath(int a){
        for(int i=0; i<a; i++){
            this.breath();
        }
    }
}
