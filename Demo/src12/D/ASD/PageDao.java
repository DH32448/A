package D.ASD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class PageDao {
    private int curPage = 1;// 当前页
    private int totalPages;// 总页数
    private int pageSize = 6;// 每页几行
    private List<String> lstAll = new ArrayList<>();

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

}
