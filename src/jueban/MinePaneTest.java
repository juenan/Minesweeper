package jueban;

import jueban.models.MinePane;

/**
 * Created by jueban on 2017/6/7.
 */
public class MinePaneTest {

    public static void main(String args[]){
        MinePane pane = new MinePane(16,16,50);


        pane.printPane();
        System.out.println();
        System.out.println();
        System.out.println();
        long start = System.currentTimeMillis();
        pane.firstSweep(5,5);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
        pane.printPane();





    }
}
