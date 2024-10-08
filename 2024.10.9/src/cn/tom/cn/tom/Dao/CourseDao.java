package cn.tom.cn.tom.Dao;

import cn.tom.entity.Course;
import cn.tom.tool.Jdbc;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDao {
    Connection connection =  null;
    PreparedStatement statement = null;
    ResultSet resultSet = null;
    public CourseDao() throws SQLException {
        this.connection = Jdbc.getCn();
    }
    public int add(Course c) {
        try {
            String sql = "insert into course(cname,tid) values(?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, c.getCname());
            statement.setString(2, c.getTid());
            int row = statement.executeUpdate();
            if (row > 0) {
                return row;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Jdbc.close(connection, statement, resultSet);
        }
        return 0;
    }
    public int update(Course c) {
        try {
            String sql = "update course set name=?, teacher=? where cno=?";
            statement = connection.prepareStatement(sql);
            int row = statement.executeUpdate();
            if (row > 0) {
                return row;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Jdbc.close(connection, statement, resultSet);
        }
        return 0;

    }
    public int removeById(String cno) {
        try {
            String sql = "delete from course where cno=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, cno);
            int row = statement.executeUpdate();
            if (row > 0) {
                return row;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Jdbc.close(connection, statement, resultSet);
        }
        return -1;

    };
    public Course findById(String cno) {
        try {
            String sql = "select * from course where cno=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, cno);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Course c = new Course();
                c.setCno(resultSet.getString("cno"));
                c.setCname(resultSet.getString("cname"));
                c.setCname(resultSet.getString("tid"));
                return c;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            Jdbc.close(connection, statement, resultSet);
        }
        return null;

    }
    public List<Course> findAll() {
        List<Course> courseList = null;
        try {
            String sql = "SELECT * FROM t_course";
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            courseList = new ArrayList<>();
            while (resultSet.next()) {
                String cno = resultSet.getString("cno");
                String cname = resultSet.getString("cname");
                String tid = resultSet.getString("tid");
                Course course = new Course(cno, cname, tid);
                courseList.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            Jdbc.close(connection, statement, resultSet);
        }
        return courseList;
    }

}
