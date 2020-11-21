package transportPacket;

import java.io.Serializable;
import java.util.Arrays;

/*传送的数据包实体类*/
public class SendDataPacket implements Serializable{
    /*产生全局唯一的序列化的实体ID*/
    private static final long serialVersionUID = -1604118947076684542L;
    /*要发送数据的本路由器  ID*/
    private int sourceRouterId;
    /*要接受该数据的最终路由器   ID*/
    private int destRouterId;
    /*要发送的数据*/
    private byte[] data;
    /*默认最大条数，即是  TTL=16*/
    private int hops=16;
    public int getSourceRouterId() {
        return sourceRouterId;
    }
    /*getters 和 setters方法*/
    public void setSourceRouterId(int sourceRouterId) {
        this.sourceRouterId = sourceRouterId;
    }
    public int getDestRouterId() {
        return destRouterId;
    }
    public void setDestRouterId(int destRouterId) {
        this.destRouterId = destRouterId;
    }
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    public int getHops() {
        return hops;
    }
    public void setHops(int hops) {
        this.hops = hops;
    }
    /*默认的无参的构造函数*/
    public SendDataPacket() {
        super();
    }
    /*自己构造的有参的构造函数*/
    public SendDataPacket(int sourceRouterId, int destRouterId, byte[] data, int hops) {
        super();
        this.sourceRouterId = sourceRouterId;
        this.destRouterId = destRouterId;
        this.data = data;
        this.hops = hops;
    }
    /*默认的 toString 方法*/
    @Override
    public String toString() {
        return "DataPacket [sourceRouterId=" + sourceRouterId +", destRouterId=" + destRouterId + ", data=" +Arrays.toString(data) + ", hops=" + hops + "]";
    }
}



