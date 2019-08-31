package com.helloworld.threadDemo;



/**
 * 匿名内部类创建线程
 * @author daxia li
 * @time 2019/7/20
 */
public class ThreadDmo {

    public static void main(String[]args){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    System.out.println("run : i = "+i);
                }
            }
        });

        thread.start();

        for (int i=0;i<20;i++){
            System.out.println("main : i = "+i);
        }
    }
}
