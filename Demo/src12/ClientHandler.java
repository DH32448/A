import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }
    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            int len;
            while (true) {
                if (in.available() > 0) {
                    len = in.read(buffer);
                    if (len != -1){
                        String message = new String(buffer, 0, len);
                        System.out.println("服务端信息：" + message);
                        if (message.equals("buy")) {
                            out.write("buy".getBytes());
                            System.out.println("聊天系关闭!");
                            break;
                        }
                    }
                }Thread.sleep(100);
            }
        }catch (IOException e){
            System.err.println("客户端异常: " + e.getMessage());
            e.printStackTrace();
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            System.out.println("线程中断：" + e.getMessage());
        }
    }
}
