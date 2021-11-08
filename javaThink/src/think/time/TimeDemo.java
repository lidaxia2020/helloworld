package think.time;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

/**
 * @author lidaxia
 * @version 1.0
 * @date 2021/5/19 19:25
 */
public class TimeDemo {

    public static void main(String[] args) {
        Timer time = new Timer("time", true);
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("test");
            }
        }, 1000 * 3, 1000);

        ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(1, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return null;
            }
        });

        while (true){

        }
    }
}
