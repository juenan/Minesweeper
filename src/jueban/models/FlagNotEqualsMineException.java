package jueban.models;

/**
 * Created by jueban on 2017/6/8.
 */
public class FlagNotEqualsMineException extends Exception{


    public String toString(){
        return "旗子数量不等于该格子周围地雷数量";
    }
}
