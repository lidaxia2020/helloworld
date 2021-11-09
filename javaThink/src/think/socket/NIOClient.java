package think.socket;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author daxia li
 * @version 1.0
 * @date 2021/6/7 11:48
 */
public class NIOClient {

    public static void main(String[] args) throws IOException
    {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress("localhost", 8001));
        socket.configureBlocking(false);

        {
            System.out.println("Not connect");
            // 正真的连接
            while(!socket.finishConnect())
            {
                System.out.println("Not finishConnect");
            }
        }

        ByteBuffer wBuf = ByteBuffer.wrap("Hello, server".getBytes());
        while(wBuf.hasRemaining())
        {
            socket.write(wBuf);
        }

        ByteBuffer rBuf = ByteBuffer.allocate(8);
        StringBuffer sBuf = new StringBuffer();
        while(-1 != (socket.read(rBuf)))
        {
            rBuf.flip();
            String s = new String(rBuf.array(), 0, rBuf.limit());
            sBuf.append(s);
            rBuf.clear();
            if(StringUtils.isEmpty(s))
            {
                break;
            }

        }

        System.out.println("Client received: " + sBuf.toString());
        socket.close();
    }
}
