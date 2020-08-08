package com;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class studentSys {
    public static void main(String[] args) throws SQLException {
        studentWin("1806100001");
    }

    static JFrame stuWin;
    static JComboBox stuID;
    static JTextField stuName;
    static JTextField stuSex;
    static JTextField stuAge;
    static JTextField stuPwd;
    static JComboBox<String> stuCourse;
    static JComboBox<String> stuGetCourse;
    static JTextField total_credit;
    static JTable table;

    static JButton stuModify;
    static JButton stuSelectCourse;
    static JButton stuDeselectCourse;
//    static JButton stuQuery;

    public static void studentWin(String get_id) throws SQLException {
        stuWin=new JFrame("欢迎使用学生信息系统");
        stuWin.setSize(900,800);
        stuWin.setLocationRelativeTo(null);
        stuWin.setResizable(false);  //设置窗口不可缩放
//        stuWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stuWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  //关闭当前窗口，但不退出整个程序
        stuWin.setLayout(null);

        //标签
        JLabel welcome=new JLabel("欢迎使用学生信息系统");
        JLabel id=new JLabel("学号");  //从登陆的信息获取
        JLabel name=new JLabel("姓名");
        JLabel sex=new JLabel("性别");
        JLabel age=new JLabel("年龄");
        JLabel pwd=new JLabel("密码");

        Font font=new Font("楷体",Font.BOLD,20);
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        welcome.setBounds(200,0,400,100);
        id.setFont(font);id.setBounds(50,65,100,100);
        name.setFont(font);name.setBounds(50,110,100,100);
        sex.setFont(font);sex.setBounds(50,155,100,100);
        age.setFont(font);age.setBounds(50,200,100,100);
        pwd.setFont(font);pwd.setBounds(50,245,100,100);

        stuWin.add(welcome);
        stuWin.add(id);
        stuWin.add(name);
        stuWin.add(sex);
        stuWin.add(age);
        stuWin.add(pwd);

        //输入框
        stuID=new JComboBox();
        stuID.addItem(get_id);
//        stuID.setEditable(false);
        stuName=new JTextField();
        stuSex=new JTextField();
        stuAge=new JTextField();
        stuPwd=new JTextField();

        stuID.setBounds(150,100,400,30);
        stuName.setBounds(150,145,400,30);
        stuSex.setBounds(150,190,400,30);
        stuAge.setBounds(150,235,400,30);
        stuPwd.setBounds(150,280,400,30);

        stuWin.add(stuID);
        stuWin.add(stuName);
        stuWin.add(stuSex);
        stuWin.add(stuAge);
        stuWin.add(stuPwd);

        //选课
        stuCourse=new JComboBox<>();
        stuCourse.setBounds(330,350,400,30);
        stuGetCourse=new JComboBox<>();
        stuGetCourse.setBounds(330,400,400,30);
        JLabel all_course=new JLabel("可选课程");
        JLabel get_course=new JLabel("已选课程");
        JLabel course=new JLabel("选课系统");
        course.setBounds(50,320,500,100);
        course.setFont(new Font("楷体",Font.BOLD,30));
        all_course.setBounds(230,320,500,100);
        all_course.setFont(font);
        get_course.setBounds(230,370,500,100);
        get_course.setFont(font);
        stuWin.add(course);
        stuWin.add(stuCourse);
        stuWin.add(stuGetCourse);
        stuWin.add(all_course);
        stuWin.add(get_course);

        //选课信息显示
        JLabel credit=new JLabel("总学分");
        credit.setBounds(770,635,100,100);
        credit.setFont(font);
        total_credit=new JTextField();
        total_credit.setBounds(750,700,100,30);
        stuWin.add(credit);
        stuWin.add(total_credit);
        total_credit.setEditable(false);
        total_credit.setFont(new Font("宋体",Font.BOLD,20));
        total_credit.setHorizontalAlignment(JTextField.CENTER);  //设置字体居中

        //选课信息表
//        String []columnNames={"课程号","课程名称","授课老师","学分"};
//        Object [][]date={{"1","2","3","4"}};
        table=new JTable();
//        JScrollPane pane=new JScrollPane(table);
//        pane.setBounds(50,450,700,280);
//        stuWin.add(pane);
        showSelectedCourseInfo();

        //按钮
//        stuQuery=new JButton("查询");
        stuModify=new JButton("修改");
        stuSelectCourse=new JButton("选课");
        stuDeselectCourse=new JButton("退课");
//        stuQuery.setBounds(600,100,150,80);
        stuModify.setBounds(600,150,150,80);
        stuSelectCourse.setBounds(750,350,100,40);
        stuDeselectCourse.setBounds(750,400,100,40);

//        stuWin.add(stuQuery);
        stuWin.add(stuModify);
        stuWin.add(stuSelectCourse);
        stuWin.add(stuDeselectCourse);

        //登录系统后，自动显示个人信息

        List stuInfo = new List();
        ResultSet rs=sqlOperator.queryOneRecord("student","sno", get_id);
        while(rs.next()){
            stuInfo.add(rs.getString("sname"));
            stuInfo.add(rs.getString("ssex"));
            stuInfo.add(rs.getString("sage"));
            stuInfo.add(rs.getString("spwd"));
        }
        stuName.setText(stuInfo.getItem(0));
        stuSex.setText(stuInfo.getItem(1));
        stuAge.setText(stuInfo.getItem(2));
        stuPwd.setText(stuInfo.getItem(3));

        //显示学生选择的课程及可选的课程
        get_all_course();
        get_selected_course();

        //添加触发器
        ButtonListener buttonListener=new ButtonListener();
        stuModify.addActionListener(buttonListener);
        stuSelectCourse.addActionListener(buttonListener);
        stuDeselectCourse.addActionListener(buttonListener);

        stuWin.setVisible(true);
    }

    public static void get_all_course() throws SQLException {  //录入新课程后刷新全部课程信息
        stuCourse.removeAllItems();
        java.util.List get_all_course_name=sqlOperator.GetOneAll("course","cname");
        for(int i=0;i<get_all_course_name.size();++i){
            stuCourse.addItem((String) get_all_course_name.get(i));
        }
    }
    public static void get_selected_course() throws SQLException {  //录入新课程后刷新全部课程信息
        stuGetCourse.removeAllItems();
        java.util.List getSelecteCourse=sqlOperator.GetCourse((String) stuID.getSelectedItem());
        for(int i=0;i<getSelecteCourse.size();++i){
            stuGetCourse.addItem((String) getSelecteCourse.get(i));
        }
    }
    //在表格中显示已选课程的信息
    public static void showSelectedCourseInfo() throws SQLException {
        table.removeAll();
//        String []columnNames={"课程号","课程名称","授课老师","学分"};
        int total_Credit=0;
        Vector date=new Vector();
        ResultSet rs=sqlOperator.queryOneRecord("sc","sno", (String) stuID.getSelectedItem());
        while(rs.next()){
            String course_id=rs.getString("cno");
            ResultSet courseInfo=sqlOperator.queryOneRecord("course","cno",course_id);
            Vector info=new Vector();
            while(courseInfo.next()){
                info.add(courseInfo.getString("cno"));
                info.add(courseInfo.getString("cname"));
                String tno=courseInfo.getString("tno");
                String tname=sqlOperator.getOneReply("teacher","tname","tno",tno);
                info.add(tname);
                int cource_credit=courseInfo.getInt("ccredit");
                total_Credit+=cource_credit;
                info.add(cource_credit);;
            }
            date.add(info);
        }

        
        Vector columnNames=new Vector();
        columnNames.add("课程号");
        columnNames.add("课程名称");
        columnNames.add("授课老师");
        columnNames.add("学分");

        System.out.println(date);
        table=new JTable(date,columnNames);
        table.setAutoResizeMode(4);
        table.setFont(new Font("宋体",Font.BOLD,20));
        table.setRowHeight(24);  //设置行距
        JScrollPane pane=new JScrollPane(table);
        pane.setBounds(50,450,700,280);
        stuWin.add(pane);

        //计算总学分
        total_credit.setText(String.valueOf(total_Credit));
    }
    static class ButtonListener implements ActionListener {  //修改密码和学生信息管理的button监听器
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("修改")) {
                String id=(String) stuID.getSelectedItem();
                String name=stuName.getText();
                String sex=stuSex.getText();
                String age=stuAge.getText();
                String pwd=stuPwd.getText();
                if(name.isEmpty()||sex.isEmpty()||age.isEmpty()||pwd.isEmpty()){
                    InfoSysMain.Tips("输入信息不完整，请补全信息后再修改！");
                }else{
                    try {
                        sqlOperator.SqlCommon("update student set sname='"+name+"' where sno='"+id+"'");
                        sqlOperator.SqlCommon("update student set ssex='"+sex+"' where sno='"+id+"'");
                        sqlOperator.SqlCommon("update student set sage='"+age+"' where sno='"+id+"'");
                        sqlOperator.SqlCommon("update student set spwd='"+pwd+"' where sno='"+id+"'");
                        InfoSysMain.Tips("个人信息修改成功！");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else if (e.getActionCommand().equals("选课")) {
                try {
                    String lesson= (String) stuCourse.getSelectedItem();  //获取选择的课程
                    int flag=1;  //判断是否该课程已选择了
                    for(int i=0;i<stuGetCourse.getItemCount();++i){
                        String course=stuGetCourse.getItemAt(i);
                        if(course.equals(lesson)){
                            flag=0;  //课程已选择
                            InfoSysMain.Tips("该课程已选择，无需重复选择！");
                            break;
                        }
                    }
                    if(flag==1){  //需要增加课程
                        String id= (String) stuID.getSelectedItem();
                        String cno=sqlOperator.getOneReply("course","cno","cname",lesson);  //获取选择的课程的id
                        sqlOperator.SqlCommon("insert into sc(sno,cno) values('"+id+"','"+cno+"')");
                        InfoSysMain.Tips("课程选择成功！");
                        get_selected_course();  //刷新已选课程
                        showSelectedCourseInfo();  //刷新课程信息
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("退课")){
                String lesson= (String) stuGetCourse.getSelectedItem();  //获取退选课程
                try {
                    String cno=sqlOperator.getOneReply("course","cno","cname",lesson);  //获取退选课程的cno
                    String id= (String) stuID.getSelectedItem();
                    sqlOperator.SqlCommon("delete from sc where sno='"+id+"' and cno='"+cno+"'");
                    InfoSysMain.Tips("退课成功！");
                    get_selected_course();  //刷新已选课程
                    showSelectedCourseInfo();  //刷新课程信息
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
