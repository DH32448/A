package D;

import java.io.*;
import java.net.Socket;

public class Client2 {
    public static void main(String[] args) throws IOException {
        String host = "localhost"; // 或者是接收方的IP地址
        int port = 12345;

        try (Socket socket = new Socket(host, port)) {
            // 发送文件名
            OutputStream os = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(os, true);
            String fileName = "example.txt";
            writer.println(fileName);

            // 等待接收方确认
            InputStream is = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String response = reader.readLine();
            if ("文件名已接收".equals(response)) {
                System.out.println("文件名已确认");

                // 打开文件输入流
                FileInputStream fis = new FileInputStream(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));

                // 发送文件内容
                String line;
                while ((line = br.readLine()) != null) {
                    writer.println(line);
                }

                // 发送结束标志
                writer.println("EOF");

                // 等待接收方最终确认
                response = reader.readLine();
                if ("文件接收完成".equals(response)) {
                    System.out.println("文件已成功发送");
                }
                br.close();
                fis.close();
            }
        }
    }
}
