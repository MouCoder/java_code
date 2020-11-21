package entity;

/*初始化的网络拓扑图*/
public class NetMap {
    /*返回初始化的网络图*/
    public int[][] getInitInternetMap(){
        int[][] initVector = new int[][]
                {{0,1,16,16,1,16},
                {1,0,1,16,16,16},
                {16,1,0,1,1,16},
                {16,16,1,0,16,1},
                {1,16,1,16,0,16},
                {16,16,16,1,16,0}};
        return initVector;
    }
}
