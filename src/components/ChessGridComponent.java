package components;


import model.ChessPiece;
import model.ChessPiece;
import view.GameFrame;
import view.GameFrame;

import javax.swing.*;
import java.awt.*;

public class ChessGridComponent/*棋盘格组件*/ extends BasicComponent {
    public static int chessSize;
    public static int gridSize;
    public static Color gridColor = new Color(255, 150, 50);
    public static boolean playerVSplayer = false;
    public static boolean computer1VSplayer = false;
    public static boolean computer2VSplayer = true;
    private ChessPiece chessPiece;
    public static boolean cheatMode=false;

    private int row;
    private int col;

    public ChessGridComponent(int row, int col) {
        this.setSize(gridSize, gridSize);

        this.row = row;
        this.col = col;
    }

    @Override
    public void onMouseClicked() {
        GameFrame.ZT2.setText("状态栏B");
        System.out.printf("%s clicked (%d, %d)\n", GameFrame.controller.getCurrentPlayer(), row, col);
        //todo: complete mouse click method
        if (GameFrame.controller.canClick(row, col) && playerVSplayer) {//人人对战
            if (this.chessPiece.getColor() != Color.BLACK && this.chessPiece.getColor() != Color.WHITE) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                GameFrame.controller.getGamePanel().colorChange(row, col, chessPiece.getColor());
                GameFrame.controller.swapPlayer();
                repaint();
                //GameFrame.controller.getGamePanel().colorChange();
            }
        } else if (!GameFrame.controller.getGamePanel().checkWhetherCanChick(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) && playerVSplayer) {
            GameFrame.controller.swapPlayer();
        }

        for (int i = 1; i < 64; ++i) {
            if (GameFrame.controller.canClick(row, col) && GameFrame.controller.getCurrentPlayer().getColor() == Color.BLACK && (computer1VSplayer || computer2VSplayer)) {//人机对战
                if (this.chessPiece.getColor() != Color.BLACK && this.chessPiece.getColor() != Color.WHITE) {
                    //玩家下棋
                    this.chessPiece = GameFrame.controller.getCurrentPlayer();
                    GameFrame.controller.getGamePanel().colorChange(row, col, chessPiece.getColor());
                    GameFrame.controller.swapPlayer();
                    repaint();
                }
            } else if (!GameFrame.controller.getGamePanel().checkWhetherCanChick(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) && (computer1VSplayer || computer2VSplayer)) {
                this.chessPiece = GameFrame.controller.getCurrentPlayer();
                GameFrame.controller.swapPlayer();
            }

            if (GameFrame.controller.getCurrentPlayer().getColor() == Color.WHITE && computer1VSplayer) {
                int row1 = GameFrame.controller.getGamePanel().getBestChoice(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) / 10;
                int col1 = GameFrame.controller.getGamePanel().getBestChoice(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) % 10;
                if (GameFrame.controller.getGamePanel().chessGrids[row1][col1].getChessPiece().getColor() != Color.BLACK && GameFrame.controller.getGamePanel().chessGrids[row1][col1].getChessPiece().getColor() != Color.WHITE) {
                    GameFrame.controller.getGamePanel().computerColorChange(row1, col1, Color.WHITE);
                    GameFrame.controller.swapPlayer();
                    repaint();
                    System.out.println(row1 + " " + col1);
                    System.out.println(GameFrame.controller.getCurrentPlayer().getColor());
                }
                if (!GameFrame.controller.getGamePanel().checkWhetherCanChick(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) && computer1VSplayer) {
                    GameFrame.controller.swapPlayer();
                }
            }

            if (GameFrame.controller.getCurrentPlayer().getColor() == Color.WHITE && computer2VSplayer) {
                int row1 = GameFrame.controller.getGamePanel().getWorseChoice(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) / 10;
                int col1 = GameFrame.controller.getGamePanel().getWorseChoice(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) % 10;
                if (GameFrame.controller.getGamePanel().chessGrids[row1][col1].getChessPiece().getColor() != Color.BLACK && GameFrame.controller.getGamePanel().chessGrids[row1][col1].getChessPiece().getColor() != Color.WHITE) {
                    GameFrame.controller.getGamePanel().computerColorChange(row1, col1, Color.WHITE);
                    GameFrame.controller.swapPlayer();
                    repaint();
                    System.out.println(row1 + " " + col1);
                    System.out.println(GameFrame.controller.getCurrentPlayer().getColor());
                }
                if (!GameFrame.controller.getGamePanel().checkWhetherCanChick(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer()) && computer2VSplayer) {
                    GameFrame.controller.swapPlayer();
                }
            }
        }

        if (!GameFrame.controller.getGamePanel().checkWhetherCanChick(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer())) {
            GameFrame.controller.swapPlayer();
            if (!GameFrame.controller.getGamePanel().checkWhetherCanChick(GameFrame.controller.getGamePanel().getChessGrids(), GameFrame.controller.getCurrentPlayer())) {
                if(GameFrame.controller.gameOver()==1){
                    JOptionPane.showMessageDialog(null,"黑方胜利","系统信息",JOptionPane.PLAIN_MESSAGE);
                }else if(GameFrame.controller.gameOver()==-1){
                    JOptionPane.showMessageDialog(null,"白方胜利","系统信息",JOptionPane.PLAIN_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(null,"平局","系统信息",JOptionPane.PLAIN_MESSAGE);
                }

            }
        }

        if (cheatMode){
            this.chessPiece = GameFrame.controller.getCurrentPlayer();
            GameFrame.controller.getGamePanel().colorChange(row, col, chessPiece.getColor());
        }
    }

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

    public void setChessPiece(ChessPiece chessPiece) {
        this.chessPiece = chessPiece;
    }

    /*public void whoWin() {
        if (GameFrame.controller.blackScore>GameFrame.controller.whiteScore){
            JOptionPane.showMessageDialog(null, "恭喜黑方获胜！");
        }else if (GameFrame.controller.blackScore<GameFrame.controller.whiteScore) {
            JOptionPane.showMessageDialog(null, "恭喜白方获胜！");
        } else {
            JOptionPane.showMessageDialog(null, "平局了！");
        }
    }*/

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void drawPiece(Graphics g) {
        g.setColor(gridColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
        if (this.chessPiece != null) {
            g.setColor(chessPiece.getColor());
            g.fillOval((gridSize - chessSize) / 2, (gridSize - chessSize) / 2, chessSize, chessSize);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.printComponents(g);
        drawPiece(g);
    }

    public static void clickpVSp() {
        playerVSplayer = true;
        computer1VSplayer = false;
        computer2VSplayer = false;
    }

    public static void clickc1VSp() {
        playerVSplayer = false;
        computer1VSplayer = true;
        computer2VSplayer = false;
    }

    public static void clickc2VSp() {
        playerVSplayer = false;
        computer1VSplayer = false;
        computer2VSplayer = true;
    }

    public static void cheat() {
        cheatMode=true;
    }

    public static  void counterCheat(){
        cheatMode=false;
    }
}

