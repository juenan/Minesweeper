package jueban;

import javax.swing.*;
import jueban.models.MinePane;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jueban on 2017/6/7.
 */
public class MainFrame extends JFrame {
    private MinePane pane;
    private JPanel main_panel;

    public MainFrame(){
        pane = new MinePane(10,10,10);
        main_panel = new JPanel(){
            public void paint(Graphics g){
                pane.paintPane(this.getWidth(),this.getHeight(),g);
            }
        };

        pane.firstSweep(1,1);

        pane.printPane();

        this.add(main_panel,BorderLayout.CENTER);

        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }



}
