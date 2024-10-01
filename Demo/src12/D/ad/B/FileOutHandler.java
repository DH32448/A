package D.ad.B;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FileOutHandler implements Runnable {
    private File file;//文件对象：获取文件路径
    private OutputStream out;
    private InputStream in;
    private Socket socket;
    private FileInputStream fileIn;

    public FileOutHandler(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            file = new File("F:\\Text.txt");//初始化文件路径
            fileIn = new FileInputStream(file);//程序读取文件转换字节，存进fileIn
            // 发送文件名
            System.out.println(fileIn.available());//剩余字节
            String fileName = file.getName();
            out.write(fileName.getBytes());//发送文件名称
            out.flush();

            // 读取确认消息
            byte[] bys = new byte[1024];
            int confirmLen = in.read(bys);
            String confirmMessage = new String(bys, 0, confirmLen);
            System.out.println("确认消息: " + confirmMessage);
            if (!confirmMessage.equals("e"))return;//确认对方准备信息


            // 读取并发送数据
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fileIn.read(bytes)) != -1) {//读取文件字节并存到byte数组中
                out.write(bytes, 0, len);//发送字节
                String message = new String(bytes, 0, len);
                System.out.println(message);//打印发送的数据
            }
            socket.close();
        } catch (FileNotFoundException e) {
            System.out.println("文件未找到: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        }
    }
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4567);
        System.out.println("等待连接");
        Socket socket1 = server.accept();
        System.out.println(socket1);
        FileOutHandler fileOutHandler = new FileOutHandler(socket1);
        fileOutHandler.run();
    }
}
