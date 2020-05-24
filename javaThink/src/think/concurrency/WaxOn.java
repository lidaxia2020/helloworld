package think.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @Auther lidaxia
 * @Date 2020-05-23 15:49
 */
public class WaxOn implements Runnable{

    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                System.out.println("Wax on");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxed();
                car.waitForBuffing();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("end Wax on task");
    }
}
