package com.b5m.designPatterns;

import java.util.Objects;

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
public class MessageSend implements Sender {
    @Override
    public void send() {
        System.out.print("message");
    }
}
