package com.b5m.mapTest;

import javax.swing.*;

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
public class MapTest extends JFrame {

    public MapTest(){
        super("demo-test按时打算");
        setSize(1000, 800);
        setLocation(50, 50);
        String path = "C:\\Users\\izene\\Desktop\\111.jpg";
        ImageIcon background = new ImageIcon(path);
        JLabel label = new JLabel(background);
        label.setBounds(0, 0, this.getWidth(), this.getHeight());
        JPanel imagePanel = (JPanel) this.getContentPane();
        imagePanel.setOpaque(false);
        this.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new MapTest();
    }
}
