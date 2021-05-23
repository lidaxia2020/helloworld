package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNioTest {

    public static void nioChannel(String path) {
        StringBuffer stringBuffer = new StringBuffer();
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(new File(path));
            FileChannel channel = fin.getChannel();
            int capacity = 1024 * 500;// 字节
            ByteBuffer bf = ByteBuffer.allocate(capacity);
            int length = -1;

            while ((length = channel.read(bf)) != -1) {

                /*
                 * 注意，读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                 */
                bf.clear();
                byte[] bytes = bf.array();

                stringBuffer.append(bytes);
                //System.out.write(bytes, 0, length);

            }
            System.out.println(stringBuffer.toString());
            channel.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
