package ad.C;

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
            //读取文件
            System.out.println("开始读取文件字节");
            file = new File("F:\\Text.png");
            fileIn = new FileInputStream(file);
            System.out.println(fileIn.read());
            System.out.println(fileIn.available());
            //发送名称
            out.write(file.getName().getBytes());
            out.flush();
            //接收信息
            byte[] buffer = new byte[1024];
            int len = in.read(buffer);
            String srt = new String(buffer, 0, len);
            if (!srt.equals("e"))return;

            byte[] data = srt.getBytes();
            int len1 ;
            while ((len1 = fileIn.read(data)) != -1){
                out.write(data, 0, len1);
                System.out.println(fileIn);
            }
            System.out.println("发送完成");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
