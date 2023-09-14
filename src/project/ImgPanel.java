package project;

import view.GameFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class ImgPanel extends JPanel {
   private ImageIcon icon;
   private Image img;
    public ImgPanel(){
        icon = new ImageIcon("src/images/bg.jpeg");
        img = icon.getImage();
        setLayout(null);

        JButton button1 = new JButton("Log in");
        button1.setFont(new Font("San Serif",Font.BOLD+Font.ITALIC,25));
        button1.setBounds(550,100,200,50);
        button1.setBorder(BorderFactory.createRaisedBevelBorder());
        button1.addActionListener(new ButtonClickListener3());

        JLabel label1 = new JLabel("Welcome to Reversi");
        label1.setFont(new Font("宋体",Font.BOLD+Font.ITALIC,40));
        label1.setForeground(Color.WHITE);
        label1.setBounds(200,0,800,100);

        JButton button2 = new JButton("Register");
        button2.setFont(new Font("San Serif",Font.BOLD+ Font.ITALIC,25));
        button2.setBounds(550,200,200,50);
        button2.addActionListener(new ButtonClickListener4());


        JButton button4 = new JButton("Quick game");
        button4.setFont(new Font("San Serif",Font.BOLD+ Font.ITALIC,25));
        button4.setBounds(550,300,200,50);
        button4.addActionListener(new ButtonClickListener2());

        JButton button5 = new JButton("Sound");
        button5.setFont(new Font ("San Serif",Font.BOLD+ Font.ITALIC,25));
        button5.setBounds(550,400,200,50);
        button5.addActionListener(new ButtonClickListener5());




        add(label1);
        add(button1);
        add(button2);
        add(button4);
        add(button5);
    }

    public class ButtonClickListener2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            MainFrame.frame.dispose();
            MainFrame.frameGame = new GameFrame(800);
            MainFrame.frameGame.setVisible(true);
        }
    }
    public class ButtonClickListener3 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            MainFrame.panel1.setVisible(false);
            MainFrame.panel3 = new LogPanel();
            MainFrame.frame.setContentPane(MainFrame.panel3);
        }
    }
    public class ButtonClickListener4 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            MainFrame.panel1.setVisible(false);
            MainFrame.panel4 = new RegPanel();
            MainFrame.frame.setContentPane(MainFrame.panel4);
        }
    }
    public static class ButtonClickListener5 implements ActionListener{
       private static int a = 2;
        @Override
        public void actionPerformed(ActionEvent arg0){
            if(a%2==0){
                JavaClip.MusicOn();
                a++;
            }else{
                JavaClip.MusicOff();
                a++;
            }

        }
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }
}
