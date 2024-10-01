package D;

import com.sun.source.tree.Scope;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.137.1",8080);
        OutputStream out = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("D:\\春节快乐.txt");
        byte[] buf = new byte[1024];
        int len;
        while ((len = fis.read(buf)) != -1) {
            out.write(buf, 0, len);
            out.flush();
        }
        socket.shutdownOutput();
        InputStream in = socket.getInputStream();
        byte[] buf1 = new byte[1024];
        int num = in.read(buf1);
        String Msg = new String(buf1,0,num);
        System.out.println(Msg);
        fis.close();
        socket.close();
        System.out.println("Buy");
    }
}
