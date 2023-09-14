package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

public class RegPanel extends JPanel{
    private static String ZH;
    private static char[] PW;
    private static char[] PWplus;
    private static JTextField j1;
    private static JPasswordField j2;
    private static JPasswordField j3;
    public RegPanel(){
        setLayout(null);
        JLabel l1 = new JLabel("账号： ");
        l1.setBounds(0,100,100,50);
        l1.setFont(new Font("San Serif",Font.PLAIN,20));
        j1 = new JTextField(18);
        j1.setBounds(100,117,600,25);
        JLabel l2 = new JLabel("密码： ");
        l2.setBounds(0,200,100,50);
        l2.setFont(new Font("San Serif",Font.PLAIN,20));
        j2 = new JPasswordField(16);
        j2.setBounds(100,217,600,25);
        JLabel l3 = new JLabel("确认密码: ");
        l3.setBounds(0,300,100,50);
        l3.setFont(new Font("San Serif",Font.PLAIN,20));
        j3 = new JPasswordField(16);
        j3.setBounds(100,317,600,25);
        JButton b1 = new JButton("Back");
        b1.setBounds(200,500,120,50);
        b1.addActionListener(new ButtonClcikActionListener());
        JButton b2 = new JButton("注册");
        b2.setBounds(400,500,120,50);
        b2.addActionListener(new ZC());
        add(l1); add(j1); add(l2); add(j2); add(l3); add(j3); add(b2); add(b1);

    }
    public class ButtonClcikActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            MainFrame.panel4.setVisible(false);
            MainFrame.panel1.setVisible(true);
            MainFrame.frame.setContentPane(MainFrame.panel1);
        }
    }
    public class ZC implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            ZH = j1.getText();
            PW = j2.getPassword();
            PWplus = j3.getPassword();
            if(Arrays.equals(PW,PWplus)){
                try{
                    BufferedWriter writer = new BufferedWriter(new FileWriter(ZH+".txt"));
                    writer.write(PW);
                    writer.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
                JOptionPane.showMessageDialog(null,"注册成功","系统信息",JOptionPane.PLAIN_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null,"两次密码不一致","系统信息",JOptionPane.ERROR_MESSAGE);
            }
        }
    }


}


