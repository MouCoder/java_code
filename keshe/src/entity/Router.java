package entity;
import java.util.HashMap;

/*路由器实体类,包含routerId,port,RouterTable*/
public class Router {
    /*产生全局唯一的序列化的实体ID*/
    private static final long serialVersionUID = -4112736218089137504L;
    /*路由 ID*/
    private int routerId;
    /*路由端口*/
    private int port;
    /*路由表*/
    private RouterTable RouterTable;
    /*存储计时器的Map*/
    private HashMap<Integer,TimeCounter> createTimerMapsForNeighbers=new HashMap<Integer,TimeCounter>();
    /*存储上一次该路由器收到某个路由器的路由表时间*/
    private HashMap<Integer,Long> lastTimeMaps=new HashMap<Integer,Long>();
    /*默认的构造方法*/
    public Router() {
        super();
    }
    /*有参数routerId和port的构造方法*/
    public Router(int routerId, int port) {
        super();
        this.routerId = routerId;
        this.port = port;
    }
    /*有参数 routerId 和 port 和 routerTable的构造方法*/
    public Router(int routerId, int port, RouterTable routerTable) {
        super();
        this.routerId = routerId;
        this.port = port;
        RouterTable = routerTable;
    }
    /*getters 和 setters方法*/
    public int getRouterId() {
        return routerId;
    }
    public void setRouterId(int routerId) {
        this.routerId = routerId;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public RouterTable getRouterTable() {
        return RouterTable;
    }
    public void setRouterTable(RouterTable routerTable) {
        RouterTable = routerTable;
    }
    public HashMap<Integer, TimeCounter> getCreateTimerMapsForNeighbers() {
        return createTimerMapsForNeighbers;
    }
    public void setCreateTimerMapsForNeighbers(HashMap<Integer, TimeCounter> createTimerMapsForNeighbers) {
        this.createTimerMapsForNeighbers = createTimerMapsForNeighbers;
    }
    public HashMap<Integer, Long> getLastTimeMaps() {
        return lastTimeMaps;
    }
    public void setLastTimeMaps(HashMap<Integer, Long> lastTimeMaps) {
        this.lastTimeMaps = lastTimeMaps;
    }
    /*tostring 方法*/
    @Override
    public String toString() {
        return "Router [routerId=" + routerId + ", port=" + port + ", RouterTable=" + RouterTable + "]";
    }
}



