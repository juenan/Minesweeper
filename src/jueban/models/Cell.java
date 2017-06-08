package jueban.models;

/**
 * Created by jueban on 2017/6/7.
 */
public class Cell {
    boolean is_mine = false;
    int around_mine_count = 0;

    public Cell(int around_mine_count) {
        this.around_mine_count = around_mine_count;
    }

    public Cell() {
        is_mine = true;
    }

    public void setmine(boolean is_mine) {
        this.is_mine = is_mine;
    }

    public boolean isMine(){
        return is_mine;
    }


    public int getArroundMineCount() {
        return around_mine_count;
    }

    public void setAroundMineCount(int around_mine_count) {
        this.around_mine_count = around_mine_count;
    }



}
