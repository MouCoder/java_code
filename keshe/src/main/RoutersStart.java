package main;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import mainManager.RouterThread;
import mainManager.RoutersInit;
import entity.NetMap;
import entity.Router;

/**主进入界面，进入程序的主控制接口*/
public class RoutersStart extends JFrame{
    /*产生全局唯一的序列化的实体   ID*/
    private static final long serialVersionUID = -4212024082944256452L;
    /*建立一个全局的  JTextArea,用来关闭时窗口是， 关闭整个进程而不是线程 */
    private JTextArea router_MainArea;
    /*初始化多少个路由器*/
    public RoutersStart()
    {
        super("router_MainProcess");
        router_MainArea=new JTextArea();
        getContentPane().add(router_MainArea,BorderLayout.CENTER);
        router_MainArea.append("\n\n\n开始模拟"+new NetMap().getInitInternetMap().length+" 个路由器的距离向量路由算法	...");
        setBounds(150,150,500,400);
        router_MainArea.setBackground(Color.BLACK);
        setBackground(Color.green);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int length=new NetMap().getInitInternetMap().length;
        Router[] routers=new RoutersInit().getInitRouters();
        for(int i=0;i<length;i++){
            new Thread(new RouterThread(routers[i])).start();
        }
    }
    /*主程序的入口*/
    public static void main(String[] args) {
        new RoutersStart();
    }
}