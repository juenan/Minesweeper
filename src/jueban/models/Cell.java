package jueban.models;

/**
 * 格子
 * Created by jueban on 2017/6/7.
 */
public class Cell {
    boolean is_mine = false;
    int around_mine_count = 0;
    boolean is_cover = true;
    boolean is_flag = false;
    boolean is_doubt = false;

    public Cell(int around_mine_count) {
        this.around_mine_count = around_mine_count;
    }

    public Cell() {
        is_mine = true;
    }

    public void setMine(boolean is_mine) {
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


    public boolean IsCover() {
        return is_cover;
    }

    public void setCover(boolean is_cover) {
        this.is_cover = is_cover;
    }

    public boolean IsFlag() {
        return is_flag;
    }

    public void setFlag(boolean is_flag) {
        this.is_flag = is_flag;
    }

    public boolean IsDoubt() {
        return is_doubt;
    }

    public void setDoubt(boolean is_doubt) {
        this.is_doubt = is_doubt;
    }
}
