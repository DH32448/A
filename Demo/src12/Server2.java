import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server2 {
    public static void main(String[] args) throws IOException {
        int port = 12345; // 选择一个合适的端口号
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("等待连接...");

        try (Socket socket = serverSocket.accept()) {
            System.out.println("连接已建立");

            // 接收文件名
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String fileName = reader.readLine();
            System.out.println("接收到文件名: " + fileName);

            // 向发送方发送确认消息
            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);
            writer.println("文件名已接收");

            // 创建文件输出流
            FileOutputStream fos = new FileOutputStream(fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            // 接收文件内容
            String line;
            while (!(line = reader.readLine()).equals("EOF")) {
                bos.write(line.getBytes());
                bos.flush();
            }

            // 关闭文件输出流
            bos.close();
            fos.close();

            // 发送最终确认消息
            writer.println("文件接收完成");
        }

        serverSocket.close();
    }

}
