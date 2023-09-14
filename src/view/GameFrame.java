package view;

import components.ChessGridComponent;
import controller.GameController;
import project.ImgPanel;
import project.LogPanel;
import project.MainFrame;
import project.BgPanel;
import view.ChessBoardPanel;
 import view.StatusPanel;

 import javax.swing.*;
        import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame {
    private JLayeredPane layeredPane;
    public static GameController controller;
    public static ChessBoardPanel chessBoardPanel;
    public static StatusPanel statusPanel;
    public static BgPanel bgPanel;
    private JLabel ZT1;
    public static JLabel ZT2;

    public static Insets inset;

    public GameFrame(int frameSize) {

        //layeredPane = new JLayeredPane();
        this.setTitle("2021F CS102A Project Reversi");
        this.setLayout(null);

        //获取窗口边框的长度，将这些值加到主窗口大小上，这能使窗口大小和预期相符
        inset = this.getInsets();
        this.setSize(frameSize + inset.left + inset.right + 100, frameSize + inset.top + inset.bottom);

        this.setLocationRelativeTo(null);
        chessBoardPanel = new ChessBoardPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.7));
        chessBoardPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, (this.getHeight() - chessBoardPanel.getHeight()) / 3);

        statusPanel = new StatusPanel((int) (this.getWidth() * 0.8), (int) (this.getHeight() * 0.1));
        statusPanel.setLocation((this.getWidth() - chessBoardPanel.getWidth()) / 2, 0);
        controller = new GameController(chessBoardPanel, statusPanel);
        controller.setGamePanel(chessBoardPanel);
        bgPanel = new BgPanel();
        bgPanel.setSize(frameSize + inset.left + inset.right + 100, frameSize + inset.top + inset.bottom);




        JButton restartBtn = new JButton("Restart");
        restartBtn.setSize(120, 50);
        restartBtn.setLocation((this.getWidth() - GameFrame.chessBoardPanel.getWidth()) / 2, (this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2);
        restartBtn.addActionListener(new ButtonRS());
        add(restartBtn);
        restartBtn.addActionListener(e -> {
            System.out.println("click restart Btn");

        });

        JButton loadGameBtn = new JButton("Load");
        loadGameBtn.setSize(120, 50);
        loadGameBtn.setLocation(restartBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(loadGameBtn);
        loadGameBtn.addActionListener(e -> {
            System.out.println("clicked Load Btn");
            GameFrame.controller.readFileData(LogPanel.ZH+".txt");
        });

        JButton saveGameBtn = new JButton("Save");
        saveGameBtn.setSize(120, 50);
        saveGameBtn.setLocation(loadGameBtn.getX()+restartBtn.getWidth()+30, restartBtn.getY());
        add(saveGameBtn);
        saveGameBtn.addActionListener(e -> {
            System.out.println("clicked Save Btn");
            GameFrame.controller.writeDataToFile(LogPanel.ZH+".txt");
        });

        JButton backButton = new JButton("Back");
        backButton.setSize(120,50);
        backButton.setLocation(saveGameBtn.getX()+restartBtn.getWidth()+30,restartBtn.getY());
        add(backButton);
        backButton.addActionListener(e -> {
            System.out.println("clicked Back Btn");
            MainFrame.frameGame.dispose();
            MainFrame.frame.setVisible(true);
        });
        JButton RR = new JButton("人人对战");
        RR.setSize(120,50);
        RR.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2);
        RR.addActionListener(new ButtonRR());
        add(RR);

        JButton RJ = new JButton("人机对战");
        RJ.setSize(120,50);
        RJ.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+200);
        RJ.addActionListener(new ButtonRJ());
        add(RJ);

        JButton ZB = new JButton("作弊模式");
        ZB.setSize(120,50);
        ZB.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+100);
        ZB.addActionListener(new ButtonZB());
        add(ZB);

        JButton RZ = new JButton("弱智人机");
        RZ.setSize(120,50);
        RZ.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+300);
        RZ.addActionListener(new ButtonRZ());
        add(RZ);

        JButton YX = new JButton("Sound");
        YX.setSize(120,50);
        YX.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+400);
        YX.addActionListener(new ImgPanel.ButtonClickListener5());
        add(YX);

        JButton CH = new JButton("撤回");
        CH.setSize(120,50);
        CH.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+500);
        CH.addActionListener(new ButtonCH());
        add(CH);

        JButton ZB2 = new JButton("取消作弊");
        ZB2.setSize(120,50);
        ZB2.setLocation(backButton.getX()+restartBtn.getWidth(),(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+600);
        ZB2.addActionListener(new ButtonZB2());
        add(ZB2);

        ZT1 = new JLabel("状态栏A");
        ZT1.setSize(150,70);
        ZT1.setLocation(backButton.getX()+restartBtn.getWidth()-725,(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+100);
        ZT1.setOpaque(true);
        add(ZT1);

        ZT2 = new JLabel("状态栏B");
        ZT2.setSize(150,70);
        ZT2.setLocation(backButton.getX()+restartBtn.getWidth()-725,(800 + GameFrame.inset.top + GameFrame.inset.bottom)-(this.getHeight() + GameFrame.chessBoardPanel.getHeight()) / 2+300);
        ZT2.setOpaque(true);
        add(ZT2);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(chessBoardPanel);
        add(statusPanel);
        add(bgPanel);
    }
    public class ButtonRR implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent arg0){
            ChessGridComponent.clickpVSp();
            ZT1.setText("人人模式开启");

        }
    }
    public class ButtonRJ implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            ChessGridComponent.clickc1VSp();
            ZT1.setText("人机模式开启");
        }
    }
    public class ButtonZB implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            ChessGridComponent.cheat();
            ZT2.setText("作弊模式开启");
        }
    }
    public class ButtonRZ implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            ChessGridComponent.clickc2VSp();
            ZT1.setText("弱智人机开启");
        }
    }
    public class ButtonRS implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            MainFrame.frameGame.dispose();
            MainFrame.frameGame = new GameFrame(800);
            MainFrame.frameGame.setVisible(true);
        }
    }
    public class ButtonCH implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            GameFrame.controller.getGamePanel().unDoAction();
        }
    }
    public class ButtonZB2 implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent arg0){
            ChessGridComponent.counterCheat();
        }
    }
}

