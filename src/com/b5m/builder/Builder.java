package com.b5m.builder;


import java.util.ArrayList;
import java.util.List;

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
public class Builder {

    private List<Sender> list = new ArrayList<Sender>();

    public void sendMail(int count){
        for (int i = 0;i <= count; i++){
            list.add(new MailSend());
            System.out.println(list.size());
        }
    }
    public void sendMessage(int count){
        for (int i = 0;i <= count; i++){
            list.add(new MessageSend());
        }
    }

}
