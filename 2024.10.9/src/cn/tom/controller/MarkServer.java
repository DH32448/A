package cn.tom.controller;

import cn.tom.cn.tom.Dao.CourseDao;
import cn.tom.entity.Course;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MarkServer extends HttpServlet {
    CourseDao courseDao;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=utf8");
        String cmd = req.getServletPath();
        System.out.println("cmd = " + cmd);
        try {
            if ("/course/show.do".equals(cmd)) go2ShowCourse(req, resp);
            if ("/course/add.do".equals(cmd)) go2AddCourse(req, resp);
            else if ("/course/doAdd.do".equals(cmd))addCourse(req, resp);
            else if ("/course/doUpdate.do".equals(cmd))go2UpdateCourse(req, resp);
            else if ("/course/del.do".equals(cmd))delCourse(req, resp);
            else {
                resp.getWriter().println("<h2>正在建设中：" + cmd + "</h2>");
                resp.flushBuffer();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void go2AddCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/course/add.jsp").forward(req,resp);
    }
    private void addCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = new Course();
        String cno = req.getParameter("cno");
        String cname = req.getParameter("cname");
        course.setCno(cno);
        course.setCname(cname);
        courseDao.add(course);
        req.getRequestDispatcher("/course/show.jsp").forward(req,resp);
    }
    private void go2UpdateCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Course course = new Course();
        String cno = req.getParameter("cno");
        String cname = req.getParameter("cname");
        String tid = req.getParameter("tid");
        course.setCno(cno);
        course.setCname(cname);
        course.setTid(tid);
        courseDao.update(course);
        req.getRequestDispatcher("/course/show.jsp").forward(req,resp);

    }
    private void delCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/course/show.jsp").forward(req,resp);

    }
    private void go2ShowCourse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Course> cs = new CourseDao().findAll();
        for (Course course : cs){
            System.out.println(course.toString());
        }
        req.setAttribute("cs",cs);
        System.out.println("--------");
        req.getRequestDispatcher("/WEB-INF/jsp/course/show.jsp").forward(req,resp);
    }

}
