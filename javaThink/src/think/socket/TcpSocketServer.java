package think.socket;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author lijiannan
 * @version 1.0
 * @date 2021/6/7 9:25
 */
public class TcpSocketServer {

    public static void main(String[] args) {
        try {
            // 创建服务端socket
            ServerSocket serverSocket = new ServerSocket(8088);

            // 创建客户端socket
            Socket socket = new Socket();

            //循环监听等待客户端的连接
            while (true) {
                // 监听客户端
                socket = serverSocket.accept();

                ServerThread thread = new ServerThread(socket);
                thread.start();

                InetAddress address = socket.getInetAddress();
                System.out.println("当前客户端的IP：" + address.getHostAddress());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }


    public static class ServerThread extends Thread {

        private Socket socket = null;

        public ServerThread(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            InputStream is = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            OutputStream os = null;
            PrintWriter pw = null;
            try {
                is = socket.getInputStream();
                isr = new InputStreamReader(is);
                br = new BufferedReader(isr);

                String info = null;

                // readLine 读取一个文本行。通过下列字符之一即可认为某行已终止
                while ((info = br.readLine()) != null) {
                    System.out.println("我是服务器，客户端说：" + info);
                }
                socket.shutdownInput();

                os = socket.getOutputStream();
                pw = new PrintWriter(os);
                pw.write("服务器欢迎你");

                pw.flush();
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                //关闭资源
                try {
                    if (pw != null) {
                        pw.close();
                    }
                    if (os != null)
                        os.close();
                    if (br != null)
                        br.close();
                    if (isr != null)
                        isr.close();
                    if (is != null)
                        is.close();
                    if (socket != null)
                        socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
