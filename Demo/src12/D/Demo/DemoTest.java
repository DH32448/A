package D.Demo;

import java.io.IOException;

public class DemoTest {
    static Dao d = new Dao();

    public static void main(String[] args) throws IOException {
        while (true) {
            String a = Tool.getString("请输入: 0. 首页  >. 下一页   <. 上一页   9. 尾页  Q. 退出");
            // 首页
            if (a.equals("0")) zon(a);
            // 下一页
            if (a.equals(">")) zon(a);
            // 上一页
            if (a.equals("<")) zon(a);
            // 尾页
            if (a.equals("9")) zon(a);
            // 结束
            if (a.equals("Q")) System.exit(0);
        }
    }

    public static void zon(String a) throws IOException {
        d.init("d:/work_605/22.txt");
        if (a.equals("0")) {
            d.first();
        }
        if (a.equals(">")) {
            d.next();
        }
        if (a.equals("<")) {
            d.prev();
        }
        if (a.equals("9")) {
            d.last();
        }
        for (int i = 0; i < d.showFile().size(); i++) {
            System.out.println(d.showFile().get(i));
        }
    }
}