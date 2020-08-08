package com;

import com.sun.javaws.util.JfxHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


//=============================功能选择窗口====================================
public class adminSys {

    static JFrame frame;
    static JButton stuButton;
    static JButton teaButton;
    static JButton pwdButton;
    public static void main(String[] args) throws SQLException {
        adminWin();
//        teacherManage();
    }

    public static void adminWin(){
        frame = new JFrame("管理员信息管理系统");
        frame.setSize(800, 400);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        JPanel panel = new JPanel();
//        frame.setLayout(new GridLayout(4,3));
        frame.add(panel);  //添加面板
        panel.setLayout(null);

        JLabel welcome=new JLabel("欢迎使用管理员信息管理系统");
        welcome.setBounds(200,20,600,100);
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        panel.add(welcome);

        stuButton=new JButton("学生信息管理");
        teaButton=new JButton("教师信息管理");
        pwdButton=new JButton("修改密码");
        stuButton.setBounds(50,150,200,100);
        teaButton.setBounds(300,150,200,100);
        pwdButton.setBounds(550,150,200,100);
        Font font=new Font("楷体",Font.BOLD,22);
        stuButton.setFont(font);
        teaButton.setFont(font);
        pwdButton.setFont(font);
        panel.add(stuButton);
        panel.add(teaButton);
        panel.add(pwdButton);
        ButtonListener choose_button =new ButtonListener();
        stuButton.addActionListener(choose_button);
        teaButton.addActionListener(choose_button);
        pwdButton.addActionListener(choose_button);
        frame.setVisible(true);
    }

    static class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("学生信息管理")){
                try {
                    studentManage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("教师信息管理")){
                try {
                    teacherManage();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if (e.getActionCommand().equals("修改密码")){
                modifyPwd();
            }
            else System.out.println("Error!");
        }
    }
//===========================学生信息管理===============================================
    static JFrame stuWin;
    static JButton stuAdd;
    static JButton stuModify;
    static JButton stuRemove;
    static JButton stuRecord;
    static JButton stuQuery;
    static JButton stuSetPwd;
    static JComboBox<String> selStuID;
    static JTextField stuID;
    static JTextField stuName;
    static JTextField stuSex;
    static JTextField stuAge;
    static JTextField stuPwd;
    static JComboBox<String> stuCourse;
    static JTextField stuGrade;
    public static void studentManage() throws SQLException {
        stuWin=new JFrame("欢迎使用学生信息管理系统");
        stuWin.setSize(900,800);
        stuWin.setLocationRelativeTo(null);
//        stuWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stuWin.setLayout(null);
        //标签
        JLabel welcome=new JLabel("欢迎使用学生信息管理系统");
        JLabel all_stu_id=new JLabel("所有学生学号");
        JLabel id=new JLabel("学号");
        JLabel name=new JLabel("姓名");
        JLabel sex=new JLabel("性别");
        JLabel age=new JLabel("年龄");
        JLabel pwd=new JLabel("密码");
        JLabel course=new JLabel("课程");
        JLabel grade=new JLabel("成绩");
        JLabel record=new JLabel("成绩录入");
        Font font=new Font("楷体",Font.BOLD,20);
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        welcome.setBounds(200,0,400,100);
        all_stu_id.setBounds(25,55,100,100);
        id.setFont(font);id.setBounds(50,115,100,100);
        name.setFont(font);name.setBounds(50,175,100,100);
        sex.setFont(font);sex.setBounds(50,235,100,100);
        age.setFont(font);age.setBounds(50,295,100,100);
        pwd.setFont(font);pwd.setBounds(50,355,100,100);
        course.setFont(font);course.setBounds(200,500,100,100);
        grade.setFont(font);grade.setBounds(200,600,100,100);
        record.setFont(font);record.setBounds(50,550,100,100);

        stuWin.add(welcome);
        stuWin.add(all_stu_id);
        stuWin.add(id);
        stuWin.add(name);
        stuWin.add(sex);
        stuWin.add(age);
        stuWin.add(pwd);
        stuWin.add(course);
        stuWin.add(grade);
        stuWin.add(record);

        //输入框
        stuID=new JTextField();
        stuName=new JTextField();
        stuSex=new JTextField();
        stuAge=new JTextField();
        stuPwd=new JTextField();

        //获取所有学生的学号，加入输入框
        selStuID=new JComboBox<String>();
        selStuID.setBounds(150,90,400,30);

        stuID.setBounds(150,150,400,30);
        stuName.setBounds(150,210,400,30);
        stuSex.setBounds(150,270,400,30);
        stuAge.setBounds(150,330,400,30);
        stuPwd.setBounds(150,390,400,30);

        stuWin.add(selStuID);
        stuWin.add(stuID);
        stuWin.add(stuName);
        stuWin.add(stuSex);
        stuWin.add(stuAge);
        stuWin.add(stuPwd);


        stuCourse=new JComboBox<>();
        stuCourse.setBounds(250,530,400,30);
        stuGrade=new JTextField();
        stuGrade.setBounds(250,630,400,30);

        //课程下拉框触发器，但课程改变，自动获得成绩
        stuCourse.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                //自动刷新成绩
                reflashGrade();
                System.out.println("执行课程下拉框触发器！");
            }
        });
        stuWin.add(stuCourse);
        stuWin.add(stuGrade);

        //---------学号下拉框触发器
        ItemListener itemListener= e -> {  //学号下拉框的匿名触发器，当学号选择改变时，触发获得该学生选择的所有
            String now= (String) selStuID.getSelectedItem();
            try {
                List all_course=sqlOperator.GetCourse(now);
                stuCourse.removeAllItems();
                for(int i=0;i<all_course.size();++i)
                {
                    stuCourse.addItem((String) all_course.get(i));
                }

                stuID.setText("");
                stuName.setText("");
                stuSex.setText("");
                stuAge.setText("");
                stuPwd.setText("");
                stuGrade.setText("");
            } catch (SQLException  ex) {
                ex.printStackTrace();
            }
        };
        selStuID.addItemListener(itemListener);

        //获取所有学生的学号，加入输入框；放在item触发器后面，以便一开始能够就能够自动获得课程名
        List all_id=sqlOperator.GetOneAll("student","sno");
        for(int i=0;i<all_id.size();++i){
            selStuID.addItem((String) all_id.get(i));
            System.out.println(all_id.get(i));
            reflashGrade();
        }

        //按钮
        stuAdd=new JButton("添加");
        stuModify=new JButton("修改");
        stuRemove=new JButton("删除");
        stuRecord=new JButton("录入");
        stuQuery=new JButton("查询");
        stuSetPwd=new JButton("设置初始密码");
//        stuSetPwd.setFont(new Font("楷体",Font.BOLD,10));

        stuAdd.setBounds(600,150,120,50);
        stuModify.setBounds(600,220,120,50);
        stuRemove.setBounds(600,290,120,50);
        stuRecord.setBounds(700,580,120,50);
        stuQuery.setBounds(750,200,80,100);
        stuSetPwd.setBounds(600,360,120,50);

        stuWin.add(stuAdd);
        stuWin.add(stuModify);
        stuWin.add(stuRemove);
        stuWin.add(stuRecord);
        stuWin.add(stuQuery);
        stuWin.add(stuSetPwd);

        ButtonListener2 buttonListener=new ButtonListener2();
        stuAdd.addActionListener(buttonListener);
        stuModify.addActionListener(buttonListener);
        stuRemove.addActionListener(buttonListener);
        stuRecord.addActionListener(buttonListener);
        stuQuery.addActionListener(buttonListener);
        stuSetPwd.addActionListener(buttonListener);

        stuWin.setVisible(true);
    }

//=============================教师信息管理=============================================
    static JFrame teachWin;
    static JComboBox sel_tId;
    static JTextField tId;
    static JTextField tName;
    static JTextField tSex;
    static JTextField tSalary;
    static JTextField tPosition;
    static JTextField tPwd;

    static JComboBox t_all_course;  //显示course系统的所有课程
    static JComboBox t_get_course;  //已选课程
    static JTextField t_add_course;  //手动输入课程名添加课程
    static JTextField t_add_course_id;  //设置添加课程的id
    static JTextField t_course_credit;  //设置添加课程的学分

    //按钮
    static JButton tAdd;
    static JButton tModify;
    static JButton tRemove;
    static JButton tRecord;
    static JButton tQuery;
    static JButton tSetPwd;

    static JComboBox tCourse;
    public static void teacherManage() throws SQLException {
        teachWin=new JFrame("欢迎使用教师信息管理系统");
        teachWin.setSize(900,880);
        teachWin.setLocationRelativeTo(null);
//        teachWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        teachWin.setLayout(null);
        //标签
        JLabel welcome=new JLabel("欢迎使用教师信息管理系统");
        JLabel all_tea_id=new JLabel("所有教师职工号");
        JLabel id = new JLabel("职工号");
        JLabel name = new JLabel("姓名");
        JLabel sex = new JLabel("性别");
        JLabel salary = new JLabel("薪水");
        JLabel position = new JLabel("职称");
        JLabel pwd = new JLabel("密码");
        JLabel all_course = new JLabel("所有课程");
        JLabel get_course=new JLabel("已选课程");
        JLabel add_course_id=new JLabel("添加课程id");
        JLabel add_course=new JLabel("添加课程名");
        JLabel add_course_credit=new JLabel("添加课程学分");
        Font font=new Font("楷体",Font.BOLD,20);
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        welcome.setBounds(200,0,400,100);teachWin.add(welcome);
        all_tea_id.setBounds(15,55,100,100);teachWin.add(all_tea_id);
        id.setFont(font);id.setBounds(45,115,100,100);teachWin.add(id);
        name.setFont(font);name.setBounds(50,175,100,100);teachWin.add(name);
        sex.setFont(font);sex.setBounds(50,235,100,100);teachWin.add(sex);
        salary.setFont(font);salary.setBounds(50,295,100,100);teachWin.add(salary);
        position.setFont(font);position.setBounds(50,355,100,100);teachWin.add(position);
        pwd.setFont(font);pwd.setBounds(50,415,100,100);teachWin.add(pwd);

        JLabel record=new JLabel("课程信息管理");
        record.setFont(font);record.setBounds(20,620,150,100);teachWin.add(record);
        all_course.setFont(font);all_course.setBounds(200,520,150,100);teachWin.add(all_course);
        get_course.setFont(font);get_course.setBounds(200,580,150,100);teachWin.add(get_course);
        add_course_id.setFont(font);add_course_id.setBounds(180,640,150,100);teachWin.add(add_course_id);
        add_course.setFont(font);add_course.setBounds(180,680,150,100);teachWin.add(add_course);
        add_course_credit.setFont(font);add_course_credit.setBounds(180,720,150,100);teachWin.add(add_course_credit);

        //输入框
        sel_tId=new JComboBox();sel_tId.setBounds(150,90,400,30);teachWin.add(sel_tId);
        tId=new JTextField();tId.setBounds(150,150,400,30);teachWin.add(tId);
        tName=new JTextField();tName.setBounds(150,210,400,30);teachWin.add(tName);
        tSex=new JTextField();tSex.setBounds(150,270,400,30);teachWin.add(tSex);
        tSalary=new JTextField();tSalary.setBounds(150,330,400,30);teachWin.add(tSalary);
        tPosition=new JTextField();tPosition.setBounds(150,390,400,30);teachWin.add(tPosition);
        tPwd=new JTextField();tPwd.setBounds(150,450,400,30);teachWin.add(tPwd);
        t_all_course=new JComboBox();t_all_course.setBounds(320,550,400,30);teachWin.add(t_all_course);
        t_get_course=new JComboBox();t_get_course.setBounds(320,610,400,30);teachWin.add(t_get_course);
        t_add_course_id=new JTextField();t_add_course_id.setBounds(320,670,400,30);teachWin.add(t_add_course_id);
        t_add_course=new JTextField();t_add_course.setBounds(320,710,400,30);teachWin.add(t_add_course);
        t_course_credit=new JTextField();t_course_credit.setBounds(320,750,400,30);teachWin.add(t_course_credit);

        //按钮
        tAdd=new JButton("添加");tAdd.setBounds(590,150,120,50);teachWin.add(tAdd);
        tModify=new JButton("修改");tModify.setBounds(590,220,120,50);teachWin.add(tModify);
        tRemove=new JButton("删除");tRemove.setBounds(590,290,120,50);teachWin.add(tRemove);
        tRecord=new JButton("录入");tRecord.setBounds(750,700,100,50);teachWin.add(tRecord);
        tQuery=new JButton("查询");tQuery.setBounds(750,200,80,100);teachWin.add(tQuery);
        tSetPwd=new JButton("设置初始密码");teachWin.add(tSetPwd);
//        tSetPwd.setFont(new Font("楷体",Font.BOLD,14));
        tSetPwd.setBounds(590,360,120,50);
        ButtonListener3 buttonListener=new ButtonListener3();
        tAdd.addActionListener(buttonListener);
        tModify.addActionListener(buttonListener);
        tRemove.addActionListener(buttonListener);
        tRecord.addActionListener(buttonListener);
        tQuery.addActionListener(buttonListener);
        tSetPwd.addActionListener(buttonListener);

        //添加所选课程触发器
        t_get_course.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                System.out.println("执行所有课程触发器");
            }
        });


        //职工号选择下拉框触发器
        sel_tId.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                t_get_course.removeAllItems();
                String now= (String) sel_tId.getSelectedItem();
                try {
                    List all_get_course=sqlOperator.getListReplyWithOneLimit("course","cname","tno",now);
                    t_get_course.removeAllItems();
                    for(int i=0;i<all_get_course.size();++i)
                    {
                        t_get_course.addItem((String) all_get_course.get(i));
                    }

                    tId.setText("");
                    tName.setText("");
                    tSex.setText("");
                    tSalary.setText("");
                    tPosition.setText("");
                    tPwd.setText("");
                } catch (SQLException  ex) {
                    ex.printStackTrace();
                }
            }
        });


        //获取所有课程名：
        List get_all_course_name=sqlOperator.GetOneAll("course","cname");
        for(int i=0;i<get_all_course_name.size();++i){
            t_all_course.addItem(get_all_course_name.get(i));
        }


        //获取所有教职工号，加入输入框；放在触发器后面
        List all_id=sqlOperator.GetOneAll("teacher","tno");
        for(int i=0;i<all_id.size();++i){
            sel_tId.addItem((String) all_id.get(i));
            System.out.println(all_id.get(i));
//            reflashGrade();
        }

        //教师授课注意：一个老师可以选多门课，但一门课只能由一位老师任课

        JLabel tips=new JLabel("说明：添加课程指向数据库中新增该课程，之后，教师可以在教师系统中选择该课程进行授课");
        tips.setFont(new Font("楷体",Font.BOLD,14));
        tips.setBounds(200,700,800,200);
        teachWin.add(tips);
        teachWin.setVisible(true);
    }

//================================修改密码==============================================
    static JPasswordField passwordText;
    static JPasswordField passwordText2;
    static JFrame modifyPwdWin;
    public static void modifyPwd(){

        modifyPwdWin = new JFrame("修改管理员密码");
        modifyPwdWin.setSize(650, 300);
//        modifyPwdWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        modifyPwdWin.setLocationRelativeTo(null);  //居中显示
        JPanel panel = new JPanel();
        modifyPwdWin.add(panel);  //添加面板
        panel.setLayout(null);
        //新密码
        JLabel pwdLabel = new JLabel("新密码:");
        pwdLabel.setBounds(10,20,300,100);
        panel.add(pwdLabel);
        pwdLabel.setFont(new Font("楷体",Font.BOLD,20));

        passwordText = new JPasswordField(20);
        passwordText.setBounds(150,60,300,30);
        panel.add(passwordText);

        //确认新密码
        JLabel pwdSure = new JLabel("确认新密码:");
        pwdSure.setBounds(10,95,300,100);
        panel.add(pwdSure);
        pwdSure.setFont(new Font("楷体",Font.BOLD,20));

        passwordText2= new JPasswordField(20);
        passwordText2.setBounds(150,130,300,30);
        panel.add(passwordText2);

        JButton loginButton = new JButton("确认修改密码");
        loginButton.setBounds(500, 90, 120, 50);
        panel.add(loginButton);

        ButtonListener2 buttonListener=new ButtonListener2();
        loginButton.addActionListener(buttonListener);

        modifyPwdWin.setVisible(true);

    }
//===============管理员、学生管理系统的按钮监听器===================
    static class ButtonListener2 implements ActionListener {  //修改密码和学生信息管理的button监听器
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("确认修改密码")) {  // JButton的action command默认是JButton上显示的文本
                char[] pwd1=passwordText.getPassword();
                String pw1toStr=new String(pwd1);
                char[] pwd2=passwordText2.getPassword();
                if(Arrays.equals(pwd1, pwd2)){  //实际修改密码的操作还未实现！
                    System.out.println("新密码为："+ Arrays.toString(pwd1));
                    if(pw1toStr.isEmpty()) InfoSysMain.Tips("请输入密码！");
                    else{
                        //修改成功
                        InfoSysMain.Tips("密码修改成功！");
                        try {
                            sqlOperator.SqlCommon(new String("update admin_users set admin_pwd='"+pw1toStr+"' where admin_id='admin'"));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        modifyPwdWin.setVisible(false);
                    }
                }
                else{
                    InfoSysMain.Tips("两次密码输入不同！");
                }
            }
            else if (e.getActionCommand().equals("添加")){
                //数据写入数据库
                String id=stuID.getText();
                String name=stuName.getText();
                String sex=stuSex.getText();
                String age=stuAge.getText();
                String pwd=stuPwd.getText();
                if(id.isEmpty()||name.isEmpty()||sex.isEmpty()||age.isEmpty()){
                    InfoSysMain.Tips("输入信息不完整，请补全信息后再添加！");
                }
                else{
                    if(!pwd.isEmpty()){  //管理员有手动为学生设置密码，则以输入的密码为准
                        try {
                            sqlOperator.SqlCommon("insert into student(sno ,sname,ssex, sage,spwd) " +
                                    "values('"+id+"','"+name+"','"+sex+"','"+age+"','"+pwd+"')");
                            InfoSysMain.Tips("信息录入成功！");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else{  //没有设置密码，自动设为学号
                        try {
                            sqlOperator.SqlCommon("insert into student(sno ,sname,ssex, sage,spwd) " +
                                    "values('"+id+"','"+name+"','"+sex+"','"+age+"','"+id+"')");
                            InfoSysMain.Tips("信息录入成功！");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        reflashStuID();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            else if(e.getActionCommand().equals("删除")){
                //delete from XXX where id==XXX;
                String id= (String) selStuID.getSelectedItem();
                try {
                    sqlOperator.SqlCommon("delete from student where sno='"+id+"'");
                    sqlOperator.SqlCommon("commit");
                    InfoSysMain.Tips("删除成功！");
                    reflashStuID();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("修改")){
                String id=stuID.getText();
                String id2= (String) selStuID.getSelectedItem();
                if(!id.equals(id2)){
                    InfoSysMain.Tips("学号不允许修改！");
                }
                else{
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
                            InfoSysMain.Tips("修改成功！");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                }

                //update
            }
            else if(e.getActionCommand().equals("录入")){
                //update grade
                String grade=stuGrade.getText();
                String id= (String) selStuID.getSelectedItem();
                String cno=null;
                String course= (String) stuCourse.getSelectedItem();
                try {
                    cno=sqlOperator.getOneReply("course","cno","cname",course);
                    sqlOperator.SqlCommon("update sc set grade="+grade+" where sno='"+id+"' and cno='"+cno+"'" );
                    InfoSysMain.Tips("成绩录入成功！");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("查询")){
                String id=(String) selStuID.getSelectedItem();
                stuID.setText(id);
                String name = null,sex = null,age = null,pwd=null;
                try {
                    name=sqlOperator.getOneReply("student","sname","sno",id);
                    sex=sqlOperator.getOneReply("student","ssex","sno",id);
                    age=sqlOperator.getOneReply("student","sage","sno",id);
                    pwd=sqlOperator.getOneReply("student","spwd","sno",id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                stuName.setText(name);
                stuAge.setText(age);
                stuSex.setText(sex);
                stuPwd.setText(pwd);

                //获取成绩
                String cno=null;
                String course= (String) stuCourse.getSelectedItem();
                try {
                    cno=sqlOperator.getOneReply("course","cno","cname",course);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String grade=null;
                try {
                    grade=sqlOperator.getOneReplyWithTwoLimit("sc","grade","sno",id,"cno",cno);
                    stuGrade.setText(grade);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("设置初始密码")){
                try {
                    sqlOperator.SqlCommon("update student set spwd=sno where spwd is null");
                    InfoSysMain.Tips("初始化学生密码成功！");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    //===============教师管理系统的按钮监听器===================
    static class ButtonListener3 implements ActionListener {  //修改密码和学生信息管理的button监听器
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("添加")){
                //数据写入数据库
                String id=tId.getText();
                String name=tName.getText();
                String sex=tSex.getText();
                String salary=tSalary.getText();
                String position=tPosition.getText();
                String pwd=tPwd.getText();
                if(id.isEmpty()||name.isEmpty()||sex.isEmpty()||salary.isEmpty()||position.isEmpty()){
                    InfoSysMain.Tips("输入信息不完整，请补全信息后再添加！");
                }
                else{
                    if(!pwd.isEmpty()){  //管理员有手动为教师设置密码，则以输入的密码为准
                        try {
                            sqlOperator.SqlCommon("insert into teacher(tno ,tname,tsex, tsalary,tposition,tpwd) " +
                                    "values('"+id+"','"+name+"','"+sex+"','"+salary+"','"+position+"','"+pwd+"')");
                            InfoSysMain.Tips("信息录入成功！");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else{  //没有设置密码，自动设为学号
                        try {
                            sqlOperator.SqlCommon("insert into teacher(tno ,tname,tsex, tsalary,tposition,tpwd) " +
                                    "values('"+id+"','"+name+"','"+sex+"','"+salary+"','"+position+"','"+id+"')");
                            InfoSysMain.Tips("信息录入成功！");
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    try {
                        reflash_all_teacher_id();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
            else if(e.getActionCommand().equals("删除")){
                //delete from XXX where id==XXX;
                String id= (String) sel_tId.getSelectedItem();
                try {
                    sqlOperator.SqlCommon("delete from teacher where tno='"+id+"'");
                    sqlOperator.SqlCommon("commit");
                    InfoSysMain.Tips("删除成功！");
                    reflash_all_teacher_id();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("修改")){
                String id=tId.getText();
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
                //update
            }
            else if(e.getActionCommand().equals("录入")){
                //录入新课程
                String id=t_add_course_id.getText();
                String course=t_add_course.getText();
                String credit=t_course_credit.getText();
                try {
                    sqlOperator.SqlCommon("insert into course(cno,cname,ccredit) values('"+id+"','"+course+"',"+credit+")");
                    //录入新课程后，刷新所有课程
                    reflash_all_course();
                    InfoSysMain.Tips("新课程添加成功！");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("查询")){
                String id=(String) sel_tId.getSelectedItem();
                tId.setText(id);
                String name = null,sex = null,age = null,pwd=null,salary=null,postion=null;
                try {
                    name=sqlOperator.getOneReply("teacher","tname","tno",id);
                    sex=sqlOperator.getOneReply("teacher","tsex","tno",id);
                    salary=sqlOperator.getOneReply("teacher","tsalary","tno",id);
                    postion=sqlOperator.getOneReply("teacher","tposition","tno",id);
                    pwd=sqlOperator.getOneReply("teacher","tpwd","tno",id);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                tName.setText(name);
                tSex.setText(sex);
                tSalary.setText(salary);
                tPosition.setText(postion);
                tPwd.setText(pwd);
            }
            else if(e.getActionCommand().equals("设置初始密码")){
                try {
                    sqlOperator.SqlCommon("update teacher set tpwd=tno where tpwd is null");
                    InfoSysMain.Tips("初始化教师密码成功！");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }



    public static void reflashStuID() throws SQLException {
        selStuID.removeAllItems();
        List all_id=sqlOperator.GetOneAll("student","sno");
        for(int i=0;i<all_id.size();++i){
            selStuID.addItem((String) all_id.get(i));
            System.out.println(all_id.get(i));
        }
    }
    public  static void reflashGrade(){  //刷新课程成绩
        String cno=null;
        String course= (String) stuCourse.getSelectedItem();
        try {
            cno=sqlOperator.getOneReply("course","cno","cname",course);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String grade=null;
        String sno= (String) selStuID.getSelectedItem();
        try {
            grade=sqlOperator.getOneReplyWithTwoLimit("sc","grade","sno",sno,"cno",cno);
            stuGrade.setText(grade);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static  void reflash_all_teacher_id() throws SQLException {
        //获取所有教职工号，加入输入框；放在触发器后面
        sel_tId.removeAllItems();
        List all_id=sqlOperator.GetOneAll("teacher","tno");
        for(int i=0;i<all_id.size();++i){
            sel_tId.addItem((String) all_id.get(i));
            System.out.println(all_id.get(i));
        }
    }

    public static void reflash_all_course() throws SQLException {  //录入新课程后刷新全部课程信息
        t_all_course.removeAllItems();
        List get_all_course_name=sqlOperator.GetOneAll("course","cname");
        for(int i=0;i<get_all_course_name.size();++i){
            t_all_course.addItem(get_all_course_name.get(i));
        }
    }

}
