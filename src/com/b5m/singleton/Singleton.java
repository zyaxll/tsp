package com.b5m.singleton;

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
public class Singleton {
//    private static Singleton instance = null;

    private static class SingletonFactory{
        private static Singleton instance = new Singleton();
    }

    private Singleton(){

    }

    public static Singleton getInstance(){
        return SingletonFactory.instance;
    }

    /*public static synchronized Singleton getInstance(){
        if(instance == null){
            instance = new Singleton();
        }
        return instance;
    }*/

    /*public static Singleton getInstance(){
        if(instance == null){
            synchronized (instance){
                if(instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }*/




}
