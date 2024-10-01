package D.ad.C;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FlierImgOutHandler implements Runnable{
    private Socket socket;
    private InputStream in;
    private OutputStream out;
    private File file;
    private FileInputStream fileIn;

    public FlierImgOutHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            System.out.println("发送文件开始！");

            // 读取文件
            System.out.println("开始读取文件字节");
            file = new File("E:\\Text.png");
            fileIn = new FileInputStream(file);

            // 发送文件大小
            long fileSize = file.length();
            System.out.println("文件大小：" + fileSize);
            out.write(Long.toString(fileSize).getBytes());
            out.flush();

            // 接收确认信息
            byte[] by = new byte[1024];
            int len2 = in.read(by);
            String confirmStr = new String(by, 0, len2);
            if (!confirmStr.equals("ready")) return;

            // 发送文件名
            out.write(file.getName().getBytes());
            out.flush();

            // 再次接收确认信息
            len2 = in.read(by);
            confirmStr = new String(by, 0, len2);
            if (!confirmStr.equals("ready")) return;

            // 发送文件内容
            byte[] buffer = new byte[1024]; // 使用更大的缓冲区
            int len;
            while ((len = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, len);
                out.flush();
            }

            System.out.println("发送完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ServerSocket socket1 = new ServerSocket(1234);
        System.out.println("等待用户");
        Socket socket2 = socket1.accept();
        System.out.println(socket2);
        FlierImgOutHandler flierImgOutHandler = new FlierImgOutHandler(socket2);
        flierImgOutHandler.run();
    }
}
