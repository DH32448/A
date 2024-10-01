package Demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    int curPage = 0; // 当前页
    int totalPages = 0; // 总页数
    private int pageNum = 6; // 每页几行
    private static List<String> lstAll = new ArrayList<>(); // 文件所有内容

    public void init(String filename) throws IOException {
        // 初始化读取浏览文件内容并计算总页数
        InputStream is = new FileInputStream(filename);
        InputStreamReader isr = new InputStreamReader(is, "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        for (String line = br.readLine(); line != null; line = br.readLine()) {
            lstAll.add(line);
        }
        br.close();
        isr.close();
        is.close();
        totalPages = (lstAll.size() + pageNum - 1) / pageNum;
    }

    public List<String> showFile() {
        // 返回当前页内容
        List<String> lst = new ArrayList<>();
        int start = curPage * pageNum;
        for (int i = start; i < start + pageNum && i < lstAll.size(); i++) {
            lst.add(lstAll.get(i));
        }
        return lst; // 返回当前页的内容
    }

    public void prev() {
        if (curPage > 0) curPage--;
    }

    public void next() {
        if (curPage < totalPages - 1) curPage++;
    }

    public void first() {
        curPage = 0;
    }

    public void last() {
        curPage = totalPages - 1;
    }
}