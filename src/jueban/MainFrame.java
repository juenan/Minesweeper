package jueban;

import javax.swing.*;

import jueban.models.FlagNotEqualsMineException;
import jueban.models.MinePane;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by jueban on 2017/6/7.
 */
public class MainFrame extends JFrame {
    private MinePane pane;
    private JPanel main_panel;

    public MainFrame(){
        pane = new MinePane(30,16,99);
        main_panel = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                if(pane != null){
                    pane.paintPane(this.getWidth(),this.getHeight(),g);
                }
            }
        };


        pane.printPane();

        main_panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                /*
                int x = e.getX() / (main_panel.getWidth()/15);
                int y = e.getY() / (main_panel.getHeight()/15);
                if(e.getClickCount() == 1){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        pane.sweep(x,y);
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        pane.setFlag(x,y);
                    }
                }

                if(e.getClickCount() > 1){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        try {
                            pane.aroundSweep(x,y);
                        } catch (FlagNotEqualsMineException e1) {

                        }
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        pane.setFlag(x,y);
                    }
                }
                main_panel.repaint();*/
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / (main_panel.getWidth()/30);
                int y = e.getY() / (main_panel.getHeight()/16);
                if(e.getClickCount() == 1){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        pane.sweep(x,y);
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        pane.setFlag(x,y);
                    }
                }

                if(e.getClickCount() > 1){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        try {
                            pane.aroundSweep(x,y);
                        } catch (FlagNotEqualsMineException e1) {

                        }
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        pane.setFlag(x,y);
                    }
                }
                main_panel.repaint();
                main_panel.repaint();

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        this.add(main_panel,BorderLayout.CENTER);
        this.setSize(600,600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }




}
