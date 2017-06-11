package jueban;

import javax.swing.*;
import java.awt.*;

/**
 * Created by jueban on 2017/6/10.
 */
public class SelectModePanel extends JPanel{
    final int EASY_MODE_WEIGHT = 9;
    final int EASY_MODE_HEIGHT = 9;
    final int EASY_MODE_MINES = 10;
    final int NORMOL_MODE_WEIGHT = 16;
    final int NORMOL_MODE_HEIGHT = 16;
    final int NORMOL_MODE_MINES = 40;
    final int HARD_MODE_WEIGHT = 30;
    final int HARD_MODE_HEIGHT = 16;
    final int HARD_MODE_MINES = 99;
    ButtonGroup select_group;
    JRadioButton easy_mode;
    JRadioButton normol_mode;
    JRadioButton hard_mode;
    JRadioButton user_defined_mode;
    JTextField weight;
    JTextField height;
    JTextField mines;
    public SelectModePanel(){
        select_group = new ButtonGroup();
        easy_mode = new JRadioButton("简单模式(9*9)");
        easy_mode.setSelected(true);
        normol_mode = new JRadioButton("普通模式(16*16)");
        hard_mode = new JRadioButton("困难模式(30*16)");
        user_defined_mode = new JRadioButton("自定义");
        select_group.add(easy_mode);
        select_group.add(normol_mode);
        select_group.add(hard_mode);
        select_group.add(user_defined_mode);
        this.add(easy_mode);
        this.add(normol_mode);
        this.add(hard_mode);
        this.add(user_defined_mode);
        this.add(new JLabel("宽度:"));
        weight = new JTextField(5);
        this.add(weight);
        this.add(new JLabel("高度:"));
        height = new JTextField(5);
        this.add(height);
        this.add(new JLabel("地雷个数:"));
        mines = new JTextField(5);
        this.add(mines);
    }


    public Dimension getDimension(){
        this.setLayout(new FlowLayout());
        this.setSize(300,500);
        Dimension dimension = new Dimension();
        int w = 0;
        int h = 0;
        if(easy_mode.isSelected()){
            dimension.setSize(EASY_MODE_WEIGHT,EASY_MODE_HEIGHT);
        }else if(normol_mode.isSelected()){
            dimension.setSize(NORMOL_MODE_WEIGHT,NORMOL_MODE_HEIGHT);
        }else if(hard_mode.isSelected()){
            dimension.setSize(HARD_MODE_WEIGHT,HARD_MODE_HEIGHT);
        }else if(user_defined_mode.isSelected()){
            w = Integer.parseInt(weight.getText());
            h = Integer.parseInt(height.getText());
            dimension.setSize(w,h);
        }

        return dimension;
    }

    public int getMines(){
        if(easy_mode.isSelected()){
            return EASY_MODE_MINES;
        }else if(normol_mode.isSelected()){
            return NORMOL_MODE_MINES;
        }else if(hard_mode.isSelected()){
            return HARD_MODE_MINES;
        }else {
            int m = 0;
            m = Integer.parseInt(mines.getText());
            return m;
        }
    }



}
