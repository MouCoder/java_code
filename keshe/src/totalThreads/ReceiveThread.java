package totalThreads;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.HashMap;
import javax.swing.JTextArea;
import transportPacket.RouterTablePacket;
import totalThreads.TotalPacket;
import entity.Constant;
import entity.Router;
import entity.RouterTable;
import entity.TimeCounter;

/*每一个路由器线程都有的接收线程*/
public class ReceiveThread extends Thread{
    /*注入的可以修改左边区域显示的	routerArea*/
    private JTextArea routerAreaLeft;
    /*注入的可以修改右上边区域显示的   routerArea*/
    private JTextArea routerAreaRightNorth;
    /*注入的可以修改右下边区域显示的   routerArea*/
    private JTextArea routerAreaRightSouth;
    /*注入的可以更新的本路由器的路由表*/
    private Router router;
    /*接收路由表的  socket*/
    private DatagramSocket receiveSocket;
    /*ReceiveThread 的默认构造函数*/
    public ReceiveThread() {
        super();
    }
/*含有参数初始化的构造函数*/
    public	ReceiveThread(JTextArea	routerAreaLeft,JTextArea
            routerAreaRightNorth,JTextArea routerAreaRightSouth,DatagramSocket receiveSocket,Router router) {
        super();
        this.routerAreaLeft = routerAreaLeft;
        this.routerAreaRightNorth = routerAreaRightNorth;
        this.routerAreaRightSouth = routerAreaRightSouth;
        this.receiveSocket = receiveSocket;
        this.router = router;
    }
    /*默认的开启该接受线程，始终接受收到的邻居路由表的信息*/
    public synchronized void run() {
        DatagramPacket dataPacket =null;
        ByteArrayInputStream bis =null;
        ObjectInputStream ois =null;
        byte[] bufferIn = new byte[1024 * 1024];
        while(true){
            try {
                dataPacket = new DatagramPacket(bufferIn, bufferIn.length); receiveSocket.receive(dataPacket);
                bis = new ByteArrayInputStream(dataPacket.getData()); ois = new ObjectInputStream(bis);
                TotalPacket totalPacket = (TotalPacket) ois.readObject();
                RouterTablePacket routerTablePacket=null;
                if("sendTable".equals(totalPacket.getType())){
                    routerTablePacket=totalPacket.getRouterTablePacket();
                    int sourceRouterId=routerTablePacket.getSourceRouterId();
                    routerAreaRightNorth.append("收 到 了 "+sourceRouterId+" 的路由表 \n");
                    routerAreaRightNorth.setCaretPosition(routerAreaRightNorth.getText().length());
                    RouterTable tableTemp =routerTablePacket.getRouterTable();
                    TimeCounter timeCounter=null;
                    HashMap<Integer,TimeCounter> createTimerMapsForNeighbers=router.getCreateTimerMapsForNeighbers();
                    HashMap<Integer,Long> lastTimeMaps=router.getLastTimeMaps();
                    long timeNow=System.currentTimeMillis();
                    if(lastTimeMaps.containsKey(sourceRouterId)){
                        lastTimeMaps.remove(sourceRouterId);
                        lastTimeMaps.put(sourceRouterId,timeNow);
                    }else{
                        lastTimeMaps.put(sourceRouterId,timeNow);
                    }
                    if(createTimerMapsForNeighbers.containsKey(sourceRouterId)){
                        timeCounter=createTimerMapsForNeighbers.get(sourceRouterId);
                        timeCounter.close();
                        createTimerMapsForNeighbers.remove(sourceRouterId);
                        timeCounter=new TimeCounter(sourceRouterId,router);
                        createTimerMapsForNeighbers.put(sourceRouterId, timeCounter);
                        timeCounter.start();
                    }else{
                        timeCounter=new TimeCounter(sourceRouterId,router);
                        createTimerMapsForNeighbers.put(sourceRouterId, timeCounter);
                        timeCounter.start();
                    }
                    int [] nextHopsNew=tableTemp.getNextHop();
                    int [] nextHopsOld=router.getRouterTable().getNextHop();
                    int [] dissNew=tableTemp.getDistance();
                    int [] dissOld=router.getRouterTable().getDistance(); //DV_ 算法的精髓部分。
                    for(int i=0;i<dissNew.length;i++){
                        if(sourceRouterId==nextHopsOld[i]){
                            nextHopsOld[i]=sourceRouterId;
                            if(dissNew[i]<16){
                                dissOld[i]=dissNew[i]+1;
                            }else{
                                dissOld[i]=dissNew[i];
                            }

                        }else{
                            if(nextHopsNew[i]==0&&dissNew[i]==0&&nextHopsNew[i]!=router.getRouterId()){
                                nextHopsOld[i]=sourceRouterId;
                                if(dissNew[i]<16){
                                    dissOld[i]=dissNew[i]+1;
                                }else{
                                    dissOld[i]=dissNew[i];
                                }
                            }else{
                                if(i!=router.getRouterId()-Constant.getRouterIdBasic()&& i!=sourceRouterId - Constant.getRouterIdBasic()&&nextHopsNew[i]!=0){
                                    if(nextHopsOld[i]==0&&nextHopsNew[i]!=router.getRouterId()){

                                        nextHopsOld[i]=sourceRouterId;

                                        if(dissNew[i]<16){
                                            dissOld[i]=dissNew[i]+1;
                                        }else{
                                            dissOld[i]=dissNew[i];
                                        }
                                    }else
                                        if(nextHopsOld[i]!=0&&nextHopsOld[i]==nextHopsNew[i]&&nextHopsNew[i]!=router.getRouterId()){
                                            nextHopsOld[i]=sourceRouterId;
                                            if(dissNew[i]<16){
                                                dissOld[i]=dissNew[i]+1;
                                            }else{
                                                dissOld[i]=dissNew[i];
                                            }
                                        }else
                                            if(nextHopsOld[i]!=0&&nextHopsOld[i]!=nextHopsNew[i]&&nextHopsNew[i]!=router.getRouterId()){
                                                if((dissNew[i]+1)<dissOld[i]){
                                                    nextHopsOld[i]=sourceRouterId;
                                                    if(dissNew[i]<16){
                                                        dissOld[i]=dissNew[i]+1;
                                                    }else{
                                                        dissOld[i]=dissNew[i];
                                                    }
                                                }
                                            }
                                }
                            }
                        }
                    }
                    updateShow(routerAreaLeft);
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

    /*当收到新的路由表时，更新显示新的路由表。*/
    public synchronized void updateShow(JTextArea routerAreaLeft){
        RouterTable routerTable=router.getRouterTable();
        String distances=Arrays.toString(routerTable.getDistance());
        String nextHops=Arrays.toString(routerTable.getNextHop());
        routerAreaLeft.setText("");
        routerAreaLeft.append("目 的路 由： \n[2001,  2002,  2003,  2004,  2005, 2006]\n");
        routerAreaLeft.append("下一跳是： \n"+nextHops+"\n");
        routerAreaLeft.append("距离是： \n"+distances+"\n");
    }
}

