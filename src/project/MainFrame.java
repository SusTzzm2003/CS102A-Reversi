package project;


import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame{
    public static ImgPanel panel1;


    public static LogPanel panel3;
    public static RegPanel panel4;
    public static MainFrame frame;
    public static GameFrame frameGame;
    public MainFrame(){
        super("游戏开始界面");
        setLayout(null);
       panel1 = new ImgPanel();
        setContentPane(panel1);
    }



    public static void main(String[] args){
         frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setResizable(false);
        frame.setVisible(true);
        JavaClip clip = new JavaClip();
    }
}
