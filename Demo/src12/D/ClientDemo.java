package D;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientDemo {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("192.168.137.1",1111);
        System.out.println("连接服务器成功");
        InputStream in = client.getInputStream();//从客户端生成网络输入流，用于接受来自网络上的数据
        OutputStream out = client.getOutputStream();//从客户端生成输出六，用于吧数据发送到网上
        out.write("这是客户端".getBytes(StandardCharsets.UTF_8));
        out.flush();//确保所有数据都被发送出去
        byte[] bt = new byte[1024];//定义字节数组，用来存贮网络数据
        int len = in.read(bt);//将网络数据写书字节数组
        String data = new String(bt, 0, len);
        System.out.println("来自服务器的信息：" + data);
        client.close();
    }
}
