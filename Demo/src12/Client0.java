import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client0 {
    private Socket socket;
    public void init() throws IOException {
        try {
            socket = new Socket("127.0.0.1", 8080);
            System.out.println("连接成功");
            Thread thread = new Thread(new ClientHandler(socket));
            Thread thread2 = new Thread(new ClientHandler2(socket));
            thread.start();//接送消息线程
            thread2.start();//发送消息线程
        }catch (UnknownHostException e) {
            throw new IOException("连接失败");
        }
    }
    public void close() throws IOException {
        try {
            socket.close();
        }catch (IOException e) {
            throw new IOException("关闭失败");
        }
    }

    public static void main(String[] args) {
        Client0 c = new Client0();
        try {
            c.init();
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
