package think.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author lijiannan
 * @version 1.0
 * @date 2021/6/7 11:47
 */
public class NIOServer {

    public static void main(String[] args) throws IOException
    {
        // 启动Socket Server Channel
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(8001));
        server.configureBlocking(false);

        // 绑定选择器，注册连接监听事件
        Selector selector = Selector.open();
        server.register(selector, SelectionKey.OP_ACCEPT);

        SelectorHandler handler = new SelectorHandler();
        while(true)
        {
            // 非阻塞监听注册事件
            if(selector.select(2000) == 0)
            {
                System.out.println("No selection");
                continue;
            }

            // 发现注册事件，依次处理
            Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
            while(keyIter.hasNext())
            {
                SelectionKey key = keyIter.next();
                if(key.isAcceptable())
                {
                    handler.doAccept(key);
                }

                if(key.isReadable())
                {
                    handler.doRead(key);
                }

                if(key.isValid() && key.isWritable())
                {
                    handler.doWrite(key);
                }

                keyIter.remove();
            }
        }
    }

    /**
     * 事件处理器
     * @author h00219638
     */
    public static class SelectorHandler
    {
        // 连接请求处理
        public void doAccept(SelectionKey key) throws IOException
        {
            SocketChannel socket = ((ServerSocketChannel)key.channel()).accept();
            socket.configureBlocking(false);
            // 注册数据读取事件
            socket.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(1024));
        }

        public void doRead(SelectionKey key) throws IOException
        {
            SocketChannel socket = (SocketChannel)key.channel();

            // 也可以临时分配ByteBuffer
            ByteBuffer buf = (ByteBuffer) key.attachment();
            buf.clear();

            if(-1 == socket.read(buf))
            {
                socket.close();
            }
            else
            {
                System.out.println("Server received: " + new String(buf.array(), 0, buf.limit()));

                /**
                 * public static final int OP_READ = 1 << 0;
                 public static final int OP_WRITE = 1 << 2;
                 public static final int OP_CONNECT = 1 << 3;
                 public static final int OP_ACCEPT = 1 << 4;
                 */
                // 增加写事件，写事件会不断被触发，数据写完后必须取消写事件监听
                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
            }
        }

        public void doWrite(SelectionKey key) throws IOException
        {
            SocketChannel socket = (SocketChannel)key.channel();
            ByteBuffer buf = (ByteBuffer) key.attachment();

            // 写数据之前注意调用flip方法，重置指针
            buf.flip();
            System.out.println("Write: " + new String(buf.array(), 0, buf.limit()));
            socket.write(buf);
            if(!buf.hasRemaining())
            {
                // 取消写事件监听
                key.interestOps(key.interestOps() &~  SelectionKey.OP_WRITE);
            }
            buf.compact();
        }
    }


}
