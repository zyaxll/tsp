/*----------------------------------------------------------------------
  File    : ACODemo.java
  Contents: ant colony optimization demonstration program
  Author  : Christian Borgelt
  History : 2005.11.18 file created
            2005.12.03 trail and inverse distance exponents exchanged
            2014.10.23 changed from LGPL license to MIT license
----------------------------------------------------------------------*/
package com.b5m.test111;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Random;


public class ACODemo extends JFrame implements MouseListener, MouseMotionListener, Runnable {

    private static final long serialVersionUID = 0x00010005L;
    public static final String VERSION = "1.5 (2015.10.23)";

    private static final Font font = new Font("Dialog", Font.BOLD, 12);
    private static final Font small = new Font("Dialog", Font.PLAIN, 10);

    private boolean isprog = false; 
    private JScrollPane scroll = null;  
    private ACOPanel panel = null;  
    private JTextField stat = null;  
    private JDialog randtsp = null;  
    private JDialog antcol = null;  
    private JDialog runopt = null;  
    private JDialog params = null;  
    private JDialog about = null;  
    private JFileChooser chooser = null;  
    private File curr = null;  
    private TSP tsp = null;  
    private Timer timer = null;  
    private int cnt = -1;    
    private int mode = 0;     
    private int mx, my;          
    private double scale, factor;   


    private JFileChooser createChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setFileHidingEnabled(true);
        fc.setAcceptAllFileFilterUsed(true);
        fc.setMultiSelectionEnabled(false);
        fc.setFileView(null);       
        return fc;                  
    }


    public void loadTSP(File file) {    
        if (file == null) {         
            if (chooser == null) {
                chooser = createChooser();
            }
            chooser.setDialogType(JFileChooser.OPEN_DIALOG);
            int r = chooser.showDialog(this, null);
            if (r != JFileChooser.APPROVE_OPTION) {
                return;
            }
            file = chooser.getSelectedFile();
        }                           
        try {                       
            this.tsp = new TSP(new FileReader(file));
            this.panel.setTSP(this.tsp); 
            this.stat.setText("traveling salesman problem loaded ("
                    + file.getName() + ").");
        } catch (IOException e) {
            String msg = e.getMessage();
            this.stat.setText(msg);
            System.err.println(msg);
            JOptionPane.showMessageDialog(this, msg,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }                           
        this.curr = file;           
    }  


    public void saveTSP(File file) {                             
        if (file == null) {
            if (chooser == null) {
                chooser = createChooser();
            }
            chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            int r = chooser.showDialog(this, null);

            if (r != JFileChooser.APPROVE_OPTION) {
                return;
            }
            file = chooser.getSelectedFile();
        }                           
        try {                      
            FileWriter writer = new FileWriter(file);
            writer.write(this.tsp.toString());
            writer.close();
        } catch (IOException e) {
            String msg = e.getMessage();
            this.stat.setText(msg);
            System.err.println(msg);
            JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
        }
        this.curr = file;           
    }


    private void genTSP(int vertcnt, long seed) {
        if (this.cnt >= 0){
            return;  
        }
        Random rand = (seed > 0) ? new Random(seed) : new Random();
        this.tsp = new TSP(vertcnt, rand);
        this.tsp.transform(10.0, 0, 0);
        this.panel.setTSP(ACODemo.this.tsp);
        this.repaint();             
        this.stat.setText("随机TSP产生...");
        this.curr = null;           
    }  


    private JDialog createRandTSP() {                             
        final JDialog dlg = new JDialog(this, "随机产生TSP...");
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        GridBagConstraints rc = new GridBagConstraints();
        JPanel grid = new JPanel(g);
        JPanel bbar;
        JLabel lbl;
        JButton btn;

        grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lc.fill =              
                rc.fill = GridBagConstraints.BOTH;
        rc.weightx = 1.0;         
        lc.weightx = 0.0;         
        lc.weighty = 0.0;         
        rc.weighty = 0.0;         
        lc.ipadx = 10;          
        lc.ipady = 10;          
        rc.gridwidth = GridBagConstraints.REMAINDER;

        lbl = new JLabel("节点个数:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JSpinner vertcnt = new JSpinner(
                new SpinnerNumberModel(30, 1, 999999, 1));
        g.setConstraints(vertcnt, rc);
        grid.add(vertcnt);

//        lbl = new JLabel("种子随机数:");
//        g.setConstraints(lbl, lc);
//        grid.add(lbl);
        final JSpinner seed = new JSpinner(
                new SpinnerNumberModel(0, 0, 999999, 1));
        g.setConstraints(seed, rc);

        bbar = new JPanel(new GridLayout(1, 2, 5, 5));
        bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));
        btn = new JButton("确定");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
                ACODemo.this.genTSP(((Integer) vertcnt.getValue()).intValue(), ((Integer) seed.getValue()).longValue());
            }
        });
        btn = new JButton("应用");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.genTSP(((Integer) vertcnt.getValue()).intValue(), ((Integer) seed.getValue()).longValue());
            }
        });
        btn = new JButton("关闭");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
            }
        });
        dlg.getContentPane().add(grid, BorderLayout.CENTER);
        dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(this);
        dlg.setLocation(664, 0);
        dlg.pack();
        return dlg;
    }  /* createRandTSP() */


    public void saveImage(File file) {                             /* --- save image to a file */
        if (file == null) {         /* if no file name is given */
            if (this.chooser == null) {
                this.chooser = this.createChooser();
            }
            this.chooser.setDialogType(JFileChooser.SAVE_DIALOG);
            int r = this.chooser.showDialog(this, null);
            if (r != JFileChooser.APPROVE_OPTION) {
                return;
            }
            file = this.chooser.getSelectedFile();
        }                           /* let the user choose a file name */
        try {                       /* open an output stream */
            FileOutputStream stream = new FileOutputStream(file);
            ImageIO.write(this.panel.makeImage(), "png", stream);
            stream.close();
        }         /* save the decision tree image */
        catch (IOException e) {     /* catch and report i/o errors */
            String msg = e.getMessage();
            stat.setText(msg);
            System.err.println(msg);
            JOptionPane.showMessageDialog(this, msg,
                    "Error", JOptionPane.ERROR_MESSAGE);
        }                           /* set the status text */
    }  /* saveImage() */


    private JDialog createAnts() {                             
        final JDialog dlg = new JDialog(this, "创建蚁群");
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        GridBagConstraints rc = new GridBagConstraints();
        JPanel grid = new JPanel(g);
        JPanel bbar;
        JLabel lbl;
        JTextArea help;
        JButton btn;

        grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lc.fill =              
                rc.fill = GridBagConstraints.BOTH;
        rc.weightx = 1.0;         
        lc.weightx = 0.0;         
        lc.weighty = 0.0;         
        rc.weighty = 0.0;         
        lc.ipadx = 10;          
        lc.ipady = 10;          
        rc.gridwidth = GridBagConstraints.REMAINDER;

        lbl = new JLabel("蚂蚁数量:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JSpinner antcnt = new JSpinner(
                new SpinnerNumberModel(30, 1, 999999, 1));
        g.setConstraints(antcnt, rc);
        grid.add(antcnt);

        lbl = new JLabel("种子随机数:");
        g.setConstraints(lbl, lc);
//        grid.add(lbl);
        final JSpinner seed = new JSpinner(
                new SpinnerNumberModel(0, 0, 999999, 1));
        g.setConstraints(seed, rc);

        lbl = new JLabel("初始信息素浓度:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField phinit = new JTextField("0");
        phinit.setFont(font);
        g.setConstraints(phinit, rc);
        grid.add(phinit);

        lbl = new JLabel("选择概率:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField exploit = new JTextField("0.2");
        exploit.setFont(font);
        g.setConstraints(exploit, rc);
        grid.add(exploit);

        lbl = new JLabel("信息素权重:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField alpha = new JTextField("1");
        alpha.setFont(font);
        g.setConstraints(alpha, rc);
        grid.add(alpha);

        lbl = new JLabel("路径长度权重:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField beta = new JTextField("1");
        beta.setFont(font);
        g.setConstraints(beta, rc);
        grid.add(beta);

        lbl = new JLabel("信息素挥发系数:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField evap = new JTextField("0.1");
        evap.setFont(font);
        g.setConstraints(evap, rc);
        grid.add(evap);

        lbl = new JLabel("路径构造系数:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField layexp = new JTextField("1");
        layexp.setFont(font);
        g.setConstraints(layexp, rc);
        grid.add(layexp);

        lbl = new JLabel("普通模式和精英模式比例:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JTextField elite = new JTextField("0.4");
        elite.setFont(font);
        g.setConstraints(elite, rc);
        grid.add(elite);

        bbar = new JPanel(new GridLayout(1, 2, 5, 5));
        bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));
        btn = new JButton("确定");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
                int s = ((Integer) seed.getValue()).intValue();
                ACODemo.this.panel.initAnts(
                        ((Integer) antcnt.getValue()).intValue(),
                        Double.parseDouble(phinit.getText()),
                        (s != 0) ? new Random(s) : new Random());
                ACODemo.this.panel.setParams(
                        Double.parseDouble(exploit.getText()),
                        Double.parseDouble(alpha.getText()),
                        Double.parseDouble(beta.getText()),
                        Double.parseDouble(layexp.getText()),
                        Double.parseDouble(elite.getText()),
                        Double.parseDouble(evap.getText()));
            }
        });
        btn = new JButton("应用");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int s = ((Integer) seed.getValue()).intValue();
                ACODemo.this.panel.initAnts(
                        ((Integer) antcnt.getValue()).intValue(),
                        Double.parseDouble(phinit.getText()),
                        (s != 0) ? new Random(s) : new Random());
                ACODemo.this.panel.setParams(
                        Double.parseDouble(exploit.getText()),
                        Double.parseDouble(alpha.getText()),
                        Double.parseDouble(beta.getText()),
                        Double.parseDouble(layexp.getText()),
                        Double.parseDouble(elite.getText()),
                        Double.parseDouble(evap.getText()));
            }
        });
        btn = new JButton("关闭");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
            }
        });

        dlg.getContentPane().add(grid, BorderLayout.CENTER);
        dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(this);
        dlg.setLocation(664, 145);
        dlg.pack();
        return dlg;
    }  /* createAnts() */


    private void runAnts(int epochs, int delay) {                             
        if (this.cnt >= 0) {        
            this.timer.stop();
            this.cnt = -1;
            return;
        }
        AntColony ants = this.panel.getAnts();
        if (ants == null){
            return;   
        }
//        else{
//        	Double length = ants.getBestLen();
//        	
//        }
        
        if (delay <= 0) {           
            while (--epochs >= 0)     
                this.panel.runAnts();   
            this.panel.repaint();     
            this.stat.setText("迭代次数: " + ants.getEpoch()
                    + ", 最佳路径长度: " + ants.getBestLen());
            return;                   
        }                           
        this.cnt = epochs;        
        this.timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (--ACODemo.this.cnt < 0) {
                    ACODemo.this.timer.stop();
                    return;
                }
                ACODemo.this.panel.runAnts(); 
                ACODemo.this.panel.repaint(); 
                AntColony ants = ACODemo.this.panel.getAnts();
                ACODemo.this.stat.setText("迭代次数: " + ants.getEpoch()
                        + ", 最佳路径长度: " + ants.getBestLen());
            }
        });                    
        this.timer.start();         
    }  


    private JDialog createRunOpt() {                             
        final JDialog dlg = new JDialog(this, "运行算法");
        GridBagLayout g = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        GridBagConstraints rc = new GridBagConstraints();
        JPanel grid = new JPanel(g);
        JPanel bbar;
        JLabel lbl;
        JTextArea help;
        JButton btn;

        grid.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        lc.fill =              
                rc.fill = GridBagConstraints.BOTH;
        rc.weightx = 1.0;         
        lc.weightx = 0.0;         
        lc.weighty = 0.0;         
        rc.weighty = 0.0;         
        lc.ipadx = 10;          
        lc.ipady = 10;          
        rc.gridwidth = GridBagConstraints.REMAINDER;

        lbl = new JLabel("迭代次数:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JSpinner epochs = new JSpinner(
                new SpinnerNumberModel(5000, 1, 999999, 1));
        g.setConstraints(epochs, rc);
        grid.add(epochs);

        lbl = new JLabel("迭代间隔时间:");
        g.setConstraints(lbl, lc);
        grid.add(lbl);
        final JSpinner delay = new JSpinner(
                new SpinnerNumberModel(200, 0, 999999, 10));
        g.setConstraints(delay, rc);
        grid.add(delay);

        bbar = new JPanel(new GridLayout(1, 2, 5, 5));
        bbar.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 3));
        btn = new JButton("确定");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
                ACODemo.this.runAnts(((Integer) epochs.getValue()).intValue(),
                        ((Integer) delay.getValue()).intValue());
            }
        });
        btn = new JButton("应用");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.runAnts(((Integer) epochs.getValue()).intValue(),
                        ((Integer) delay.getValue()).intValue());
            }
        });
        btn = new JButton("关闭");
        bbar.add(btn);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
            }
        });

        dlg.getContentPane().add(grid, BorderLayout.CENTER);
        dlg.getContentPane().add(bbar, BorderLayout.SOUTH);
        dlg.setLocationRelativeTo(this);
        dlg.setLocation(664, 465);
        dlg.pack();
        return dlg;
    }  /* createRunOpt() */


    private JDialog createAbout() {
        final JDialog dlg = new JDialog(this, "About ACODemo...", true);
        Container pane = dlg.getContentPane();
        PanelLogo logo = new PanelLogo();
        JButton btn = new JButton("Ok");
        JPanel rest = new JPanel(new BorderLayout(2, 2));
        JTextArea text = new JTextArea
                ("ACODemo\n"
                        + "An Ant Colony Optimization Demo\n"
                        + "Version " + ACODemo.VERSION + "\n\n"
                        + "written by zya\n"
                        + "JiangNan-University of wuxi\n"
                        + "e-mail: 345500675@qq.com");
        text.setBackground(this.getBackground());
        text.setFont(new Font("Dialog", Font.BOLD, 12));
        text.setEditable(false);
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
            }
        });
//        rest.add(logo, BorderLayout.NORTH);
        rest.add(btn, BorderLayout.SOUTH);
        pane.setLayout(new FlowLayout());
        pane.add(text);
        pane.add(rest);
        dlg.setLocationRelativeTo(this);
        dlg.pack();
        dlg.setResizable(false);
        return dlg;
    }  /* createAbout() */


    public void mousePressed(MouseEvent me) {                             
        int m;                      

        if (this.tsp == null) return;  
        this.mode = 0;              
        this.mx = me.getX();        
        this.my = me.getY();        
        m = me.getModifiers();      
        switch (m) {                
            case InputEvent.BUTTON1_MASK:
                this.mode = 1;
                break;   
            case InputEvent.BUTTON2_MASK:  
            case InputEvent.BUTTON3_MASK:  
                this.mode = (m == InputEvent.BUTTON2_MASK) ? 2 : 3;
                this.scale = this.panel.getScale();
                this.my -= this.scroll.getViewport().getViewPosition().y;
                break;                  
        }                           
    }  


    public void mouseDragged(MouseEvent e) {                             
            JViewport view;             
            Point refp;             
            Dimension size;             
            int xmax, ymax, d;    
            double scl;              

        if ((this.tsp == null)         
                || (this.mode <= 0)) return;  
        view = this.scroll.getViewport();
        refp = view.getViewPosition(); 
        if (this.mode == 1) {       
            refp.x += this.mx - e.getX();
            refp.y += this.my - e.getY();
            size = this.panel.getPreferredSize();
            xmax = size.width;
            ymax = size.height;
            size = view.getExtentSize();  
            xmax -= size.width;
            ymax -= size.height;
            if (refp.x > xmax) {
                this.mx -= refp.x - xmax;
                refp.x = xmax;
            }
            if (refp.x < 0) {
                this.mx -= refp.x;
                refp.x = 0;
            }
            if (refp.y > ymax) {
                this.my -= refp.y - ymax;
                refp.y = ymax;
            }
            if (refp.y < 0) {
                this.my -= refp.y;
                refp.y = 0;
            }
            view.setViewPosition(refp);
        } else {                      
            d = (e.getY() - refp.y) - this.my;
            scl = Math.pow((this.mode > 2) ? 1.004 : 1.02, d);
            this.panel.setScale(this.scale * scl);
        }                           
        this.panel.revalidate();    
        this.panel.repaint();       
    }  /* mouseDragged() */


    public void mouseReleased(MouseEvent e) {
        this.mode = 0;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }


    public void run() {                             
        JMenuBar mbar;             
        JMenu menu;             
        JMenuItem item;             

        this.getContentPane().setLayout(new BorderLayout());

        mbar = new JMenuBar();
        this.getContentPane().add(mbar, BorderLayout.NORTH);

        menu = mbar.add(new JMenu("文件"));
        menu.setMnemonic('f');
        item = menu.add(new JMenuItem("加载TSP..."));
        item.setMnemonic('l');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.loadTSP(null);
            }
        });
        item = menu.add(new JMenuItem("重新加载TSP"));
        item.setMnemonic('r');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.loadTSP(ACODemo.this.curr);
            }
        });
        item = menu.add(new JMenuItem("保存"));
        item.setMnemonic('s');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.saveTSP(ACODemo.this.curr);
            }
        });
        item = menu.add(new JMenuItem("另存为"));
        item.setMnemonic('a');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.saveTSP(null);
            }
        });
        item = menu.add(new JMenuItem("保存图像"));
        item.setMnemonic('i');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.saveImage(null);
            }
        });
        menu.addSeparator();
        item = menu.add(new JMenuItem("退出"));
        item.setMnemonic('q');
        if (this.isprog) {          /* if stand-alone program */
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }else {                      
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (ACODemo.this.about != null)
                        ACODemo.this.about.setVisible(false);
                    if (ACODemo.this.randtsp != null)
                        ACODemo.this.randtsp.setVisible(false);
                    if (ACODemo.this.antcol != null)
                        ACODemo.this.antcol.setVisible(false);
                    if (ACODemo.this.runopt != null)
                        ACODemo.this.runopt.setVisible(false);
                    if (ACODemo.this.params != null)
                        ACODemo.this.params.setVisible(false);
                    ACODemo.this.setVisible(false);
                }
            });                  
        }                           

        menu = mbar.add(new JMenu("操作"));
        menu.setMnemonic('a');
        item = menu.add(new JMenuItem("随机生成TSP..."));
        item.setMnemonic('g');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ACODemo.this.randtsp == null)
                    ACODemo.this.randtsp = createRandTSP();
                ACODemo.this.randtsp.setVisible(true);
            }
        });
        item = menu.add(new JMenuItem("创建蚁群..."));
        item.setMnemonic('c');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ACODemo.this.antcol == null)
                    ACODemo.this.antcol = createAnts();
                ACODemo.this.antcol.setVisible(true);
            }
        });
        item = menu.add(new JMenuItem("运行算法..."));
        item.setMnemonic('o');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ACODemo.this.runopt == null)
                    ACODemo.this.runopt = createRunOpt();
                ACODemo.this.runopt.setVisible(true);
            }
        });
        item = menu.add(new JMenuItem("停止算法"));
        item.setMnemonic('s');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ACODemo.this.timer == null) return;
                ACODemo.this.timer.stop();
                ACODemo.this.cnt = -1;
            }
        });
        menu.addSeparator();
        item = menu.add(new JMenuItem("重绘"));
        item.setMnemonic('r');
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ACODemo.this.panel.repaint();
            }
        });

        this.panel = new ACOPanel();
        this.panel.setLayout(new BorderLayout());
        this.panel.setPreferredSize(new Dimension(656, 656));
        this.panel.addMouseListener(this);
        this.panel.addMouseMotionListener(this);
        this.scroll = new JScrollPane(this.panel);
        this.getContentPane().add(this.scroll, BorderLayout.CENTER);

        this.stat = new JTextField("");
        this.stat.setEditable(false);
        this.getContentPane().add(this.stat, BorderLayout.SOUTH);

        this.setTitle("ACODemo");
        this.setDefaultCloseOperation(this.isprog
                ? JFrame.EXIT_ON_CLOSE : JFrame.HIDE_ON_CLOSE);
        this.setLocation(0, 0);
        this.pack();
        if (this.isprog) this.setVisible(true);
        this.stat.setText("ACODemo is up and running.");
    }  


    public ACODemo(boolean isProg) {
        this.isprog = isProg;
        try {
            EventQueue.invokeAndWait(this);
        } catch (Exception e) {
        }
    }

    public ACODemo() {
        this.isprog = false;
        try {
            EventQueue.invokeAndWait(this);
        } catch (Exception e) {
        }
    }

    public ACODemo(String title) {
        this(false);
        this.setTitle(title);
    }

    public ACODemo(File file) {
        this(false);
        this.loadTSP(file);
    }

    public ACODemo(String title, File file) {
        this(title);
        this.loadTSP(file);
    }


    public static void main(String args[]) {                            
        ACODemo v = new ACODemo(true);    
        if (args.length > 0){
            v.loadTSP(new File(args[0]));
        }
    } 

}  
