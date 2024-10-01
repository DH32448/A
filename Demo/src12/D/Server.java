package D;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1123);//创建ServerSocket对象
        System.out.println("服务端已做好接受文件的准备...");
        while (true) {//调用accept()方法接受客户端请求，得到Sockst对象
            Socket s = serverSocket.accept();
            //每当和客户端建立Socket连接后，单独开启一个线程处理和客户的交互
            new Thread(new ServerThread(s)).start();
        }
    }
    //内部类ServerThread
    static class ServerThread implements Runnable {
        private Socket socket;//持有一个Socket类型的属性
        public ServerThread(Socket socket) {//获取方法中把Socket对象作为实参传入
            this.socket = socket;
        }

        @Override
        public void run() {
            String ip = socket.getInetAddress().getHostAddress();
            int count = 1;//上传文件个数
            try {
                InputStream in = socket.getInputStream();
                File parentFile = new File("F:\\File\\");//创建上传文件目录的file对象
                if (!parentFile.exists()) {//如果不存在，就创建这个目录
                    parentFile.mkdir();
                }
                //把客户端的IP地址作为上传文件的文件名
//                File file = new File("F:\\File\\" + count + ".txt");
                File file ;
//                        = new File(parentFile,ip + "("+(count++)+").txt");
                file = new File(parentFile,ip + parentFile.exists() + "("+(count++)+").java");
                //创建FileOutputStream对象
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];
                int len = 0;//定义一个int类型的变量len，初始值为0
                while ((len = in.read(buf)) != -1) {//循环读取数据
                    fos.write(buf, 0, len);
                }
                OutputStream out = socket.getOutputStream();//获取服务端的输出流
                out.write("文件上传成功".getBytes(StandardCharsets.UTF_8));//上传成功后想客户点写出”文件上传成功
                out.flush();
                fos.close();//关闭Socket对象
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
}
