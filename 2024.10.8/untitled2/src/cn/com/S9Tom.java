package cn.com;

import javax.swing.plaf.IconUIResource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class S9Tom {
    private PageDao pageDao;
    public S9Tom() {
        pageDao = new PageDao();
        try {
            pageDao.init("D:\\Text.txt"); // 替换为实际文件路径
            System.out.println("总页："+pageDao.getTotalPages());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() throws IOException {
        ServerSocket server = new ServerSocket(1111);
        System.out.println("等待链接");
        while (true) {
            try{
                Socket socket = server.accept();
                System.out.println(socket);
                InputStream in = socket.getInputStream();
                byte[] inBy = new byte[1024];
                int len = in.read(inBy);
                String str = new String(inBy,0,len);
                String[] parts = str.split(" ");
                if (parts.length >= 2) {
                    String uri = parts[1];
                    switch (uri) {
                        case "/first":
                            System.out.println(pageDao.getTotalPages());
                            System.out.println(pageDao.getCurPage());

                            pageDao.first();
                            break;
                        case "/prev":
                            pageDao.prev();
                            break;
                        case "/next":
                            pageDao.next();
                            break;
                        case "/last":
                            pageDao.last();
                            break;
                        default:
                            break;
                    }
                }
                if (len > 0){
                    String str2 = new String(inBy,0,len);
                    System.out.println(str2);
                    OutputStream out = socket.getOutputStream();
                    StringBuffer strBuff = new StringBuffer();
                    strBuff.append("Http/1.1 200 OK\n");
                    strBuff.append("Content-Type: text/html; charset=UTF-8\n");
                    strBuff.append("\n");
                    String html = "<!DOCTYPE html>\n" +
                            "<html lang=\"en\">\n" +
                            "<head>\n" +
                            "    <meta charset=\"UTF-8\">\n" +
                            "    <title>Socket网页</title>\n" +
                            "</head>\n" +
                            "<body>\n" +
                            "<h1>当前页数据：</h1>\\n" +
                            "<ul>\n" ;
                    //方法
                    List<String> list = pageDao.curRead();
                    for (String line : list) {
                        strBuff.append("<li>").append(line).append("</li>\n");
                    }
                    String html2 =         "</ul>\n" +
                            "    <form method=\"get\">\n" +
                            "        <a href=\"first\">首页</a>\n" +
                            "        <a href=\"prev\">上一页</a>\n" +
                            "        <a href=\"next\">下一页</a>\n" +
                            "        <a href=\"last\">最后一页</a>\n" +
                            "    </form>\n" +
                            "</body>\n" +
                            "</html>";
                    strBuff.append(html);
                    strBuff.append(html2);
                    out.write(strBuff.toString().getBytes());
                    out.flush();
                    out.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
    public void c(String req){

    }

    public static void main(String[] args) throws IOException {
        S9Tom s9Tom = new S9Tom();
        s9Tom.run();
    }
}


