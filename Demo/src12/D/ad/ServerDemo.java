package D.ad;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ServerDemo {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(1111);//创造服务器对象，监听1111
        System.out.println("服务器启动成功：等待用户端接入");
        //等待用户接入，直到用户端接入
        Socket client = server.accept();
        //得到接入客户端的IP地址
        System.out.println("有客服端启动成功，客户ip：" + client.getInetAddress());
        InputStream in = client.getInputStream();//从客户端生成网络输入流，用于接受来自网络的数据
        OutputStream out = client.getOutputStream();//从客户端生成网络输出留，用于把数据发送到网上
        byte[] bt = new byte[1024];//定义一个字节数组，用来存储网络数据
        int len = in.read(bt);//将网络数据写入字节数组
        String data = new String(bt, 0, len);//将网络数据转换为字符串数据
        System.out.println("来自客户端的信息"+data);
        out.write("这是服务器，欢迎你！".getBytes(StandardCharsets.UTF_8));
        out.flush();
        out.close();//关闭套接字
    }
}
