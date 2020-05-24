package oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther lidaxia
 * @Date 2020-05-24 21:46
 */
public class RunTimeConstantPoolOom1 {

    /**
     * VmM args: -XX:PermSize=10M -XX:MaxPermSize=10M
     * @param args
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        int i = 0;
        while (true){
            list.add(String.valueOf(i++).intern());
        }
    }
}
