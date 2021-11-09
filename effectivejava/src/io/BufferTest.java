package io;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * @author daxia li
 * @version 1.0
 * @date 2021/5/24 11:21
 */
public class BufferTest {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(30);
        System.out.printf("position = %d, limit = %d, capacity = %d,\n" ,byteBuffer.position() ,byteBuffer.limit(), byteBuffer.capacity());
        byteBuffer.put("123456".getBytes());
        System.out.printf("position = %d, limit = %d, capacity = %d,\n" ,byteBuffer.position() ,byteBuffer.limit(), byteBuffer.capacity());
//        byteBuffer.put("123456".getBytes());
        byteBuffer.flip();
        System.out.printf("position = %d, limit = %d, capacity = %d,\n" ,byteBuffer.position() ,byteBuffer.limit(), byteBuffer.capacity());
        byteBuffer.put("123".getBytes());
        System.out.printf("position = %d, limit = %d, capacity = %d,\n" ,byteBuffer.position() ,byteBuffer.limit(), byteBuffer.capacity());

    }
}
