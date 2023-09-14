package project;

import components.ChessGridComponent;
import project.MainFrame;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BgPanel extends JPanel {

    private ImageIcon icon;
    private Image img;

    public BgPanel(){
        this.setVisible(true);
        this.setLayout(null);
        icon = new ImageIcon("src/images/bg4.jpeg");
        img = icon.getImage();







}

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(img,0,0,this.getWidth(),this.getHeight(),this);
    }
}
