package totalThreads;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JTextArea;
import transportPacket.SendDataPacket;
import totalThreads.TotalPacket;
import entity.Constant;
import entity.NetMap;
import entity.Router;


/*每一个路由器线程都有的转发线程*/
public class ForwardThread extends Thread {
    /*注入的可以修改左边区域显示的   routerArea*/
    private JTextArea routerAreaLeft;
    /*注入的可以修改右上边区域显示的   routerArea*/
    private JTextArea routerAreaRightNorth;
    /*注入的可以修改右下边区域显示的   routerArea*/
    private JTextArea routerAreaRightSouth;
    /*注入的可以更新的本路由器的路由表*/
    private Router router;
    /*接收路由表的  socket*/
    private DatagramSocket receiveDataSocket;
    /*默认的构造方法*/
    public ForwardThread() {
        super();
    }
    /*含有参数初始化的构造函数*/
    public	ForwardThread(JTextArea	routerAreaLeft,JTextArea
            routerAreaRightNorth,JTextArea routerAreaRightSouth,DatagramSocket receiveDataSocket,Router router) {
        super();
        this.routerAreaLeft = routerAreaLeft;
        this.routerAreaRightNorth = routerAreaRightNorth;
        this.routerAreaRightSouth = routerAreaRightSouth;
        this.receiveDataSocket = receiveDataSocket;this.router = router;

    }
    /*转发进程的开启，始终监听收到的数据*/
    @Override
    public synchronized void run() {
        DatagramPacket dataPacket =null;
        ByteArrayInputStream bis =null;
        ObjectInputStream ois =null;
        byte[] bufferIn = new byte[1024 * 1024];
        while(true){
            try {
                dataPacket = new DatagramPacket(bufferIn, bufferIn.length); receiveDataSocket.receive(dataPacket);
                bis = new ByteArrayInputStream(dataPacket.getData()); ois = new ObjectInputStream(bis);
                TotalPacket totalPacket = (TotalPacket) ois.readObject();
                routerAreaRightSouth.setText(""); if("sendData".equals(totalPacket.getType())){
                    SendDataPacket sendDataPacket=totalPacket.getSendDataPacket();
                    int destRouterId=sendDataPacket.getDestRouterId();
                    int sourceRouterId=sendDataPacket.getSourceRouterId();
                    byte [] data=sendDataPacket.getData();
                    int remainHops=sendDataPacket.getHops()-1;
                    String datas=new String(data);
                    routerAreaRightSouth.append("收 到 了 "+sourceRouterId+" 传 向 "+destRouterId+"的数据 \n");
                    routerAreaRightSouth.append("数据是 :"+datas+"\n");
                    if(destRouterId == router.getRouterId()){
                        routerAreaRightSouth.append("我就是目的地。 \n");
                        }else{
                        if(sendDataPacket.getHops()==1){
                            routerAreaRightSouth.append("无法到达跳数已经 16 了， \n 我要抛弃该包了。 \n");
                        }else{
                            totalPacket.getSendDataPacket().setHops(remainHops);
                            totalPacket.getSendDataPacket().setSourceRouterId(router.getRouterId());
                            for(int i=0;i<new NetMap().getInitInternetMap().length;i++){
                                if(i==(destRouterId-Constant.getRouterIdBasic())){
                                    if((router.getRouterTable().getDistance()[i]!=16)&&(router.getRouterTable().getNextHop()[i]!=0)){
                                        int forwardNextHopRouterId=router.getRouterTable().getNextHop()[i];
                                        routerAreaRightSouth.append("我要转发这个数据包给 "+forwardNextHopRouterId+"\n");
                                        forward(forwardNextHopRouterId,totalPacket);
                                        routerAreaRightSouth.append(" 转 发 完 毕。\n");
                                    }else
                                        if(router.getRouterTable().getNextHop()[i]==0){
                                            routerAreaRightSouth.append("这个路由我不可达， \n 我要丢弃该包。 \n");
                                            break;
                                        }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) { e.printStackTrace();
            }finally{
                try {
                    bis.close();
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /*此方法用于当收到的数据的目的地址不是我自己是，我就将数据转发给我的下一跳。*/
    public synchronized void forward(int forwardNextHopRouterId,TotalPacket totalPacketForward){
        DatagramSocket sendSocket=null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        DatagramPacket datagramPacket = null;
        byte[] bufferOut = new byte[1024 * 1024];
        try {
            sendSocket=new DatagramSocket();
            InetAddress serverInetAddress =InetAddress.getByName("localhost");
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(totalPacketForward);
            bufferOut = bos.toByteArray();
            int severReceivePort=forwardNextHopRouterId-Constant.getRouterIdBasic()+Constant.getPortBasic()+10;
            datagramPacket = new DatagramPacket(bufferOut, bufferOut.length,serverInetAddress, severReceivePort);
            sendSocket.send(datagramPacket);
        } catch (SocketException e) { e.printStackTrace();
        } catch (UnknownHostException e) { e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{ sendSocket.close();
        }
    }
}
