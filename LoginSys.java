package com;
import com.adminSys;
import com.studentSys;
import com.teacherSys;

import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.sqlOperator;

public class LoginSys{
    private static JTextField userText;
    private static JPasswordField passwordText;
    JFrame jFrame;
//    static JFrame adminFrame;  //登录成功后，adminFrame作为参数传给管理员系统界面
//    static JFrame studentFrame;
//    static JFrame teacherFrame;
    static String SysType="学生";


    public static void main(String[] args) throws SQLException {
        SysType="管理员";
        sqlOperator loginAuth=new sqlOperator();
//        loginAuth.Auth("T005","T005","教师");
//        if (loginAuth.Auth("T005","T005",SysType)) System.out.println("认证通过！");
//        else  System.out.println("账号或密码输入有误！");
        LoginWin(SysType);
    }

    public static void ErrorWarning(){
        JFrame win;
        win=new JFrame("Error!");
        win.setSize(300,200);
        win.setLocationRelativeTo(null);
//        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField warning=new JTextField("账号或密码有误！");
        warning.setFont(new Font("楷体",Font.BOLD,20));
        win.add(warning);
        win.setVisible(true);
    }
    static JFrame frame;
    public static void LoginWin(String title) throws SQLException {
        frame = new JFrame(title+"登录");
        SysType=title;
        frame.setSize(580, 260);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  //居中显示
        frame.setResizable(false);  //设置窗口不可缩放
        JPanel panel = new JPanel();
        frame.add(panel);  //添加面板
        panel.setLayout(null);
        //账号
        JLabel userLabel = new JLabel("账号");
        userLabel.setBounds(10,20,300,100);
        panel.add(userLabel);
        userLabel.setFont(new Font("楷体",Font.BOLD,30));

        userText = new JTextField(20);
        userText.setBounds(100,60,300,30);
        panel.add(userText);

        //密码
        JLabel passwordLabel = new JLabel("密码");
        passwordLabel.setBounds(10,95,300,100);
        panel.add(passwordLabel);
        passwordLabel.setFont(new Font("楷体",Font.BOLD,30));

        passwordText = new JPasswordField(20);
        passwordText.setBounds(100,130,300,30);
        panel.add(passwordText);

        JButton loginButton = new JButton("登录");
        loginButton.setBounds(420, 90, 120, 50);
        panel.add(loginButton);

        ButtonListener buttonListener=new ButtonListener();
        loginButton.addActionListener(buttonListener);

        frame.setVisible(true);
    }

    static class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("登录")) {  // JButton的action command默认是JButton上显示的文本
                System.out.println("Button Up is clicked");
                String getUser=userText.getText();
                String getPwd=new String(passwordText.getPassword());
                System.out.println("user="+getUser);
                System.out.println("pwd="+getPwd);
                //认证
               {
                    if(SysType.equals("学生")||SysType.equals("教师")){
                        sqlOperator loginAuth=new sqlOperator();
                        try {
                            if (loginAuth.Auth(getUser,getPwd,SysType)) {
                                System.out.println("认证通过！");
                                frame.setVisible(false);
                                if(SysType.equals("学生")) studentSys.studentWin(getUser);
                                else teacherSys.teacherWin(getUser);
                                return;
                            }
                            else  {
                                System.out.println("账号或密码输入有误！");  //需要改成弹窗
                                ErrorWarning();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    else if(SysType.equals("管理员")){
                        String id=getUser;
                        String pwd=getPwd;
                        try{
                            String getpwd=sqlOperator.getOneReply("admin_users","admin_pwd","admin_id",id);
                            System.out.println("数据库中查询到的密码为："+getpwd);
                            if(getpwd!=null&&getpwd.equals(pwd)) {
                                System.out.println("认证通过");
                                frame.setVisible(false);
                                adminSys.adminWin();
                            }
                            else {
                                InfoSysMain.Tips("账号或密码有误！");
                            }
                        } catch (SQLException ex) {
//                            InfoSysMain.Tips("账号不存在！");
                            ex.printStackTrace();
                        }
//                        if(getUser.equals("admin")&&getPwd.equals("admin")){  //管理员账号密码后续得设置一个变量保存
//                            System.out.println("认证通过");
//                            adminSys.adminWin();
//                            return;
//                        }
//                        else{
//                            System.out.println("账号或密码输入有误！");
//                            ErrorWarning();
//                        }
                    }
                    else System.out.println("找不到对应的类型！");
                }

            }else {
                System.out.println("Error");
            }
        }
    }
}
