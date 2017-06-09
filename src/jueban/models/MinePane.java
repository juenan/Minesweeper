package jueban.models;

import javax.swing.*;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * 扫雷面板
 * Created by jueban on 2017/6/7.
 */
public class MinePane {
    final int MAX_WEIGHT = 100;//最大宽度
    final int MAX_HEIGHT = 100;//最大长度
    private boolean is_first_sweep = true;

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
            Point point = getNextCell(x1,y1);
            x1 = (int)point.getX();
            y1 = (int)point.getY();
        }//for

        //计算非雷格子周围雷数
        flushCellsMineCount();
    }

    /**
     * 第一次扫雷
     * @param x x轴
     * @param y y轴
     */
    private void firstSweep(int x,int y){
        is_first_sweep = false;
        //如果第一次点击的位置是雷则转移
        if(cells[y][x].isMine()){
            int x1 = (int)(Math.random()*weight) % weight;
            int y1 = (int)(Math.random()*height) % height;
            while(cells[y1][x1].isMine()){
                Point point = getNextCell(x1,y1);
                x1 = (int)point.getX();
                y1 = (int)point.getY();
            }


            Cell temp = cells[y][x];
            cells[y][x] = cells[y1][x1];
            cells[y1][x1] = temp;
        }

        //把第一次点击的格子周围的雷都转移
        Cell around_cells[] = aroundCells(x,y);
        for(int i = 0;i<around_cells.length;i++){
            if(around_cells[i].isMine()){
                int x1 = (int)(Math.random()*weight) % weight;
                int y1 = (int)(Math.random()*height) % height;
                boolean is_around_cell = false;
                do{
                    is_around_cell = false;
                    Point point = getNextCell(x1,y1);
                    x1 = (int)point.getX();
                    y1 = (int)point.getY();
                    for(int j = 0;j<around_cells.length;j++){
                        if(cells[y1][x1] == around_cells[j])is_around_cell = true;
                    }
                    if(cells[y1][x1] == cells[y][x])is_around_cell = true;
                }while(cells[y1][x1].isMine() || is_around_cell);
                Point point = getCellPoint(around_cells[i]);
                int x2 = (int)point.getX();
                int y2 = (int)point.getY();
                Cell temp = cells[y2][x2];
                cells[y2][x2] = cells[y1][x1];
                cells[y1][x1] = temp;
            }


        }

        //重新计算整个扫雷面板的周围雷数目
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
        if(is_first_sweep){
            firstSweep(x,y);
            return false;
        }

        if(!cells[y][x].isMine() && cells[y][x].getArroundMineCount() == 0 && cells[y][x].isCover()){

            cells[y][x].setCover(false);

            for(int y1=-1;y1<2;y1++){
                for(int x1=-1;x1<2;x1++){
                    if(y1 == 0 && x1 == 0)continue;
                    int x2 = x+x1;
                    int y2 = y+y1;
                    if (x2 < 0 || x2 > weight-1 || y2 < 0 || y2 > height-1) continue;
                    if(cells[y2][x2].isCover() && !cells[y2][x2].isFlag() && !cells[y2][x2].isDoubt())sweep(x2,y2);
                }
            }
        }


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
        if(cells[y][x].isCover())return sweep(x,y);
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
                    Point p = getCellPoint(arount_cells[i]);
                    sweep((int)p.getX(),(int)p.getY());
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


    /**
     * 获取一个格子的坐标
     * @param cell
     * @return 坐标
     */
    private Point getCellPoint(Cell cell){
        Point point = new Point();
        for(int y = 0; y < height;y++){
            for(int x = 0;x<weight;x++){
                if(cells[y][x]==cell){
                    point.setLocation(x,y);
                }
            }
        }
        return point;
    }

    /**
     * 返回一个坐标的下一个坐标
     * @param x
     * @param y
     * @return 下一个坐标
     */
    private Point getNextCell(int x,int y){
        int x1 = x;
        int y1 = y;
        if(x1<(weight-1)){
            x1++;
        }else if(x1==(weight-1) && y1<(height-1)){
            x1=0;
            y1++;
        }else {
            y1=0;
            x1=0;
        }

        Point point = new Point(x1,y1);
        return point;
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


    public void paintPane(int weight,int height,Graphics g){
        int weight_pixel = weight/this.weight;
        int height_pixel = height/this.height;
        Image cover = new ImageIcon("D:\\ideaspace\\Minesweeper\\src\\jueban\\models\\img\\Cover.png").getImage();
        cover = new ImageIcon(cover.getScaledInstance(weight_pixel,height_pixel,Image.SCALE_DEFAULT)).getImage();
        Image flag = new ImageIcon("D:\\ideaspace\\Minesweeper\\src\\jueban\\models\\img\\Flag.png").getImage();
        flag = new ImageIcon(flag.getScaledInstance(weight_pixel,height_pixel,Image.SCALE_DEFAULT)).getImage();
        Image doubt = new ImageIcon("D:\\ideaspace\\Minesweeper\\src\\jueban\\models\\img\\Doubt.png").getImage();
        doubt = new ImageIcon(doubt.getScaledInstance(weight_pixel,height_pixel,Image.SCALE_DEFAULT)).getImage();
        Image num = new ImageIcon("D:\\ideaspace\\Minesweeper\\src\\jueban\\models\\img\\Num.png").getImage();
        num = new ImageIcon(num.getScaledInstance(weight_pixel,height_pixel,Image.SCALE_DEFAULT)).getImage();
        for(int y = 0;y<this.height;y++){

            for(int x = 0;x<this.weight;x++){



                if(cells[y][x].isCover()){
                    g.drawImage(cover, x*weight_pixel,y*height_pixel, null);
                    if(cells[y][x].isFlag()){
                        g.drawImage(flag,x*weight_pixel,y*height_pixel, null);
                    }
                    if(cells[y][x].isDoubt()){
                        g.drawImage(doubt,x*weight_pixel,y*height_pixel, null);
                    }
                }else {
                    g.drawImage(num,x*weight_pixel,y*height_pixel, null);
                    if(cells[y][x].isMine()){
                        g.drawString("*",x*weight_pixel+10,y*height_pixel+15);
                    }else {
                        g.drawString(cells[y][x].getArroundMineCount()+"",x*weight_pixel+10,y*height_pixel+15);
                    }

                }


                /*
                if(cells[y][x].isMine()){

                    g.drawString("*",x*weight_pixel,y*height_pixel);
                }else {
                    g.drawString(cells[y][x].getArroundMineCount()+"",x*weight_pixel,y*height_pixel);
                }*/
        }
        }

    }






}
