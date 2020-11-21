package mainManager;
import entity.Constant;
import entity.NetMap;
import entity. Router;
import entity.RouterTable;

/*读取拓扑图的二维数组数据，来初始化二维数组维数个路由器线程*/
public class RoutersInit {
    /*初始化路由器数组*/
    public Router[] getInitRouters(){
        int[][] initNetMap=new NetMap().getInitInternetMap();
        Router[] routers=new Router[initNetMap.length];
        RouterTable[] routerTables=new RouterTable[initNetMap.length];
        for(int i=0;i< initNetMap.length;i++){
            routers[i]=new Router(Constant. getRouterIdBasic( )+i,Constant. getPortBasic()+i);
            routerTables[i]=new RouterTable();
            routerTables[i].setDistance(initNetMap[i]);
            int[] nextHop = new int[initNetMap.length];
            for(int j=0;j<initNetMap.length;j++) {
                if (initNetMap[i][i] == 1) {
                    nextHop[i] = Constant.getRouterIdBasic() + j;
                } else {
                    nextHop[i] = 0;
                }
            }
                routerTables[i].setNextHop(nextHop);
                routers[i].setRouterTable(routerTables[i]);
                System.out.println(routers[i]);
            }
            return routers;
        }
}

