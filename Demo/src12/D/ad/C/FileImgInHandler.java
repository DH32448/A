package D.ad.C;

import java.io.*;
import java.net.Socket;

public class FileImgInHandler implements Runnable {
    private File file;
    private FileOutputStream fileOut;
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public FileImgInHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            System.out.println("接受png照片开始！");

            // 接收文件大小
            byte[] fileSizeBuffer = new byte[1024];
            int fileSizeLen = in.read(fileSizeBuffer);
            String fileSizeStr = new String(fileSizeBuffer, 0, fileSizeLen);
            long fileSize = Long.parseLong(fileSizeStr);
            System.out.println("接收到文件大小：" + fileSize);

            // 发送准备确认信息
            out.write("ready".getBytes());
            out.flush();

            // 接收文件名
            byte[] fileNameBuffer = new byte[1024];
            int fileNameLen = in.read(fileNameBuffer);
            String fileName = new String(fileNameBuffer, 0, fileNameLen);
            System.out.println("接收到文件名称：" + fileName);

            // 发送准备确认信息
            out.write("ready".getBytes());
            out.flush();

            // 创建文件
            file = new File("C:\\Users\\Administrator\\Downloads", fileName);
            fileOut = new FileOutputStream(file);

            // 接收文件内容
            byte[] buffer = new byte[1024]; // 使用更大的缓冲区
            long size = 0;
            int len;
            while (size < fileSize && (len = in.read(buffer)) != -1) {
                fileOut.write(buffer, 0, len);
                size += len;
            }

            // 关闭资源
            fileOut.close();
            socket.close();
            System.out.println("接收完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket("127.0.0.1", 1234);
        FileImgInHandler fileInHandler = new FileImgInHandler(socket1);
        fileInHandler.run();
    }
}
