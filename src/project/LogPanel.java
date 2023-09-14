package project;


import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class LogPanel extends JPanel {
    public static String ZH;
    private JTextField j1;
    private char[] PW;
    private JPasswordField j2;

    public LogPanel() {
        setLayout(null);
        JLabel l1 = new JLabel("账号： ");
        l1.setBounds(0,100,100,50);
        l1.setFont(new Font("San Serif",Font.PLAIN,30));
        j1 = new JTextField(18);
        j1.setBounds(100,117,600,25);
        JLabel l2 = new JLabel("密码： ");
        l2.setBounds(0,200,100,50);
        l2.setFont(new Font("San Serif",Font.PLAIN,30));
        j2 = new JPasswordField(16);
        j2.setBounds(100,217,600,25);
        JButton b1 = new JButton("Back");
        b1.setBounds(200,400,120,50);
        b1.addActionListener(new ButtonClcikActionListener());
        JButton b2 = new JButton("登录");
        b2.setBounds(400,400,120,50);
        b2.addActionListener(new judge());
        add(l1);
        add(j1);
        add(l2);
        add(j2);
        add(b2);
        add(b1);

    }

    public class ButtonClcikActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            MainFrame.panel3.setVisible(false);
            MainFrame.panel1.setVisible(true);
            MainFrame.frame.setContentPane(MainFrame.panel1);
        }
    }

    public class judge implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0) {
            ZH = j1.getText();
            PW = j2.getPassword();
            String PW1 = String.valueOf(PW);
            String realPw = null;
            try {
                BufferedReader reader = new BufferedReader(new FileReader(ZH + ".txt"));
                realPw = reader.readLine();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert realPw != null;
            if (realPw.equals(PW1)) {
                JOptionPane.showMessageDialog(null, "登陆成功", "系统信息", JOptionPane.PLAIN_MESSAGE);
                MainFrame.frame.dispose();
                MainFrame.frameGame = new GameFrame(800);
                MainFrame.frameGame.setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "密码错误", "系统信息", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
