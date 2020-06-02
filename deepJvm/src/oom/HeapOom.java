package oom;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther lidaxia
 * @Date 2020-05-24 20:58
 */
public class HeapOom {

    static class OoMObject{

    }

    /**
     * VM args: -Xmx20m -Xms20m -XX:+HeapDumpOnOutOfMemoryError 可以让虚拟机在内存溢出异常时dump当前的内存堆存储的快照
     * -XX:HeapDumpPath=D:\ideause\git\helloworld\deepJvm\src
     * @param args
     */
    public static void main(String[] args) {
        List<OoMObject> list = new ArrayList<>();
        while (true){
            list.add(new OoMObject());
        }
    }
}
