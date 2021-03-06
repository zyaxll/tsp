package com.b5m.designModel;

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
        IElictrical fan = new Fenshan();
        IElictrical light = new Light();

        Switch fenSwitch = new FenshanSwitch();
        Switch lightSwitch = new LightSwitch();

        fenSwitch.elictrical = fan;
        fenSwitch.turnOn();
        fenSwitch.turnOff();

        lightSwitch.elictrical = light;
        lightSwitch.turnOn();
        lightSwitch.turnOff();



    }
}
