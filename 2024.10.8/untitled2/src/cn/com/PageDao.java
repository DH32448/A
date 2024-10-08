package cn.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PageDao {
    private int curPage = 1;// 当前页
    private int totalPages = 1;// 总页数
    private int pageSize = 5;// 每页几行
    private List<String> lstAll = new ArrayList<>();

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getLstAll() {
        return lstAll;
    }

    public void setLstAll(List<String> lstAll) {
        this.lstAll = lstAll;
    }

    //读取文件
    public void init(String filename) throws IOException{
        try{
            FileReader fileReader = new FileReader(filename, Charset.forName("GBK"));
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null ){
                lstAll.add(line);
            }
            totalPages = (lstAll.size()/pageSize) + 1;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //当前页数据
    public List<String> curRead(){
        List<String> curList = new ArrayList<>();
        int count = curPage * pageSize;
        for (int i = count; i < lstAll.size() && i < count + pageSize; i++){
            curList.add(lstAll.get(i));
        }
        return curList;
    }
    // 上一页
    public void prev() {
        if (curPage > 0) {
            curPage--;
        }
    }

    // 下一页
    public void next() {
        if (curPage < totalPages - 1) {
            curPage++;
        }
    }

    // 第一页
    public void first() {
        curPage = 0;
    }

    // 最后一页
    public void last() {
        curPage = totalPages - 1;
    }
    public static void main(String[] args) throws IOException {
        PageDao pageDao1 = new PageDao();
        pageDao1.init("D:\\Text.txt");
        List<String> strings = new ArrayList<>();
        strings = pageDao1.curRead();
        for (String s : strings){
            System.out.println(s);
        }
    }
}
