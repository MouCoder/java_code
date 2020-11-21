package transportPacket;

import java.io.Serializable;
import entity.RouterTable;
/*传送的路由器的路由表的实体类*/
public class RouterTablePacket implements Serializable{
    /*产生全局唯一的序列化的实体   ID*/
    private static final long serialVersionUID = -3178322080563528386L;
    /*要发送路由表的本路由器  ID*/
    private int sourceRouterId;
    /*要发送路由表的本路由器的路由表*/
    private RouterTable routerTable;
    /*getters 和 setters方法*/
    public int getSourceRouterId() {
        return sourceRouterId;
    }
    public void setSourceRouterId(int sourceRouterId) {
        this.sourceRouterId = sourceRouterId;
    }
    public RouterTable getRouterTable() {
        return routerTable;
    }
    public void setRouterTable(RouterTable routerTable) {
        this.routerTable = routerTable;
    }

    public RouterTablePacket() {
        super();
    }
    /*含有参数的构造函数*/

    public RouterTablePacket(int sourceRouterId, RouterTable routerTable) { super();
        this.sourceRouterId = sourceRouterId;
        this.routerTable = routerTable;
    }
    /*默认的 toString 说明该实体类内容*/
    @Override
    public String toString() {
        return "RouterTablePacket [routerTable=" + routerTable + ", sourceRouterId=" + sourceRouterId + "]";
    }
}














