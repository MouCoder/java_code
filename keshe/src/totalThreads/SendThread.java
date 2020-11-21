package totalThreads;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import transportPacket.RouterTablePacket;
import totalThreads.TotalPacket;
import entity.Constant;
import entity.NetMap;
import entity.Router;
/*每一个路由器线程都有的发送线程*/
public class SendThread extends Thread {
    /*本路由器的路由表，里面还有   RouterTable属性，即是本路由器路由表的值。*/
    private Router router;
    /*用于发送路由表的  socket*/
    private DatagramSocket receiveSocket;
    /*SendThread 的默认构造函数*/
    public SendThread() {
        super();
    }
    /*含有参数的构造函数*/
    public SendThread(DatagramSocket receiveSocket,Router router) { super();
        this.receiveSocket=receiveSocket;
        this.router = router;
    }
    /*开启发送线程，每隔  1 秒钟就发送本路由表中的信息*/
    public synchronized void run() {
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        int severReceivePort=0;
        DatagramPacket datagramPacket = null;
        byte[] bufferOut = new byte[1024 * 1024];
        while(true) {
            try {
                DatagramSocket sendSocket=new DatagramSocket();
                InetAddress serverInetAddress =InetAddress.getByName("localhost");
                TotalPacket totalPacket=new TotalPacket("sendTable");
                RouterTablePacket routerTablePacket=new RouterTablePacket();
                routerTablePacket.setSourceRouterId(router.getRouterId());
                routerTablePacket.setRouterTable(router.getRouterTable());
                totalPacket.setRouterTablePacket(routerTablePacket);
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(totalPacket);
                bufferOut = bos.toByteArray();
                for(int i=0;i<new NetMap().getInitInternetMap().length;i++){
                    if(1==router.getRouterTable().getDistance()[i]){
                    severReceivePort=Constant.getPortBasic()+i;
                    if(severReceivePort!=router.getPort()){
                        datagramPacket = new DatagramPacket(bufferOut,
                                bufferOut.length,serverInetAddress, severReceivePort); sendSocket.send(datagramPacket);
                    }
                }
                }
                this.sleep(1000);
            } catch (Exception e) { e.printStackTrace();
            }finally{
                try {
                    bos.close();
                    oos.close();
                } catch (IOException e) { e.printStackTrace();
                }
            }
        }
    }
}


