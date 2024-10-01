package ad;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server0 {
    private ServerSocket server;
    private Socket client;

    public void init() throws IOException {
        try {
            server = new ServerSocket(8080);
            System.out.println("等待用户连接中...");
            //连接成功主权交给 client
            while (true) {
                client = server.accept();
                System.out.println(client);

                    System.out.println("新用户:" + client.getInetAddress().getHostAddress());
                    Thread thread2 = new Thread(new ServerHandler2(client));
                    Thread thread = new Thread(new ServerHandler(client));
                    thread.start();
                    thread2.start();
                    System.out.println("连接成功,聊天系统已开启：");
            }

        }catch (IOException e) {
            System.out.println("监控端口异常");
            init();
        }
    }

    public static void main(String[] args) throws IOException {
        Server0 server = new Server0();
        try {
            server.init();
        }catch (Exception e) {
            System.out.println("出现异常："+e.getMessage() + "重启系统");
        }
    }
}
