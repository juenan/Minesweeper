package jueban.models;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 扫雷面板
 * Created by jueban on 2017/6/7.
 */
public class MinePane {
    final int MAX_WEIGHT = 100;//最大宽度
    final int MAX_HEIGHT = 100;//最大长度

    int weight,height,mine_count;//扫雷面板的宽度，长度和雷的数量 长度宽度最大值为100;
    Cell cells[][];//网格数组

    public MinePane(int weight, int height, int mine_count) {
        //初始化数据
        if(weight < MAX_WEIGHT)this.weight = weight;else this.weight = MAX_WEIGHT;
        if(height < MAX_HEIGHT)this.height = height;else this.height = MAX_HEIGHT;
        if(mine_count < (weight*height))this.mine_count = mine_count;
        else this.mine_count = this.height*this.weight-1;

        //初始化格子
        initCell();
    }

    /**
     * 初始化格子
     */
    private void initCell(){
        cells = new Cell[height][weight];

        //初始化格子并将雷放入数组前面
        int mine = 0;
        for(int y = 0;y<height;y++){
            for(int x = 0;x<weight;x++){
                if(mine < mine_count){
                    cells[y][x] = new Cell();
                    mine++;
                }else {
                    cells[y][x] = new Cell(0);
                }//if
            }//for
        }//for

        //将雷的位置打乱，采用洗牌算法
        int x1 = 0;
        int y1 = 0;
        for(int i = 0;i<mine_count;i++){
            int x = (int)(Math.random()*weight) % weight;
            int y = (int)(Math.random()*height) % height;
            Cell temp = cells[y1][x1];
            cells[y1][x1] = cells[y][x];
            cells[y][x] = temp;
            if(x1<weight-1){
                x1++;
            }else {
                y1++;
                x1=0;
            }//if
        }//for

        //计算非雷格子周围雷数
        flushCellsMineCount();
    }

    /**
     * 第一次扫雷
     * @param x x轴
     * @param y y轴
     */
    public void firstSweep(int x,int y){
        if(cells[y][x].isMine()){
            int x1 = (int)(Math.random()*weight) % weight;
            int y1 = (int)(Math.random()*height) % height;
            while(cells[y1][x1].isMine()){
                if(x1<(weight-1)){
                    x1++;
                }else if(x1==(weight-1) && y1<(height-1)){
                    x1=0;
                    y1++;
                }else {
                    y1=0;
                    x1=0;
                }
            }
            Cell temp = cells[y][x];
            cells[y][x] = cells[y1][x1];
            cells[y1][x1] = temp;
        }
        flushCellsMineCount();
        sweep(x,y);

    }

    /**
     * 计算格子周围雷的数量
     * @param x 格子的x坐标
     * @param y 格子的y坐标
     * @return 格子周围雷的数量
     */
    private int computMineCount(int x,int y){
        int mine_count = 0;
        Cell around_cell[] = aroundCells(x,y);
        for(int i = 0;i<around_cell.length;i++){
            if(around_cell[i].isMine())mine_count++;
        }
        return mine_count;

    }

    /**
     * 计算所有非雷格子周围雷数
     */
    private void flushCellsMineCount(){
        for(int y = 0;y<height;y++) {
            for (int x = 0; x < weight; x++) {
                cells[y][x].setAroundMineCount(computMineCount(x,y));
            }//for
        }//for
    }

    /**
     * 扫雷    TBD    TBD       TBD           TBD              TBD                   TBD
     * 改正不会连续清除周围格子周围雷数为0的问题
     * @param x x坐标
     * @param y y坐标
     * @return 是否为雷
     */
    public boolean sweep(int x,int y){
        cells[y][x].setCover(false);
        return cells[y][x].isMine();
    }

    /**
     * 扫描周围的雷
     * @param x
     * @param y
     * @return 返回是否打开地雷格子
     */
    public boolean aroundSweep(int x,int y) throws FlagNotEqualsMineException {
        //查看该坐标周围其中的数量
        Cell arount_cells[] = aroundCells(x,y);
        int flag_count = 0;
        for(int i = 0;i<arount_cells.length;i++){
            if(arount_cells[i].isFlag())flag_count++;
        }

        //如果周围旗数等于该格子around_mine_count则打开周围的格子
        if(flag_count==cells[y][x].getArroundMineCount()){
            for(int i = 0;i<arount_cells.length;i++){
                if(arount_cells[i].isFlag())continue;
                if(arount_cells[i].isDoubt())continue;
                if(arount_cells[i].isMine()){
                    arount_cells[i].setCover(false);
                    return true;
                }else {
                    arount_cells[i].setCover(false);
                }

            }
        }else {
            throw new FlagNotEqualsMineException();
        }

        return false;
    }

    /**
     * 设置某个格子的状态
     * @param x
     * @param y
     */
    public void setFlag(int x,int y){
        if(!cells[y][x].isFlag() && !cells[y][x].isDoubt()){
            cells[y][x].setFlag(true);
        }else if(cells[y][x].isFlag() && !cells[y][x].isDoubt()){
            cells[y][x].setFlag(false);
            cells[y][x].setDoubt(true);
        }else if(cells[y][x].isDoubt()){
            cells[y][x].setDoubt(false);
        }
    }

    /**
     * 返回某格子周围的格子
     * @param x x坐标
     * @param y y坐标
     * @return 返回某格子周围的格子
     */
    private Cell[] aroundCells(int x,int y){
        //计算x,y坐标周围的格子，并将结果放在ArrayList里面
        ArrayList<Cell> array = new ArrayList<>();
        for(int y1=-1;y1<2;y1++){
            for(int x1=-1;x1<2;x1++){
                if (x1 == 0 && y1 == 0) continue;
                int x2 = x + x1;
                int y2 = y + y1;
                if (x2 < 0 || x2 > weight-1 || y2 < 0 || y2 > height-1) continue;
                array.add(cells[y2][x2]);
            }
        }

        //将ArrayList里面的内容转移到Cell数组里
        Iterator<Cell> it = array.iterator();
        Cell result[] = new Cell[array.size()];
        int index = 0;
        while(it.hasNext()){
            result[index] = it.next();
            index++;
        }
        return result;
    }


    public void printPane(){
        for(int y = 0;y<height;y++){
            for(int x = 0;x<weight;x++){
                if(cells[y][x].isMine()){
                    System.out.print("*");
                }else {
                    System.out.print(cells[y][x].getArroundMineCount());
                }
                if(x != weight-1)System.out.print("  ");
            }
            System.out.println("");
        }
    }






}
