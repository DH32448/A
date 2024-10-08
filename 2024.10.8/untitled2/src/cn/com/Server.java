package cn.com;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server extends HttpServlet {
        PageDao pageDao;
    List<String> strings ;
    @Override
    public void init() throws ServletException {
        pageDao = new PageDao();
        try {
            pageDao.init("D:\\Text.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String path = req.getServletPath();
        HttpSession session = req.getSession();
        System.out.println(pageDao.getCurPage());



        System.out.println(path);
       if (path.equals("/first.do")){
           strings = pageDao.curRead();
           session.setAttribute("t",strings);

           session.setAttribute("a",pageDao.getTotalPages());//总页数
           session.setAttribute("b",pageDao.getCurPage());
           System.out.println("b:"+session.getAttribute("b"));

           pageDao.first();

           req.getRequestDispatcher("/first.jsp").forward(req,resp);}else
       if (path.equals("/last.do")){
           strings = pageDao.curRead();
           session.setAttribute("t",strings);

           session.setAttribute("a",pageDao.getTotalPages());//总页数
           session.setAttribute("b",pageDao.getCurPage());

           pageDao.last();

           req.getRequestDispatcher("/last.jsp").forward(req,resp);}else
       if (path.equals("/next.do")){
           strings = pageDao.curRead();
           session.setAttribute("t",strings);

           session.setAttribute("a",pageDao.getTotalPages());//总页数
           session.setAttribute("b",pageDao.getCurPage());

           pageDao.next();

           req.getRequestDispatcher("/next.jsp").forward(req,resp);}else
       if (path.equals("/prev.do")){
           strings = pageDao.curRead();
           session.setAttribute("t",strings);

           session.setAttribute("a",pageDao.getTotalPages());//总页数
           session.setAttribute("b",pageDao.getCurPage());

           pageDao.prev();

           req.getRequestDispatcher("/prev.jsp").forward(req,resp);}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String path = req.getServletPath();
       HttpSession session = req.getSession();
        System.out.println(path + "post");
        if (path.equals("/changed.do")){
            String a = req.getParameter("a");
            String b = req.getParameter("b");
            if (!a.isEmpty()){
                pageDao.setCurPage(Integer.parseInt(a));
            }
            if (!b.isEmpty()){
                pageDao.setPageSize(Integer.parseInt(b));
            }

            strings = pageDao.curRead();
            session.setAttribute("t",strings);



            session.setAttribute("a",pageDao.getTotalPages());//总页数
            session.setAttribute("b",pageDao.getCurPage());


            req.getRequestDispatcher("/changed.jsp").forward(req,resp);
        }
    }
}
