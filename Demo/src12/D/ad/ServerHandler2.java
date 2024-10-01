package D.ad;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerHandler2 implements Runnable {
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private Scanner scanner ;
    public ServerHandler2(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        out = socket.getOutputStream();
        scanner = new Scanner(System.in);
    }
    @Override
    public void run() {
        try {
            System.out.println("聊天功能开启：");
            while (true) {
                    if (scanner.hasNextLine() ) {
                    System.out.println("发送成功：");
                    String input = scanner.nextLine();
                    out.write(input.getBytes());
                        if (input.equals("buy")){
                            System.out.println("聊天系统关闭");
                            Server.main(new String[0]);
                            break;
                        }
                    }

                Thread.sleep(100);
            }
        } catch (IOException e) {
            System.out.println("连接异常");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("线程中断：" + e.getMessage());
        }
    }
}
