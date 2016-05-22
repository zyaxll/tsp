package com.b5m.test111;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

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
public class PanelLogo extends JPanel implements Serializable {

    private static final long serialVersionUID = 0x00010000L;
    ImageLogo imageLogo = new ImageLogo();
    private ImageIcon logo = new ImageIcon(imageLogo.logodata);

    public Dimension getPreferredSize() {
        return new Dimension(logo.getIconWidth(), logo.getIconHeight());
    }

    public void paint(Graphics g) {
        g.drawImage(logo.getImage(), 0, 0, this);
    }
}
