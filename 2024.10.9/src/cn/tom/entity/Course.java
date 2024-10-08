package cn.tom.entity;

public class Course {
    private String cno;
    private String cname;
    private String tid;

    public Course() {
    }

    public Course(String cname, String tid) {
        this.cname = cname;
        this.tid = tid;
    }

    public Course(String cno, String cname, String tid) {
        this.cno = cno;
        this.cname = cname;
        this.tid = tid;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return "Course{" +
                "cno='" + cno + '\'' +
                ", cname='" + cname + '\'' +
                ", tid='" + tid + '\'' +
                '}';
    }
}
