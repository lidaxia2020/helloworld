package com.helloworld.threaddemo;

/**
 * @author daxia li
 * @time 2019/7/20
 */
public class ThreadDemo extends Thread{


    /**
     *  线程需要执行的任务
     */
    @Override
    public void run() {
        for (int i=0;i<20;i++){
            System.out.println("run : i = "+i);
        }
    }

    public static void main(String[]args){
        ThreadDemo threadDemo = new ThreadDemo();

        threadDemo.start();

        for (int i=0;i<20;i++){
            System.out.println("main : i = "+i);
        }
    }
}
