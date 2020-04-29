package com.helloworld.threaddemo;

import java.util.concurrent.*;

/**
 * @author daxia li
 * @time 2019/7/20
 */
public class RunnableDemo implements Runnable{
    @Override
    public void run() {
        for (int i=0;i<20;i++){
            System.out.println("run : i = "+i);
        }
    }

    public static void main(String[]args){
        RunnableDemo runnableDemo = new RunnableDemo();
        ThreadPoolExecutor t = new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());
        t.execute(runnableDemo);

        for (int i=0;i<20;i++){
            System.out.println("main : i = "+i);
        }
    }
}
