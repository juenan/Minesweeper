package jueban.models;

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
        else this.mine_count = this.height*this.weight;

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
        cells[y][x].setCover(false);
        flushCellsMineCount();


    }

    /**
     * 计算格子周围雷的数量
     * @param x 格子的x轴
     * @param y 格子的y轴
     * @return 格子周围雷的数量
     */
    private int computMineCount(int x,int y){
        int mine_count = 0;
        for(int y1 = -1;y1<2;y1++) {
            for (int x1 = -1; x1 < 2; x1++) {
                if (x1 == 0 && y1 == 0) continue;
                int x2 = x + x1;
                int y2 = y + y1;
                if (x2 < 0 || x2 > weight-1 || y2 < 0 || y2 > height-1) continue;
                if (cells[y2][x2].isMine()) mine_count++;
            }//for
        }//for
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
