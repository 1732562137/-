package com;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class teacherSys {
    public static void main(String[] args) throws SQLException {
        teacherWin("T001");
    }


    static JFrame teachWin;
    static JComboBox sel_tId;
    static JComboBox<String> tId;
    static JTextField tName;
    static JTextField tSex;
    static JTextField tSalary;
    static JTextField tPosition;
    static JTextField tPwd;

    static JComboBox<String> t_all_course;  //显示course系统的所有课程
    static JComboBox<String> t_allow_sel_course;  //可选课程
    static JComboBox<String> t_get_course;  //已选课程

    static JButton tModify;

    static JTable courseList;

    static JButton getCourseInfo;
    static JButton selectCourse;
    static JButton deselectCourse;

    public static void teacherWin(String ID) throws SQLException {
        teachWin=new JFrame("欢迎使用教师信息管理系统");
        teachWin.setSize(900,880);
        teachWin.setLocationRelativeTo(null);
        teachWin.setResizable(false);  //设置窗口不可缩放
//        teachWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teachWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        teachWin.setLayout(null);
        //标签
        JLabel welcome=new JLabel("欢迎使用教师信息管理系统");
        JLabel id = new JLabel("职工号");
        JLabel name = new JLabel("姓名");
        JLabel sex = new JLabel("性别");
        JLabel salary = new JLabel("薪水");
        JLabel position = new JLabel("职称");
        JLabel pwd = new JLabel("密码");

        Font font=new Font("楷体",Font.BOLD,20);

        welcome.setFont(new Font("楷体",Font.BOLD,30));
        welcome.setBounds(200,0,400,100);teachWin.add(welcome);
        id.setFont(font);id.setBounds(45,65,100,100);teachWin.add(id);
        name.setFont(font);name.setBounds(50,110,100,100);teachWin.add(name);
        sex.setFont(font);sex.setBounds(50,155,100,100);teachWin.add(sex);
        salary.setFont(font);salary.setBounds(50,200,100,100);teachWin.add(salary);
        position.setFont(font);position.setBounds(50,245,100,100);teachWin.add(position);
        pwd.setFont(font);pwd.setBounds(50,290,100,100);teachWin.add(pwd);

        JLabel teach_course= new JLabel("授课管理系统");
        JLabel all_course = new JLabel("所有课程");
        JLabel allow_sel_course=new JLabel("可选课程");
        JLabel get_course=new JLabel("已选课程");
        teach_course.setFont(new Font("楷体",Font.BOLD,25));teach_course.setBounds(20,400,200,100);teachWin.add(teach_course);
        all_course.setFont(font);all_course.setBounds(200,365,150,100);teachWin.add(all_course);
        allow_sel_course.setFont(font);allow_sel_course.setBounds(200,415,150,100);teachWin.add(allow_sel_course);
        get_course.setFont(font);get_course.setBounds(200,465,150,100);teachWin.add(get_course);

        //输入框
        tId=new JComboBox<>();tId.setBounds(150,100,400,30);teachWin.add(tId);
        tName=new JTextField();tName.setBounds(150,145,400,30);teachWin.add(tName);
        tSex=new JTextField();tSex.setBounds(150,190,400,30);teachWin.add(tSex);
        tSalary=new JTextField();tSalary.setBounds(150,230,400,30);teachWin.add(tSalary);
        tPosition=new JTextField();tPosition.setBounds(150,280,400,30);teachWin.add(tPosition);
        tPwd=new JTextField();tPwd.setBounds(150,325,400,30);teachWin.add(tPwd);
        t_all_course=new JComboBox<String>();t_all_course.setBounds(320,400,400,30);teachWin.add(t_all_course);
        t_allow_sel_course=new JComboBox<String>();t_allow_sel_course.setBounds(320,450,400,30);teachWin.add(t_allow_sel_course);
        t_get_course=new JComboBox<String>();t_get_course.setBounds(320,500,400,30);teachWin.add(t_get_course);
        tId.setEditable(false);

        //按钮
        tModify=new JButton("修改");
        tModify.setBounds(580,180,60,80);
        teachWin.add(tModify);

        //显示个人信息
        tId.addItem(ID);
        String Name=sqlOperator.getOneReply("teacher","tname","tno",ID);
        tName.setText(Name);
        String Sex=sqlOperator.getOneReply("teacher","tsex","tno",ID);
        tSex.setText(Sex);
        String Postion=sqlOperator.getOneReply("teacher","tposition","tno",ID);
        tPosition.setText(Postion);
        String Salary=sqlOperator.getOneReply("teacher","tsalary","tno",ID);
        tSalary.setText(Salary);
        String Pwd=sqlOperator.getOneReply("teacher","tpwd","tno",ID);
        tPwd.setText(Pwd);

        //显示老师教师的课程的相关信息
        JLabel myCourse=new JLabel("授课信息");
        myCourse.setBounds(720,20,400,100);
        myCourse.setFont(font);
        teachWin.add(myCourse);
        showCourse();

        //获取课程信息
        get_all_course();
        get_allow_sel_course();
        get_selected_course();
        //统计教师信息
        teachInfoCount();
        //课程成绩统计
        courseInfoCount();
        //统计学生信息
        stuInfoCount();

        //教师选课
        getCourseInfo=new JButton("查询");
        selectCourse=new JButton("选择");
        deselectCourse=new JButton("退选");
        getCourseInfo.setBounds(750,400,60,30);
        selectCourse.setBounds(750,450,60,30);
        deselectCourse.setBounds(750,500,60,30);
        teachWin.add(getCourseInfo);
        teachWin.add(selectCourse);
        teachWin.add(deselectCourse);
        ButtonListener listener=new ButtonListener();
        getCourseInfo.addActionListener(listener);
        selectCourse.addActionListener(listener);  //添加触发器
        deselectCourse.addActionListener(listener);
        tModify.addActionListener(listener);

        teachWin.setVisible(true);
    }

    //==============================================================================
    //显示教师授课信息
    public static void showCourse() throws SQLException {
        String[] columnName={"课程号","课程名","学分"};
        Vector<String> vector=new Vector<>(Arrays.asList(columnName));
        String tno=(String)tId.getSelectedItem();
        ResultSet rs=sqlOperator.queryOneRecord("course","tno", tno);
        Vector<Vector<String>> all_course=new Vector<>();
        while(rs.next()){
            Vector<String> courseInfo=new Vector<>();
            String cno=rs.getString("cno");
            String cname=rs.getString("cname");
            String credit=rs.getString("ccredit");
            courseInfo.add(cno);
            courseInfo.add(cname);
            courseInfo.add(credit);
            all_course.add(courseInfo);
        }
        courseList=new JTable(all_course,vector);
        Font font=new Font("楷体",Font.BOLD,25);
        courseList.setFont(new Font("宋体",Font.BOLD,11));
        courseList.setRowHeight(22);
        //表格样式渲染
//        teachInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        teachInfoTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        courseList.getColumn("课程号").setCellRenderer(render);
        courseList.getColumn("课程名").setCellRenderer(render);
        courseList.getColumn("学分").setCellRenderer(render);;

//        courseList.setBounds(660,80,200,300);
        JScrollPane pane=new JScrollPane(courseList);
        pane.setBounds(660,100,200,280);
        teachWin.add(pane);
    }

    //获取所有课程
    public static void get_all_course() throws SQLException {  //录入新课程后刷新全部课程信息
        t_all_course.removeAllItems();
        java.util.List get_all_course_name=sqlOperator.GetOneAll("course","cname");
        for(int i=0;i<get_all_course_name.size();++i){
            t_all_course.addItem((String) get_all_course_name.get(i));
        }
        //设置已有教师的课程列表项不可选择，避免重复选课

    }
    //教师可选课程
    public static void get_allow_sel_course() throws SQLException {
        t_allow_sel_course.removeAllItems();
        ResultSet rs=sqlOperator.SqlCommon("select cname from course where tno is null");
        while(rs.next()){
            String cname=rs.getString(1);
            t_allow_sel_course.addItem(cname);
        }
    }

    //获取已选课程
    public static void get_selected_course() throws SQLException {  //录入新课程后刷新全部课程信息
        t_get_course.removeAllItems();
        String id= (String) tId.getSelectedItem();
        java.util.List<String> getSelecteCourse = new ArrayList<String>();
        getSelecteCourse= sqlOperator.getListReplyWithOneLimit("course","cname","tno",id);
        for(int i=0;i<getSelecteCourse.size();++i){
            t_get_course.addItem((String) getSelecteCourse.get(i));
        }
    }

    //=========信息统计==========
    //教师信息统计
    public static void teachInfoCount() throws SQLException {
        Vector<String> columnName=new Vector<>();
        columnName.add("职称");
        columnName.add("人数");
        columnName.add("平均工资");
        Vector<Vector<java.io.Serializable>> data=new Vector<Vector<java.io.Serializable>>();
        //查询每个职称的人数
        ResultSet rs=sqlOperator.SqlCommon("select tposition,count(tno),Avg(tsalary) from teacher where tno is not null group by tposition");
        while(rs.next()){
            Vector<java.io.Serializable> rowInfo=new Vector<java.io.Serializable>();
            rowInfo.add(rs.getString(1));
            rowInfo.add(rs.getInt(2));
            rowInfo.add(rs.getInt(3));
            data.add(rowInfo);
        }
        JTable teachInfoTable=new JTable(data,columnName);
        JScrollPane pane=new JScrollPane(teachInfoTable);
        pane.setBounds(30,600,200,200);

        Font font=new Font("楷体",Font.BOLD,25);
        teachInfoTable.setFont(new Font("宋体",Font.BOLD,14));
        teachInfoTable.setRowHeight(22);
        //表格样式渲染
//        teachInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        teachInfoTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        teachInfoTable.getColumn("职称").setCellRenderer(render);
        teachInfoTable.getColumn("人数").setCellRenderer(render);
        teachInfoTable.getColumn("平均工资").setCellRenderer(render);

        teachWin.add(pane);
        JLabel teachInfoCount=new JLabel("教师信息统计");
        teachInfoCount.setBounds(40,520,300,100);
        teachInfoCount.setFont(font);
        teachWin.add(teachInfoCount);
    }

    //课程成绩统计
    public static void courseInfoCount() throws SQLException {
        Vector<String> columnName=new Vector<>();
        columnName.add("课程名");
        columnName.add("平均分");
        columnName.add("最高分");
        columnName.add("最低分");
        Vector<Vector<java.io.Serializable>> data=new Vector<Vector<java.io.Serializable>>();
        for(int i=0;i<t_get_course.getItemCount();++i){  //对每一门课程在sc表中计算平均分、最高分、最低分
            String cname=t_get_course.getItemAt(i);  //获取该教师教授的课程名
            String cno=sqlOperator.getOneReply("course","cno","cname",cname);
            Vector<java.io.Serializable> rowInfo=new Vector<>();
            rowInfo.add(cname);
            ResultSet rs=sqlOperator.SqlCommon("select avg(grade),max(grade),min(grade) from sc where cno='"+cno+"'");
            while(rs.next()){
                int avgGrade=rs.getInt(1);
                int maxGrade=rs.getInt(2);
                int minGrade=rs.getInt(3);
                rowInfo.add(avgGrade);
                rowInfo.add(maxGrade);
                rowInfo.add(minGrade);
            }
            data.add(rowInfo);
        }
        JTable courseInfoTable=new JTable(data,columnName);
        JScrollPane pane=new JScrollPane(courseInfoTable);
        pane.setBounds(240,600,300,200);
        teachWin.add(pane);
        Font font=new Font("楷体",Font.BOLD,25);
        courseInfoTable.setFont(new Font("宋体",Font.BOLD,12));
        courseInfoTable.setRowHeight(22);
        //表格样式渲染
//        teachInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        teachInfoTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        courseInfoTable.getColumn("课程名").setCellRenderer(render);
        courseInfoTable.getColumn("平均分").setCellRenderer(render);
        courseInfoTable.getColumn("最高分").setCellRenderer(render);
        courseInfoTable.getColumn("最低分").setCellRenderer(render);

        JLabel courseInfoCount=new JLabel("课程成绩统计");  //只统计该教师教授的课程的课程
        courseInfoCount.setBounds(300,520,300,100);
        courseInfoCount.setFont(font);
        teachWin.add(courseInfoCount);
    }
    //学生学分统计
    public static void stuInfoCount() throws SQLException {
        Vector<String> columnName=new Vector<>();
        columnName.add("学号");
        columnName.add("姓名");
        columnName.add("总学分");
        Vector<Vector<Serializable>> data=new Vector<Vector<Serializable>>();
        ResultSet rs=sqlOperator.SqlCommon("select sno,sum(ccredit) from course,sc where course.cno=sc.cno group by sc.sno");
        while(rs.next()){
            Vector<Serializable> rowInfo=new Vector<Serializable>();
            String sno=rs.getString(1);
            int sumCredit=rs.getInt(2);
            String sname=sqlOperator.getOneReply("student","sname","sno",sno);
            rowInfo.add(sno);
            rowInfo.add(sname);
            rowInfo.add(sumCredit);
            data.add(rowInfo);
        }
        //统计未选课的学生,设置学分为0，添加到表格中
        ResultSet rs2=sqlOperator.SqlCommon("select sno from student where sno not in (select sno from sc where sno is not null)");
        while(rs2.next()){
            String sno=rs2.getString(1);
            String sname=sqlOperator.getOneReply("student","sname","sno",sno);
            int credit=0;
            Vector<Serializable> rowInfo=new Vector<Serializable>();
            rowInfo.add(sno);
            rowInfo.add(sname);
            rowInfo.add(credit);
            data.add(rowInfo);
        }

        JTable stuInfoTable=new JTable(data,columnName);
        JScrollPane pane=new JScrollPane(stuInfoTable);
        pane.setBounds(550,600,300,200);
        teachWin.add(pane);
        Font font=new Font("楷体",Font.BOLD,25);
        stuInfoTable.setFont(new Font("宋体",Font.BOLD,14));
        stuInfoTable.setRowHeight(22);

        //表格样式渲染
//        teachInfoTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        teachInfoTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        DefaultTableCellRenderer render = new DefaultTableCellRenderer();
        render.setHorizontalAlignment(SwingConstants.CENTER);
        stuInfoTable.getColumn("学号").setCellRenderer(render);
        stuInfoTable.getColumn("姓名").setCellRenderer(render);
        stuInfoTable.getColumn("总学分").setCellRenderer(render);
        //按学号排序？？？
//        TableModel model = new DefaultTableModel(data,columnName);
//        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
//        sorter.convertRowIndexToModel(1);
//        stuInfoTable.setRowSorter(sorter);

        JLabel stuInfoCount=new JLabel("课程成绩统计");  //只统计该教师教授的课程的课程
        stuInfoCount.setBounds(625,520,300,100);
        stuInfoCount.setFont(font);
        teachWin.add(stuInfoCount);
    }

    //触发器(选课、退课)
    public static class ButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("选择")){
                String cname= (String) t_allow_sel_course.getSelectedItem();
//                System.out.println("");
                try {
                    //判断选择的课程是否有其他老师选了
                    String tno= (String) tId.getSelectedItem();
                    String courseTeacher=sqlOperator.getOneReply("course","tno","cname",cname);
                    if(courseTeacher==null){  //当此课程没有授课教师，则可以选择
                        sqlOperator.SqlCommon("update course set tno='"+tno+"' where cname='"+cname+"'");
                        sqlOperator.SqlCommon("commit");
                        get_selected_course();
                        InfoSysMain.Tips("选课成功！");
                        showCourse();  //更新授课信息
                        get_allow_sel_course();
                        courseInfoCount();
                    }
                    else {
                        InfoSysMain.Tips("已有授课教师，选择失败！");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


            }
            else if (e.getActionCommand().equals("退选")){
                String cname= (String) t_get_course.getSelectedItem();
                try {
                    sqlOperator.SqlCommon("update course set tno=null where cname='"+cname+"'");
                    sqlOperator.SqlCommon("commit");
                    InfoSysMain.Tips("退选成功！");
                    get_selected_course();
                    showCourse();  //更新授课信息
                    get_allow_sel_course();
                    courseInfoCount();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                };
            }
            else if(e.getActionCommand().equals("查询")){
                String cname= (String) t_all_course.getSelectedItem();
                try {
                    ResultSet rs=sqlOperator.SqlCommon("select cno,tno,ccredit from course where cname='"+cname+"'");
                    Vector<String> columnName=new Vector<>();
                    columnName.add("课程号");
                    columnName.add("课程名");
                    columnName.add("学分");
                    columnName.add("任课教师");
                    columnName.add("教师职称");
                    columnName.add("选课人数");
                    Vector<Vector<Object>> data=new Vector<Vector<Object>>();
                    Vector<Object> rowInfo=new Vector<>();
                    while(rs.next()){
                        String cno=rs.getString(1);rowInfo.add(cno);rowInfo.add(cname);
                        String ccredit=rs.getString(3);rowInfo.add(ccredit);
                        String tno=rs.getString(2);
                        ResultSet rs2=sqlOperator.SqlCommon("select tname,tposition from teacher where tno='"+tno+"'");
                        while(rs2.next()){
                            String tname=rs2.getString(1);rowInfo.add(tname);
                            String tpostion=rs2.getString(2);rowInfo.add(tpostion);
                        }
                        int selStuCount=0;
                        ResultSet rs3=sqlOperator.SqlCommon("select count(sno) from sc where cno='"+cno+"'");
                        while(rs3.next()){
                            selStuCount=rs3.getInt(1);
                        }
                        rowInfo.add(selStuCount);
                        data.add(rowInfo);
                        JTable table=new JTable(data,columnName);
                        table.setBounds(0,0,400,150);
                        table.setRowHeight(22);
                        table.setFont(new Font("宋体",Font.BOLD,18));
                        JScrollPane pane=new JScrollPane(table);
                        pane.setBounds(0,0,400,150);
                        JFrame courseDetailWin=new JFrame("课程详情");
//                        courseDetailWin.setLayout(new GridLayout(1,1));

//                        courseDetailWin.add(table);
                        courseDetailWin.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        courseDetailWin.setSize(600,95);
                        courseDetailWin.setLocationRelativeTo(null);;
                        courseDetailWin.add(pane);
                        courseDetailWin.setVisible(true);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("修改")){
                String id= (String) tId.getSelectedItem();
                String name=tName.getText();
                String sex=tSex.getText();
                String salary=tSalary.getText();
                String position=tPosition.getText();
                String pwd=tPwd.getText();
                if(id.isEmpty()||name.isEmpty()||sex.isEmpty()||salary.isEmpty()||position.isEmpty()){
                    InfoSysMain.Tips("输入信息不完整，请补全信息后再添加！");
                }
                else{
                    try {
                        sqlOperator.SqlCommon("update teacher set tname='"+name+"' where tno='"+id+"'");
                        sqlOperator.SqlCommon("update teacher set tsex='"+sex+"' where tno='"+id+"'");
                        sqlOperator.SqlCommon("update teacher set tsalary='"+salary+"' where tno='"+id+"'");
                        sqlOperator.SqlCommon("update teacher set tposition='"+position+"' where tno='"+id+"'");
                        sqlOperator.SqlCommon("update teacher set tpwd='"+pwd+"' where tno='"+id+"'");
                        InfoSysMain.Tips("修改成功！");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }


}

