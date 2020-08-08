package com;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class InfoSysMain {
    static JButton adminButton;
    static JButton studentButton;
    static JButton teacherButton;


    public static void main(String[] args) {
        initWin();
        //initWin2();
    }

    static void initWin(){  //第一种界面
        JFrame frame = new JFrame("欢迎使用校园信息管理系统");
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  //设置窗口居中显示
        frame.setResizable(false);
        JPanel panel = new JPanel();
//        frame.setLayout(new GridLayout(4,3));
        frame.add(panel);  //添加面板
        panel.setLayout(null);

        JLabel welcome=new JLabel("欢迎使用校园信息管理系统");
        welcome.setBounds(200,20,600,100);
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        panel.add(welcome);

        adminButton=new JButton("管理员系统");
        studentButton=new JButton("学生信息系统");
        teacherButton=new JButton("教师信息系统");
        adminButton.setBounds(50,150,200,100);
        studentButton.setBounds(300,150,200,100);
        teacherButton.setBounds(550,150,200,100);
        Font font=new Font("楷体",Font.BOLD,22);
        adminButton.setFont(font);
        studentButton.setFont(font);
        teacherButton.setFont(font);
        panel.add(adminButton);
        panel.add(studentButton);
        panel.add(teacherButton);
        ButtonListener choose_button =new ButtonListener();
        adminButton.addActionListener(choose_button);
        studentButton.addActionListener(choose_button);
        teacherButton.addActionListener(choose_button);
        frame.setVisible(true);
    }

    static void initWin2(){  //第二种界面
        JFrame frame = new JFrame("欢迎使用信息管理系统");
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        JPanel panel1 = new JPanel();
        frame.setLayout(new GridLayout(2,1));
        frame.add(panel1);  //添加面板
        panel1.setLayout(null);
        JLabel welcome=new JLabel("欢迎使用信息管理系统");
        welcome.setBounds(100,20,600,100);
        welcome.setFont(new Font("楷体",Font.BOLD,30));
        panel1.add(welcome);
        JPanel panel2 = new JPanel();
        frame.add(panel2);
        panel2.setLayout(new GridLayout(1,3));
        adminButton=new JButton("管理员系统");
        studentButton=new JButton("学生信息系统");
        teacherButton=new JButton("教师信息系统");
        Font font=new Font("楷体",Font.BOLD,22);
        adminButton.setFont(font);
        studentButton.setFont(font);
        teacherButton.setFont(font);
        panel2.add(adminButton);
        panel2.add(studentButton);
        panel2.add(teacherButton);
        ButtonListener choose_button =new ButtonListener();
        adminButton.addActionListener(choose_button);
        studentButton.addActionListener(choose_button);
        teacherButton.addActionListener(choose_button);
        frame.setVisible(true);
    }


    static class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("管理员系统")){
//                adminSys.adminWin(adminFrame);
                try {
                    LoginSys.LoginWin("管理员");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if(e.getActionCommand().equals("学生信息系统")){
                try {
                    LoginSys.LoginWin("学生");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else if (e.getActionCommand().equals("教师信息系统")){
                try {
                    LoginSys.LoginWin("教师");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            else System.out.println("Error!");
        }
    }
    public static void Tips(String tip){
        JFrame win;
        win=new JFrame(tip);
        win.setSize(300,200);
        win.setLocationRelativeTo(null);
//        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTextField text=new JTextField(tip);
        text.setFont(new Font("楷体",Font.BOLD,20));
        win.add(text);
        win.setVisible(true);
    }
}
