package totalThreads;

import java.io.Serializable;
import transportPacket.SendDataPacket;
import transportPacket.RouterTablePacket;
/*最后的包装包实体类，不但可以传输数据包，还可以传送路由表 */
public class TotalPacket implements Serializable{
    /*产生全局唯一的序列化的实体ID*/
    private static final long serialVersionUID = 7430514869915215965L;
    /*要发送的是路由表还是发送的是数据的标记*/
    private String type;
    /*如果发送的是路由表，那么此属性   routerTablePacket必须要实例化。*/
    private RouterTablePacket routerTablePacket;
    /*如果发送的是数据，那么此属性   sendDataPacket必须要实例化。*/
    private SendDataPacket sendDataPacket;
    /*getters 和 setters方法*/
    public String getType() {
        return type;
    }
    /*getters 和 setters方法*/
    public void setType(String type) {
        this.type = type;
    }
    public RouterTablePacket getRouterTablePacket() {
        return routerTablePacket;
    }
    public void setRouterTablePacket(RouterTablePacket routerTablePacket) {
        this.routerTablePacket = routerTablePacket;
    }

    public SendDataPacket getSendDataPacket() {
        return sendDataPacket;
    }

    public void setSendDataPacket(SendDataPacket sendDataPacket) {
        this.sendDataPacket = sendDataPacket;
    }
    public TotalPacket() {
        super();
    }
    public TotalPacket(String type) {
        super();
        this.type = type;
    }
    /*自己创建有参  type 和 routerTablePacket的构造函数*/
    public TotalPacket(String type, RouterTablePacket routerTablePacket) { super();
        this.type = type;
        this.routerTablePacket = routerTablePacket;
    }

    /*自己创建有参type 的构造函数*/
    public TotalPacket(String type, SendDataPacket sendDataPacket) { super();
        this.type = type;
        this.sendDataPacket = sendDataPacket;
    }
    /*默认的 tostring 方法*/
    @Override
    public String toString() {
        return "TotalPacket [type=" + type + ", routerTablePacket=" + routerTablePacket + ", sendDataPacket=" + sendDataPacket +"]";
    }
}