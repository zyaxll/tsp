package com.b5m.designPatterns;


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
public class SendFactory {
//    public Sender sendType(String type){
//        if(type == "111"){
//            return new MailSend();
//        }else if(type == "222"){
//            return new MessageSend();
//        }else{
//            return null;
//        }
//    }
    public static Sender sendEmail(){
        return new MailSend();
    }

    public static Sender sendMessage(){
        return new MessageSend();
    }
}
