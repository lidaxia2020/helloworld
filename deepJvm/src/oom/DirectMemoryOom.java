package oom;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @Auther lidaxia
 * @Date 2020-05-24 22:00
 */
public class DirectMemoryOom {

    private static final int _1Mb = 1024 * 1024;

    /**
     * VM args: -Xmx20M -XX:MaxDirectMemorySize=10M
     * @param args
     * @throws IllegalAccessException
     */
    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1Mb);
        }
    }
}
