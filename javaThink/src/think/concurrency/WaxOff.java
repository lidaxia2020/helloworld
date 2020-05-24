package think.concurrency;

import java.util.concurrent.TimeUnit;

/**
 * @Auther lidaxia
 * @Date 2020-05-23 15:52
 */
public class WaxOff implements Runnable{

    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()){
                car.waitForWaxing();
                System.out.println("Wax off");
                TimeUnit.MILLISECONDS.sleep(200);
                car.buffed();
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("end Wax off task");
    }
}
