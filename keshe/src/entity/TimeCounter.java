package entity;

import java.util.Timer;
import java.util.TimerTask;

public class TimeCounter {
    /*得到邻居的路由器ID*/
    private int sourceRouterId;
    /*得到本身路由器*/
    private Router router;
    /*每个这个类一个计时器*/
    private Timer timer = new Timer();
    /*getters和 setters方法*/
    public int getSourceRouterId() {
        return sourceRouterId;
    }

    public void setSourceRouterId(int sourceRouterId) {
        this.sourceRouterId = sourceRouterId;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    /*默认构造方法*/

    public TimeCounter() {
    }

    public TimeCounter(int sourceRouterId, Router router) {
        super();
        this.sourceRouterId = sourceRouterId;
        this.router = router;
    }

    /*开启计时器*/
    public void start() {
        final long lastTime = router.getLastTimeMaps().get(sourceRouterId);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (lastTime == router.getLastTimeMaps().get(sourceRouterId)) {
                    for (int i = 0; i < new NetMap().getInitInternetMap().length; i++) {
                        if (router.getRouterTable().getNextHop()[i] == sourceRouterId) {
                            router.getRouterTable().getDistance()[i] = 16;
                            router.getRouterTable().getNextHop()[i] = 0;
                        }
                    }
                } else {
                    timer.cancel();
                }
            }
        }, 10 * 1000);
    }
    /*关闭计时器*/
    public void close(){
        timer.cancel();
    }

}
