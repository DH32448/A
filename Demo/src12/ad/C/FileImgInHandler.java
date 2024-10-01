package ad.C;

import ad.B.FileInHandler;

import java.io.*;
import java.net.Socket;

public class FileImgInHandler implements Runnable {
    private File file;
    private FileOutputStream fileOut;
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public FileImgInHandler(Socket socket) throws IOException {
        this.file = new File("C:\\Users\\10114\\Downloads");
        this.socket = socket;
        this.in = socket.getInputStream();
        this.out = socket.getOutputStream();
    }

    @Override
    public void run() {
        try {
            if (!file.exists()) {
                System.out.println("文件目录不存在");
                return;
            }
            System.out.println("接受png照片开始！");
            //接收目录
            file = new File("C:\\Users\\10114\\Downloads");
            fileOut = new FileOutputStream(file);
            //接收名称
            byte[] buffer = new byte[1024];
            int len = in.read(buffer);
            String filrName = new String(buffer,0,len);
            System.out.println("接收到文件名称：" + filrName);

            File file1 = new File(file,filrName);
            fileOut = new FileOutputStream(file1);


            //发送准备确认信息
            out.write("e".getBytes());
            out.flush();

            byte[] outBy = new byte[1024];
            while ((len = in.read(outBy)) != -1) {
                fileOut.write(outBy,0,len);
                String message = new String(outBy, 0, len);
                System.out.println(message);//打印接收到的数据
                String str = new String(outBy, 0, len);
                System.out.println(str);
            }
            // 关闭资源
            fileOut.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket("127.0.0.1",1234);
        FileInHandler fileInHandler = new FileInHandler(socket1);
        fileInHandler.run();
    }
}
