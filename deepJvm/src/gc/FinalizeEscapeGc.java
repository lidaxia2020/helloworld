package gc;

import sun.security.krb5.internal.TGSRep;

import java.util.Arrays;

/**
 *  2点：
 *      1、对象可以在gc时自救
 *      2、这种自救的机会就只会有一次，因为一个对象的finalize()方法最多只调用一次
 * @Auther lidaxia
 * @Date 2020-06-02 19:34
 */
public class FinalizeEscapeGc {

    public static FinalizeEscapeGc SAVE_HOOK = null;

    public void isAlive(){
        System.out.println("yes , i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGc.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeEscapeGc();

        // 第一次自救
        SAVE_HOOK = null;
        System.gc();
        // 因为finalize方法优先级很低，所以等待0.5s
        Thread.sleep(500);
        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i am dead!");
        }


        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);
        if (SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("no, i am dead!");
        }
    }
}
