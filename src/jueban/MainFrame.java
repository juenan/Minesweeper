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
    private MinePane pane;//扫雷面板
    private JPanel main_panel;//画板
    final int CELL_SIZE = 50;//格子大小
    private JMenuBar menubar = new JMenuBar();
    private JMenu New = new JMenu("新建");
    private JMenuItem new_game = new JMenuItem("新游戏");
    Dimension dimension;
    DialogPanel dialog;
    SelectModePanel selectpanel;
    public MainFrame(){



        this.setBounds(260,260,600,600);
        //弹出模式选择框
        selectpanel = new SelectModePanel();
        dialog = new DialogPanel(selectpanel);
        dialog.showDialog(this,"选择难度");
        dimension = selectpanel.getDimension();


        //创建扫雷面板
        pane = new MinePane((int)dimension.getWidth(),(int)dimension.getHeight(),selectpanel.getMines());
        main_panel = new JPanel(){
            public void paint(Graphics g){
                super.paint(g);
                if(pane != null){
                    pane.paintPane(this.getWidth(),this.getHeight(),g);
                }
            }
        };
        main_panel.setSize((int)dimension.getWidth()*CELL_SIZE,(int)dimension.getHeight()*CELL_SIZE);

        //为画板添加鼠标监听器
        main_panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getX() / (main_panel.getWidth()/(int)dimension.getWidth());
                int y = e.getY() / (main_panel.getHeight()/(int)dimension.getHeight());
                boolean is_fail = false;
                if(e.getClickCount() == 1){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        is_fail = pane.sweep(x,y);
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        pane.setFlag(x,y);
                    }
                }

                if(e.getClickCount() > 1){
                    if(e.getButton() == MouseEvent.BUTTON1){
                        try {
                            is_fail = pane.aroundSweep(x,y);
                        } catch (FlagNotEqualsMineException e1) {

                        }
                    }else if(e.getButton() == MouseEvent.BUTTON3){
                        pane.setFlag(x,y);
                    }
                }
                main_panel.repaint();
                if(pane.isWin()){
                    if(JOptionPane.showOptionDialog(MainFrame.this,"成功,是否再来一局?","恭喜:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null) == JOptionPane.YES_OPTION){
                        selectMode();
                    }else {
                        System.exit(0);
                    }
                }

                if(is_fail){
                    pane.uncoverAllMines();
                    main_panel.repaint();
                    if(JOptionPane.showOptionDialog(MainFrame.this,"失败,是否再来一局?","QAQ:",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.INFORMATION_MESSAGE,null,null,null) == JOptionPane.YES_OPTION){
                        selectMode();
                    }else {
                        System.exit(0);
                    }
                }



            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        //添加菜单
        menubar.add(New);
        New.add(new_game);
        new_game.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMode();
            }
        });
        this.setJMenuBar(menubar);


        this.add(main_panel,BorderLayout.CENTER);
        this.setSize((int)dimension.getWidth()*CELL_SIZE,(int)dimension.getHeight()*CELL_SIZE+50);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }




    private void selectMode(){
        dialog.showDialog(this,"选择难度");
        dimension = selectpanel.getDimension();
        pane = new MinePane((int)dimension.getWidth(),(int)dimension.getHeight(),selectpanel.getMines());
        main_panel.setSize((int)dimension.getWidth()*CELL_SIZE,(int)dimension.getHeight()*CELL_SIZE);
        main_panel.repaint();
        this.setSize((int)dimension.getWidth()*CELL_SIZE,(int)dimension.getHeight()*CELL_SIZE+50);
    }



}
