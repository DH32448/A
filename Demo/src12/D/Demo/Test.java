package D.Demo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Test {
    static Dao dao = new Dao();

    public static void main(String[] args) throws IOException {
        dao.init("f:/22.txt"); // 初始化数据
        int port = 8080; // 监听的端口
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("服务器启动，监听端口：" + port);
        while (true) {
            Socket clientSocket = serverSocket.accept(); // 等待客户端连接
            System.out.println("新的客户端连接：" + clientSocket.getInetAddress().getHostAddress());

            // 为每个客户端创建一个新的线程来处理请求
            new Thread(() -> handleRequest(clientSocket)).start();
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {
            String inputLine;

            // 读取请求行和请求头
            while ((inputLine = reader.readLine()) != null && !inputLine.isBlank()) {
                System.out.println(inputLine);
                // 这里可以添加解析请求的逻辑
                if (inputLine.startsWith("GET")) {
                    handleGetRequest(inputLine, writer);
                    return;
                }
            }

            // 如果没有匹配到GET请求，发送400 Bad Request
            sendResponse(writer, 400, "Bad Request");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleGetRequest(String requestLine, PrintWriter writer) {
        String[] tokens = requestLine.split(" ");
        if (tokens.length != 3) {
            sendResponse(writer, 400, "Bad Request");
            return;
        }

        String method = tokens[0];
        String path = tokens[1];

        if (!method.equals("GET")) {
            sendResponse(writer, 405, "Method Not Allowed");
            return;
        }

        // 根据路径处理分页请求
        if (path.equals("/first.do")) {
            dao.first();
        } else if (path.equals("/prev.do")) {
            dao.prev();
        } else if (path.equals("/next.do")) {
            dao.next();
        } else if (path.equals("/last.do")) {
            dao.last();
        } else if (path.equals("/") || path.equals("/index.html")) {
            // 默认主页
            sendResponse(writer, 200, "<html><body><h1>欢迎访问分页系统</h1><p><a href=\"/first.do\">首页</a></p></body></html>");
            return;
        } else if (path.equals("/favicon.ico")) {
            // 忽略 favicon 请求
            return;
        } else {
            sendResponse(writer, 404, "Not Found");
            return;
        }

        // 获取当前页的数据
        List<String> lines = dao.showFile();
        StringBuilder responseContent = new StringBuilder();
        responseContent.append("<html><head><style>");
        responseContent.append("body { font-family: Arial, sans-serif; }");
        responseContent.append(".container { max-width: 800px; margin: 0 auto; padding: 20px; }");
        responseContent.append(".pagination { display: flex; justify-content: center; margin-top: 20px; }");
        responseContent.append(".pagination a, .pagination span { margin: 0 5px; padding: 5px 10px; text-decoration: none; color: #333; background-color: #f1f1f1; border: 1px solid #ddd; }");
        responseContent.append(".pagination a:hover { background-color: #ddd; }");
        responseContent.append("</style></head><body>");
        responseContent.append("<div class=\"container\">");
        responseContent.append("<h1>当前页: ").append(dao.curPage + 1).append("</h1>");
        responseContent.append("<form action= \"/prev.do\"" +
                " method =\"post\"><button type=\"submit\">上页</button>" +
                "</form>");
        responseContent.append("<form action= \"/next.do\"" +
                " method =\"post\"><button type=\"submit\">下页</button>" +
                "</form>");
        for (String line : lines) {
            responseContent.append("<p>").append(line).append("</p>");
        }

        // 添加导航链接
        responseContent.append("<div class=\"pagination\">");
        if (dao.curPage > 0) {
            responseContent.append("<form action= \"/prev.do\"" +
                    " method =\"post\"><button type=\"submit\">上页</button>" +
                    "/form");
        } else {
            responseContent.append("<span>上页</span>");
        }

        if (dao.curPage < dao.totalPages - 1) {
            responseContent.append("<a href=\"/next.do\">下页</a>");
        } else {
            responseContent.append("<span>下页</span>");
        }

        responseContent.append("<a href=\"/first.do\">首页</a>");
        responseContent.append("<a href=\"/last.do\">最后页</a>");
        responseContent.append("</div></div></body></html>");

        sendResponse(writer, 200, responseContent.toString());
    }
    private static void sendResponse(PrintWriter writer, int status, String content) {
        writer.println("HTTP/1.1 " + status + " OK");
        writer.println("Content-Type: text/html; charset=UTF-8");
        writer.println("Content-Length: " + content.length());
        writer.println();
        writer.print(content);
        writer.flush();
    }
}