package D.ad.B;

import java.io.*;
import java.net.Socket;

public class FileInHandler implements Runnable {
    private File file; // 文件上传目录
    private OutputStream out;
    private InputStream in;
    private Socket socket;
    private FileOutputStream fileOut;

    public FileInHandler(Socket socket) throws IOException {
        this.out = socket.getOutputStream();
        this.in = socket.getInputStream();
        this.socket = socket;
        this.file = new File("d:\\text");
    }
    @Override
    public void run() {
        try {
            if (!file.exists()) {
                System.out.println("文件目录不存在");
                return;
            }

            // 读取文件名
            byte[] inBy = new byte[1024];
            int len = in.read(inBy);
            String fileName = new String(inBy, 0, len).trim();
            System.out.println("接收到的文件名为：" + fileName);

            // 创建文件对象
            File inFile = new File(file, fileName);

            // 打开文件输出流
            fileOut = new FileOutputStream(inFile);

            // 发送确认消息
            out.write("e".getBytes());
            out.flush();
            // 读取并写入数据
            byte[] outBy = new byte[1024];
            while ((len = in.read(outBy)) != -1) {
                String message = new String(outBy, 0, len);
                fileOut.write(message.getBytes());
                String str = new String(outBy, 0, len);
            }
            System.out.println("-----3");

            // 关闭资源
            fileOut.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("文件接收异常");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket("127.0.0.1", 4567);
        FileInHandler fileInHandler = new FileInHandler(socket1);
        fileInHandler.run();
    }
}