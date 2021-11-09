package think.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author daxia li
 * @version 1.0
 * @date 2021/5/20 9:13
 */
public class AtomicDemo {

    static AtomicInteger atomicInteger = new AtomicInteger(0);
    AtomicReference atomicReference = new AtomicReference();

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch latch = new CountDownLatch(2);

        Thread thread1 = new Thread(new Runnable(){

            @Override
            public void run() {
                for ( int i=0;i<10;i++){
                    System.out.println( "thread1  " +atomicInteger.getAndIncrement());
                }
                latch.countDown();
            }
        });
        thread1.start();

        Thread thread2 = new Thread(new Runnable(){

            @Override
            public void run() {
                for ( int i=0;i<10;i++){
                    System.out.println( "thread2  " +atomicInteger.getAndIncrement());
                }
                latch.countDown();
            }
        });
        thread2.start();

        latch.await();

        SimpleObject test = new SimpleObject("test3" , 30);
        AtomicReference<SimpleObject> atomicReference2 = new AtomicReference<>(test);
        Boolean bool = atomicReference2.compareAndSet(test, new SimpleObject("test4", 40));
        System.out.println("simpleObject  Value: " + bool);


    }


    static class SimpleObject{

        public SimpleObject(String name, int age) {
            this.name = name;
            this.age = age;
        }

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
