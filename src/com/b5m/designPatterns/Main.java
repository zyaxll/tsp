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
public class Main {
    public static void main(String[] args) {
//        SendFactory sendFactory = new SendFactory();
//        Sender sender = sendFactory.sendType("222");
//        sender.send();
        Sender sender = SendFactory.sendEmail();
        sender.send();
    }
}
