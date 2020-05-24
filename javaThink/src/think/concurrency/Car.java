package think.concurrency;

/**
 * @Auther lidaxia
 * @Date 2020-05-23 15:46
 */
public class Car {

    private boolean waxOn = false;

    /**
     * 上蜡
     */
    public synchronized void waxed() {
        waxOn = true;
        notifyAll();
    }

    /**
     * 磨光
     */
    public synchronized void buffed() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn == false) {
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn == true) {
            wait();
        }
    }

}
