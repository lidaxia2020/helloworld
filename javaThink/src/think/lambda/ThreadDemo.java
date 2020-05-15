package think.lambda;

import java.util.Arrays;

/**
 * @Auther lidaxia
 * @Date 2020-05-15 20:22
 */
public class ThreadDemo {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("test");
            }
        }).start();

        // jdk8 lambda
        new Thread(()-> System.out.println("ok"));
    }
}
