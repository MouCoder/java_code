package mainManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import totalThreads.ForwardThread;
import totalThreads.ReceiveThread;
import totalThreads.SendThread;
import transportPacket.SendDataPacket;
import totalThreads.TotalPacket;
import entity.Constant;
import entity.NetMap;
import entity.Router;
import entity.RouterTable;

/*每一个路由器都有的路由器线程*/
public class RouterThread extends JFrame implements Runnable {
    /*产生全局唯一的序列化的实体ID*/
    private static final long serialVersionUID = -9094445007188117205L;
    /*每一个路由器线程一个路由器实体*/
    private Router router;
    /*每一个路由器实体一个接受路由表线程*/
    private DatagramSocket receiveSocket =null;
    /*每一个路由器实体一个接受转发的数据线程*/
    private DatagramSocket receiveDataSocket =null;
    /*每个路由器线程一个接受线程*/
    private ReceiveThread receiveThread=null;
    /*每个路由器线程一个发送线程*/
    private SendThread sendThread=null;
    /*每个路由器线程一个转发线程*/
    private ForwardThread forwardThread=null;
    /*每个路由器线程一个主窗口*/
    private JFrame routerFrame;
    /*有三个显示路由器信息的 JTextArea，一个路由表，一个路由表的更新信息，一个是发送数据的显示信息*/
    private JTextArea routerAreaLeft,routerAreaRightNorth,routerAreaRightSouth; //构造路由器窗口的组件。
    /*提示信息三个请输 (目的路由 #数据 )，该路由器路由表信息，路由转发和数据转发的信息*/
    private JLabel commandPrompt,showMsgLink,showMsgForward;
    /*选择是否挂起该路由的提示*/
    private JLabel suspended;
    /*写出要达到的目的地和要发送的数据*/
    private JTextField enterField;
    /*提交数据*/
    private JButton submit;
    /*选择是否挂起该路由*/
    private JCheckBox selectSuspend;
    /*是否挂起该路由的内置变量布尔值*/
    public boolean suspendFlag = false;
    /*显示几个 panal,用于承载所有的组件。*/
    private JPanel firstLine, secondLine,centerArea,centerAreaLeft,centerAreaRight,centerAreaRightBorderCenter;
    /*默认的构造方法*/
    public RouterThread() {
        super();
    }
    /*进行本路由器注入的构造方法*/
    public RouterThread(Router router) {
        try {
            this.router = router;
            routerFrame = new JFrame("路由器算法模拟实现");
            commandPrompt = new JLabel("请输入(目的路由，数据 ):");
            suspended = new JLabel("选择是否使该路由发生挂起: ");
            enterField = new JTextField("输入样例2003，11",100);
            submit=new JButton("确认");
            submit.setSize(30,15);
            selectSuspend = new JCheckBox("开始挂起...");
            firstLine = new JPanel();
            secondLine = new JPanel();
            centerArea = new JPanel();
            centerAreaLeft = new JPanel();
            centerAreaRight = new JPanel();
            centerAreaRightBorderCenter = new JPanel();
            firstLine.setLayout(new GridLayout(1, 2)); secondLine.setLayout(new GridLayout(1, 2));
            centerArea.setLayout(new GridLayout(1, 2)); centerAreaLeft.setLayout(new BorderLayout());
            centerAreaRight.setLayout(new BorderLayout());
            centerAreaRightBorderCenter.setLayout(new GridLayout(2,1));
            firstLine.add(commandPrompt);
            firstLine.add(enterField);
            firstLine.add(submit);
            secondLine.add(suspended);
            secondLine.add(selectSuspend);
            enterField.setCaretPosition(enterField.getText().length());
            showMsgLink = new JLabel("以下是该路由器路由表信息 : ");
            showMsgLink.setOpaque(true);
            showMsgLink.setBackground(Color.red);
            showMsgForward = new JLabel("以下是路由转发和数据转发的信息 :");
            showMsgForward.setOpaque(true);
            showMsgForward.setBackground(Color.red);
            routerAreaLeft=new JTextArea();
            routerAreaRightNorth=new JTextArea();
            routerAreaRightSouth=new JTextArea();
            routerAreaLeft.setBackground(Color.white);
            routerAreaRightNorth.setBackground(Color.black);
            routerAreaRightSouth.setBackground(Color.white);
            JScrollPane scrollPaneLeft=new JScrollPane(routerAreaLeft);
            JScrollPane scrollPaneRightNorth=new JScrollPane(routerAreaRightNorth);
            JScrollPane scrollPaneRightSouth=new JScrollPane(routerAreaRightSouth);
            centerAreaLeft.add(showMsgLink,BorderLayout.NORTH); centerAreaLeft.add(scrollPaneLeft,BorderLayout.CENTER);
            centerAreaRight.add(showMsgForward,BorderLayout.NORTH);
            centerAreaRightBorderCenter.add(scrollPaneRightNorth);
            centerAreaRightBorderCenter.add(scrollPaneRightSouth);
            centerAreaRight.add(centerAreaRightBorderCenter,BorderLayout.CENTER);
            centerArea.add(centerAreaLeft);
            centerArea.add(centerAreaRight);
            routerFrame.add(firstLine, BorderLayout.NORTH);
            routerFrame.add(centerArea,BorderLayout.CENTER);
            routerFrame.add(secondLine, BorderLayout.SOUTH);
            routerFrame.setVisible(true);
            routerFrame.setBounds(200, 200, 420, 300);
            showTable(routerAreaLeft);
            receiveSocket = new DatagramSocket(router.getPort());
            receiveDataSocket = new DatagramSocket(router.getPort()+10);
            sendThread=new SendThread(receiveSocket,router);
            forwardThread=new ForwardThread(routerAreaLeft,routerAreaRightNorth,routerAreaRightSouth,receiveDataSocket,router);
            receiveThread=new ReceiveThread(routerAreaLeft,routerAreaRightNorth,routerAreaRightSouth,receiveSocket,router);
            routerFrame.addWindowListener(new WindowAdapter(){ public void windowClosing(WindowEvent e) {
                routerFrame.dispose();
            }
            });
            selectSuspend.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent event){
                suspendFlag = !suspendFlag;
                if (suspendFlag) {
                    sendThread.suspend();
                    receiveThread.suspend();
                    forwardThread.suspend();
                }else{
                    sendThread.resume();
                    receiveThread.resume();
                    forwardThread.resume();
                    routerAreaLeft.setText("");
                    showTable(routerAreaLeft);
                }
            }
            });
       submit.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent event){
                String sendMsgTotal=enterField.getText();
                int destRouterId=0;
                byte [] msgBytes=null;
                try {
                    String [] sendMsgs=sendMsgTotal.split(",");
                    destRouterId=Integer.parseInt(sendMsgs[0]);
                    msgBytes=sendMsgs[1].getBytes();
                    routerAreaRightSouth.setText("");
                    sendData(destRouterId,msgBytes);
                    enterField.setText("如2003&AAA");
                } catch (NumberFormatException e) { routerAreaRightSouth.setText(""); routerAreaRightSouth.append("输入格式错误，请重新输入 ....");
                }
            }
            });
        } catch (HeadlessException e) { e.printStackTrace();
        } catch (SocketException e) { e.printStackTrace();
        }
    }
    /*继承 Thread 函数的 run 方法，进行开启每个路由器的两个监听线程（转发数据和接收路由表），一个发送线程*/
    @Override
    public synchronized void run() {
        forwardThread.start();
        receiveThread.start();
        sendThread.start();
    }
    /*展示本路由表信息的方法*/
    public synchronized void showTable(JTextArea routerAreaLeft){
        RouterTable routerTable=router.getRouterTable();
        String distances=Arrays.toString(routerTable.getDistance());
        String nextHops=Arrays.toString(routerTable.getNextHop());
        routerAreaLeft.setText("");
        routerAreaLeft.append("目的路由：\n[2001,2002,2003,2004,2005,2006]\n");
        routerAreaLeft.append("下一跳是：\n"+nextHops+"\n");
        routerAreaLeft.append("距离是：\n"+distances+"\n");
    }
    /*点击发送按钮，来向  destRouterId发送 msgBytes 数据。*/
    public synchronized boolean sendData(int destRouterId,byte [] msgBytes){ boolean sendDataSuccess=false;
        DatagramSocket sendSocket = null;
        ByteArrayOutputStream bos = null;
        ObjectOutputStream oos = null;
        int severReceivePort=0;
        DatagramPacket datagramPacket = null;
        byte[] bufferOut = new byte[1024 * 1024];
        try {
            sendSocket=new DatagramSocket();
            TotalPacket totalPacket=new TotalPacket("sendData");
            SendDataPacket sendDataPacket=new SendDataPacket();
            sendDataPacket.setSourceRouterId(router.getRouterId());
            sendDataPacket.setDestRouterId(destRouterId);
            sendDataPacket.setHops(16);
            int forwardNextHopRouterId=0;
            for(int i=0;i<new NetMap().getInitInternetMap().length;i++){
                if(i==(destRouterId-Constant.getRouterIdBasic())){
                forwardNextHopRouterId=router.getRouterTable().getNextHop()[i];
                routerAreaRightSouth.append("我 最 终 的 目 的 是 "+destRouterId+"\n");
                routerAreaRightSouth.append("我 要 转 发 这 个 数 据 包 给 "+forwardNextHopRouterId+"\n");
                routerAreaRightSouth.append("要 传 送 的 数 据 是 :"+new String(msgBytes)+"\n");
                sendDataPacket.setData(msgBytes);
                totalPacket.setSendDataPacket(sendDataPacket);
                InetAddress serverInetAddress =InetAddress.getByName("localhost");
                bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(totalPacket);
                bufferOut = bos.toByteArray();
                severReceivePort=forwardNextHopRouterId-Constant.getRouterIdBasic()+Constant.getPortBasic()+10;
                datagramPacket = new DatagramPacket(bufferOut, bufferOut.length,serverInetAddress, severReceivePort);
                sendSocket.send(datagramPacket);
                routerAreaRightSouth.append("转发完毕 \n");
                break;
            }
            }
            sendSocket.close();
            sendDataSuccess=true;
        } catch (UnknownHostException e) {
                    e.printStackTrace();
        } catch (IOException e) { e.printStackTrace();
        }finally{
            sendSocket.close();
        }
        return sendDataSuccess;
    }
}


