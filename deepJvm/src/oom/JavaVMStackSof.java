package oom;

import java.util.Arrays;

/**
 * @Auther lidaxia
 * @Date 2020-05-24 21:41
 */
public class JavaVMStackSof {

    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    /**
     * VM args: -Xss128k
     * @param args
     */
    public static void main(String[] args) {
        JavaVMStackSof oom = new JavaVMStackSof();
        try {
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }

    }
}
