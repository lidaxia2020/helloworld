package com.helloworld.threadDemo;

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

        new Thread(runnableDemo).start();

        for (int i=0;i<20;i++){
            System.out.println("main : i = "+i);
        }
    }
}
