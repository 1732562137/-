package com;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class sqlOperator {

    private static Connection conn;

    static {  //与数据库建立连接
        try {
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "cyf", "admin");
            System.out.println(conn);
//            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "admin");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    static void DoSql(String mysql) throws SQLException {  //SQL模板
        //3.操作数据库，实现增删改查
        System.out.println(mysql);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(new String(mysql));
        //如果有数据，rs.next()返回true
        while(rs.next()) {
            System.out.println(rs.getString("sno") + "  " + rs.getInt("sage"));
        }
        rs.close();
    }

    public static boolean Auth(String user, String password, String userType) throws SQLException {  //登录认证
        if(userType.equals("学生")){
            String mysql2="select spwd from student where sno='"+user+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(new String(mysql2));
            //如果有数据，rs.next()返回true
            try{
                String mypwd = null;
                while(rs.next()) {
                    mypwd=rs.getString("spwd");
                    System.out.println(mypwd );
                }
                assert mypwd != null;
                return mypwd.equals(password);
            }catch (Exception e){
                System.out.println("账号不存在！");
                return false;
            }

        }
        else if(userType.equals("教师")){
            String mysql2="select tpwd from teacher where tno='"+user+"'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(new String(mysql2));
            //如果有数据，rs.next()返回true
            try{
                String mypwd = null;
                while(rs.next()) {
                    mypwd=rs.getString("tpwd");
                    System.out.println(mypwd );
                }
                assert mypwd != null;
                return mypwd.equals(password);
            }catch (Exception e){
                System.out.println("账号不存在！");
                return false;
            }

        }
        else {
            System.out.println("认证类型输入有误！");
            return false;
        }
    }

    //获得学生选课信息
    static List GetCourse(String id) throws SQLException {  //SQL模板
        Statement stmt = conn.createStatement();
        String mysql="select cno from sc where sno='"+id+"'";  //SQL语句不用加;结尾
        ResultSet rs = stmt.executeQuery(new String(mysql));

//        如果有数据，rs.next()返回true
        List<String>course = new ArrayList<String>();
        String getCourse;
        String getCourseName;
        while(rs.next()) {
            getCourse=rs.getString("cno");
            System.out.println(getCourse);
            course.add(getCourse);
        }
        List<String> courseName=new ArrayList<String>();
        for(int i=0;i<course.size();++i){
            String cno=course.get(i);
            String mysql2="select cname from course where cno='"+cno+"'";  //SQL语句不用加;结尾
            ResultSet rs2= stmt.executeQuery(new String(mysql2));
            while(rs2.next()){
                String cname=rs2.getString("cname");
                System.out.println(cname);
                courseName.add(cname);
            }
        }
        return courseName;
    }

    //获得教师授课信息
    public static List<String> getListReplyWithOneLimit(String table, String item, String Lim_name, String Lim) throws SQLException { //获取表table主码为id的字段item的值
        Statement stmt = conn.createStatement();
        String sql=new String("select "+item+" from "+table+" where "+Lim_name+"='"+Lim+"'");
        ResultSet rs = stmt.executeQuery(new String(sql));
        List<String>reply = new ArrayList<String>();
        String elem=null;
        while(rs.next()){
            elem=rs.getString(item);
            reply.add(elem);
            System.out.println(elem);
        }
        return  reply;
    }

    //放回指定字段的所有值
    static List GetOneAll(String table,String item) throws SQLException {  //获得指定表的某个字段的所有值
        Statement stmt = conn.createStatement();
        String mysql="select "+item+" from "+table+" where "+item+" is not null";  //SQL语句不用加;结尾
        ResultSet rs = stmt.executeQuery(new String(mysql));

//        如果有数据，rs.next()返回true
        List<String>allInfo = new ArrayList<String>();
        String elem;
        while(rs.next()) {
            elem=rs.getString(item);
            System.out.println(elem);
            allInfo.add(elem);
        }
        return allInfo;
    }


    public static ResultSet SqlCommon(String sql) throws SQLException {  //通用的Sql执行接口（传入oracle的sql语句）
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(new String(sql));
        try {
//            System.out.println(rs);
            System.out.println("Sql执行成功！");
        }catch (Exception e){
            System.out.println("Sql执行出错！");
        }
        return rs;
    }

    public static String getOneReply(String table,String item,String idName,String id) throws SQLException { //获取表table主码为id的字段item的值
        Statement stmt = conn.createStatement();
        String sql=new String("select "+item+" from "+table+" where "+idName+"='"+id+"'");
        ResultSet rs = stmt.executeQuery(new String(sql));
        String reply=null;
        while(rs.next()){
            reply=rs.getString(item);
            System.out.println(reply);
        }
        return  reply;
    }

    public static String getOneReplyWithOneLimit(String table,String item,String Lim_name,String Lim) throws SQLException { //获取表table主码为id的字段item的值
        Statement stmt = conn.createStatement();
        String sql=new String("select "+item+" from "+table+" where "+Lim_name+"='"+Lim+"'");
        ResultSet rs = stmt.executeQuery(new String(sql));
        String reply=null;
        while(rs.next()){
            reply=rs.getString(item);
            System.out.println(reply);
        }
        return  reply;
    }

    public static String getOneReplyWithTwoLimit(String table,String item,String Lim1_name,String Lim1,String Lim2_name,String Lim2) throws SQLException { //获取表table主码为id的字段item的值
        Statement stmt = conn.createStatement();
        String sql=new String("select "+item+" from "+table+" where "+Lim1_name+"='"+Lim1+"' and "+Lim2_name+"='"+Lim2+"'");
        ResultSet rs = stmt.executeQuery(new String(sql));
        String reply=null;
        while(rs.next()){
            reply=rs.getString(item);
            System.out.println(reply);
        }
        return  reply;
    }


    public static ResultSet queryOneRecord(String table,String id_name,String id) throws SQLException {
        Statement stmt = conn.createStatement();
        String sql=new String("select * from "+table+" where "+id_name+"='"+id+"'");
        ResultSet rs = stmt.executeQuery(new String(sql));
        return rs;
    }

    public static void main(String[] args) throws SQLException {
//        String mysql="select sno,sage from student where sno='1806100001'";
//        DoSql(new String(mysql));
//        if (Auth("T005","T005","教师")) System.out.println("认证通过！");
//        else  System.out.println("账号或密码输入有误！");
//        GetCourse();
//        GetOneAll("student","sno");
//        getOneReply("student","sname","sno","1806100001");
        queryOneRecord("student","sno","1806100001");
//        GetCourse("1806100001");
//        SqlCommon("insert into student(sname,ssex,sno, sage) values('李明','男','1806100006',20)");
    }


}
