package entity;

import java.io.Serializable;
import java.util.Arrays;

/*路由表实体类*/
public class RouterTable implements Serializable {
    /*产生全局唯一的序列化的实体*/
    private static final long serialVersionUID = 7320514855714982331L;
    /*为每个路由器设置一个下一跳的数组*/
    private int[] nextHop;
    /*为每个路由器设置一个距离的数组*/
    private int[] distance;

    /*getter和setter方法*/
    public int[] getNextHop() {
        return nextHop;
    }
    public void setNextHop(int[] nestHop) {
        this.nextHop = nestHop;
    }
    public int[] getDistance() {
        return distance;
    }
    public void setDistance(int[] distance) {
        this.distance = distance;
    }
    /*tostring方法*/
    @Override
    public String toString(){
        return "RouterTable [distance=" + Arrays.toString(distance)
                +",nextHop=" + Arrays.toString(nextHop) + "]";
    }
}

