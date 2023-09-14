package view;

import components.ChessGridComponent;
import model.ChessPiece;
import components.ChessGridComponent;
import model.ChessPiece;

import javax.swing.*;
import java.awt.*;

public class ChessBoardPanel/*棋盘*/ extends JPanel {
    private final int CHESS_COUNT = 8;
    public ChessGridComponent[][] chessGrids;
    boolean gameOver = false;
    public int blackChess = 0, whiteChess = 0;
    public int[][] data = new int[8][8];
    public int[][][] recordGame= new int[64][8][8];
    public int step=1;

    public ChessBoardPanel(int width, int height) {
        this.setVisible(true);
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        int length = Math.min(width, height);
        this.setSize(length, length);
        ChessGridComponent.gridSize = length / CHESS_COUNT;
        ChessGridComponent.chessSize = (int) (ChessGridComponent.gridSize * 0.8);
        System.out.printf("width = %d height = %d gridSize = %d chessSize = %d\n",
                width, height, ChessGridComponent.gridSize, ChessGridComponent.chessSize);

        initialChessGrids();//return empty chessboard
        initialGame();//add initial four chess

        repaint();
    }

    /**
     * set an empty chessboard
     */
    public void initialChessGrids() {
        chessGrids = new ChessGridComponent[CHESS_COUNT][CHESS_COUNT];

        //draw all chess grids
        for (int i = 0; i < CHESS_COUNT; i++) {
            for (int j = 0; j < CHESS_COUNT; j++) {
                ChessGridComponent gridComponent = new ChessGridComponent(i, j);
                gridComponent.setLocation(j * ChessGridComponent.gridSize, i * ChessGridComponent.gridSize);
                chessGrids[i][j] = gridComponent;
                this.add(chessGrids[i][j]);
            }
        }
    }

    /**
     * initial origin four chess
     */
    public void initialGame() {
        chessGrids[3][3].setChessPiece(ChessPiece.BLACK);
        chessGrids[3][4].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][3].setChessPiece(ChessPiece.WHITE);
        chessGrids[4][4].setChessPiece(ChessPiece.BLACK);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public int translateColor(Color color) {
        if (color == Color.WHITE) {
            return 1;
        } else if (color == Color.BLACK) {
            return -1;
        } else {
            return 0;
        }
    }


    public ChessGridComponent[][] getChessGrids() {
        return chessGrids;
    }

    public boolean canClickGrid(int row, int col, ChessPiece currentPlayer) {
        //todo: complete this method
        //判断是否这个地方能不能下棋子
        ////
        if (getWhereCanChick(chessGrids, currentPlayer)[row][col] == 1) {
            return true;
        } else {
            return false;
        }
    }

    public int getBestChoice(ChessGridComponent[][] chessGrids, ChessPiece currentPlayer) {//找出最多棋子的位置
        int count = 0;
        int color = translateColor(currentPlayer.getColor());
        int[] sum = new int[64];//用来记录棋盘中棋子个数
        int[][] positionInformation = new int[65][2];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getWhereCanChick(chessGrids, currentPlayer)[i][j] == 1) {
                    ++count;
                    positionInformation[count][0] = i;//0用来储存行
                    positionInformation[count][1] = j;//1用来储存列
                }
            }
        }
        int[][] boardx = new int[8][8];
        for (int i = 0; i < 8; ++i) {//把棋盘中的数据转换成数值
            for (int j = 0; j < 8; ++j) {
                if (chessGrids[i][j].getChessPiece() == null) {

                } else {
                    boardx[i][j] = translateColor(chessGrids[i][j].getChessPiece().getColor());
                }
            }
        }
        for (int k = 1; k <= count; ++k) {
            int row1 = positionInformation[k][0];
            int col1 = positionInformation[k][1];
            if (col1 <= 5 && boardx[row1][col1 + 1] == -color) {//放右边
                int m = 1;
                while (boardx[row1][col1 + m] == -color && col1 + m <= 6) {
                    m++;
                }
                if (col1 + m <= 7 && boardx[row1][col1 + m] == color) {
                    for (int n = col1; n <= col1 + m; ++n) {
                        boardx[row1][n] = color;
                    }
                }
            }
            if (col1 >= 2 && boardx[row1][col1 - 1] == -color) {//放左边
                int m = 1;
                while (boardx[row1][col1 - m] == -color && col1 - m >= 1) {
                    m++;
                }
                if (col1 - m >= 0 && boardx[row1][col1 - m] == color) {
                    for (int n = col1; n >= col1 - m; --n) {
                        boardx[row1][n] = color;
                    }
                }
            }
            if (row1 >= 2 && boardx[row1 - 1][col1] == -color) {//放上边
                int m = 1;
                while (boardx[row1 - m][col1] == -color && row1 - m >= 1) {
                    m++;
                }
                if (row1 - m >= 0 && boardx[row1 - m][col1] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 - n][col1] = color;
                    }
                }
            }
            if (row1 <= 5 && boardx[row1 + 1][col1] == -color) {//放下边
                int m = 1;
                while (boardx[row1 + m][col1] == -color && row1 + m <= 6) {
                    m++;
                }
                if (row1 + m <= 7 && boardx[row1 + m][col1] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 + n][col1] = color;
                    }
                }
            }
            if (row1 <= 5 && col1 <= 5 && boardx[row1 + 1][col1 + 1] == -color) {//放右下边
                int m = 1;
                while (boardx[row1 + m][col1 + m] == -color && col1 + m <= 6 && row1 + m <= 6) {
                    m++;
                }
                if (col1 + m <= 7 && row1 + m <= 7 && boardx[row1 + m][col1 + m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 + n][col1 + n] = color;
                    }
                }
            }
            if (row1 >= 2 && col1 >= 2 && boardx[row1 - 1][col1 - 1] == -color) {//放左上边
                int m = 1;
                while (boardx[row1 - m][col1 - m] == -color && col1 - m >= 1 && row1 - m >= 1) {
                    m++;
                }
                if (col1 - m >= 0 && row1 - m >= 0 && boardx[row1 - m][col1 - m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 - n][col1 - n] = color;
                    }
                }
            }
            if (row1 <= 5 && col1 >= 2 && boardx[row1 + 1][col1 - 1] == -color) {//放左下边
                int m = 1;
                while (boardx[row1 + m][col1 - m] == -color && row1 + m <= 6 && col1 - m >= 1) {
                    m++;
                }
                if (col1 - m >= 0 && row1 + m <= 7 && boardx[row1 + m][col1 - m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 + n][col1 - n] = color;
                    }
                }
            }
            if (row1 >= 2 && col1 <= 5 && boardx[row1 - 1][col1 + 1] == -color) {//放右上边
                int m = 1;
                while (boardx[row1 - m][col1 + m] == -color && row1 - m >= 1 && col1 + m <= 6) {
                    m++;
                }
                if (col1 + m <= 7 && row1 - m >= 0 && boardx[row1 - m][col1 + m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 - n][col1 + n] = color;
                    }
                }
            }
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (boardx[i][j] == color) {
                        sum[k]++;
                    }
                }
            }
        }
        int max = sum[1];
        int point = 1;
        for (int i = 1; i < 64; ++i) {
            if (sum[i] > max) {
                max = sum[i];
                point = i;
            }
        }
        return positionInformation[point][0] * 10 + positionInformation[point][1];
        //这是电脑VS玩家时电脑的操作
        //我找出所有可以下棋子的地方下了棋之后棋盘内白色棋子个数，然后进行比较，找出棋子最多的那个点，然后下那个位置
        //我把行放在十位上，列放在个位上，取用的时候再整除和取余获得
    }

    public int getWorseChoice(ChessGridComponent[][] chessGrids, ChessPiece currentPlayer) {//找出最多棋子的位置
        int count = 0;
        int color = translateColor(currentPlayer.getColor());
        int[] sum = new int[64];//用来记录棋盘中棋子个数
        int[][] positionInformation = new int[65][2];
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getWhereCanChick(chessGrids, currentPlayer)[i][j] == 1) {
                    ++count;
                    positionInformation[count][0] = i;//0用来储存行
                    positionInformation[count][1] = j;//1用来储存列
                }
            }
        }
        int[][] boardx = new int[8][8];
        for (int i = 0; i < 8; ++i) {//把棋盘中的数据转换成数值
            for (int j = 0; j < 8; ++j) {
                if (chessGrids[i][j].getChessPiece() == null) {

                } else {
                    boardx[i][j] = translateColor(chessGrids[i][j].getChessPiece().getColor());
                }
            }
        }
        for (int k = 1; k <= count; ++k) {
            int row1 = positionInformation[k][0];
            int col1 = positionInformation[k][1];
            if (col1 <= 5 && boardx[row1][col1 + 1] == -color) {//放右边
                int m = 1;
                while (boardx[row1][col1 + m] == -color && col1 + m <= 6) {
                    m++;
                }
                if (col1 + m <= 7 && boardx[row1][col1 + m] == color) {
                    for (int n = col1; n <= col1 + m; ++n) {
                        boardx[row1][n] = color;
                    }
                }
            }
            if (col1 >= 2 && boardx[row1][col1 - 1] == -color) {//放左边
                int m = 1;
                while (boardx[row1][col1 - m] == -color && col1 - m >= 1) {
                    m++;
                }
                if (col1 - m >= 0 && boardx[row1][col1 - m] == color) {
                    for (int n = col1; n >= col1 - m; --n) {
                        boardx[row1][n] = color;
                    }
                }
            }
            if (row1 >= 2 && boardx[row1 - 1][col1] == -color) {//放上边
                int m = 1;
                while (boardx[row1 - m][col1] == -color && row1 - m >= 1) {
                    m++;
                }
                if (row1 - m >= 0 && boardx[row1 - m][col1] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 - n][col1] = color;
                    }
                }
            }
            if (row1 <= 5 && boardx[row1 + 1][col1] == -color) {//放下边
                int m = 1;
                while (boardx[row1 + m][col1] == -color && row1 + m <= 6) {
                    m++;
                }
                if (row1 + m <= 7 && boardx[row1 + m][col1] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 + n][col1] = color;
                    }
                }
            }
            if (row1 <= 5 && col1 <= 5 && boardx[row1 + 1][col1 + 1] == -color) {//放右下边
                int m = 1;
                while (boardx[row1 + m][col1 + m] == -color && col1 + m <= 6 && row1 + m <= 6) {
                    m++;
                }
                if (col1 + m <= 7 && row1 + m <= 7 && boardx[row1 + m][col1 + m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 + n][col1 + n] = color;
                    }
                }
            }
            if (row1 >= 2 && col1 >= 2 && boardx[row1 - 1][col1 - 1] == -color) {//放左上边
                int m = 1;
                while (boardx[row1 - m][col1 - m] == -color && col1 - m >= 1 && row1 - m >= 1) {
                    m++;
                }
                if (col1 - m >= 0 && row1 - m >= 0 && boardx[row1 - m][col1 - m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 - n][col1 - n] = color;
                    }
                }
            }
            if (row1 <= 5 && col1 >= 2 && boardx[row1 + 1][col1 - 1] == -color) {//放左下边
                int m = 1;
                while (boardx[row1 + m][col1 - m] == -color && row1 + m <= 6 && col1 - m >= 1) {
                    m++;
                }
                if (col1 - m >= 0 && row1 + m <= 7 && boardx[row1 + m][col1 - m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 + n][col1 - n] = color;
                    }
                }
            }
            if (row1 >= 2 && col1 <= 5 && boardx[row1 - 1][col1 + 1] == -color) {//放右上边
                int m = 1;
                while (boardx[row1 - m][col1 + m] == -color && row1 - m >= 1 && col1 + m <= 6) {
                    m++;
                }
                if (col1 + m <= 7 && row1 - m >= 0 && boardx[row1 - m][col1 + m] == color) {
                    for (int n = 0; n <= m - 1; ++n) {
                        boardx[row1 - n][col1 + n] = color;
                    }
                }
            }
            for (int i = 0; i < 8; ++i) {
                for (int j = 0; j < 8; ++j) {
                    if (boardx[i][j] == color) {
                        sum[k]++;
                    }
                }
            }
        }
        int max = sum[1];
        int point = 1;
        for (int i = 1; i < 64; ++i) {
            if (sum[i] > max) {
                max = sum[i];
                point = i;
            }
        }
        return positionInformation[1][0]*10+positionInformation[1][1];
    }


    public void colorChange(int row, int col, Color color1) {
        int color = translateColor(color1);
        int[][] boardx = new int[8][8];
        for (int i = 0; i < 8; i++) {//输入棋盘
            for (int j = 0; j < 8; j++) {
                if (chessGrids[i][j].getChessPiece() == null) {
                    ;
                } else {
                    boardx[i][j] = translateColor(chessGrids[i][j].getChessPiece().getColor());
                }
            }
        }//输入完毕
        int row1 = row;
        int col1 = col;
        if (col1 <= 5 && boardx[row1][col1 + 1] == -color) {//检查右边
            int m = 1;
            while (boardx[row1][col1 + m] == -color && col1 + m <= 6) {
                m++;
            }
            if (col1 + m <= 7 && boardx[row1][col1 + m] == color) {
                for (int n = col1; n <= col1 + m; ++n) {
                    boardx[row1][n] = color;
                }
            }
        }
        if (col1 >= 2 && boardx[row1][col1 - 1] == -color) {//检查左边
            int m = 1;
            while (boardx[row1][col1 - m] == -color && col1 - m >= 1) {
                m++;
            }
            if (col1 - m >= 0 && boardx[row1][col1 - m] == color) {
                for (int n = col1; n >= col1 - m; --n) {
                    boardx[row1][n] = color;
                }
            }
        }
        if (row1 >= 2 && boardx[row1 - 1][col1] == -color) {//检查上边
            int m = 1;
            while (boardx[row1 - m][col1] == -color && row1 - m >= 1) {
                m++;
            }
            if (row1 - m >= 0 && boardx[row1 - m][col1] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 - n][col1] = color;
                }
            }
        }
        if (row1 <= 5 && boardx[row1 + 1][col1] == -color) {//检查下边
            int m = 1;
            while (boardx[row1 + m][col1] == -color && row1 + m <= 6) {
                m++;
            }
            if (row1 + m <= 7 && boardx[row1 + m][col1] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 + n][col1] = color;
                }
            }
        }
        if (row1 <= 5 && col1 <= 5 && boardx[row1 + 1][col1 + 1] == -color) {//检查右下边
            int m = 1;
            while (boardx[row1 + m][col1 + m] == -color && col1 + m <= 6 && row1 + m <= 6) {
                m++;
            }
            System.out.println(m);
            if (col1 + m <= 7 && row1 + m <= 7 && boardx[row1 + m][col1 + m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 + n][col1 + n] = color;
                }
            }
        }
        if (row1 >= 2 && col1 >= 2 && boardx[row1 - 1][col1 - 1] == -color) {//检查左上边
            int m = 1;
            while (boardx[row1 - m][col1 - m] == -color && col1 - m >= 1 && row1 - m >= 1) {
                m++;
            }
            if (col1 - m >= 0 && row1 - m >= 0 && boardx[row1 - m][col1 - m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 - n][col1 - n] = color;
                }
            }
        }
        if (row1 <= 5 && col1 >= 2 && boardx[row1 + 1][col1 - 1] == -color) {//检查左下边
            int m = 1;
            while (boardx[row1 + m][col1 - m] == -color && row1 + m <= 6 && col1 - m >= 1) {
                m++;
            }
            if (col1 - m >= 0 && row1 + m <= 7 && boardx[row1 + m][col1 - m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 + n][col1 - n] = color;
                }
            }
        }
        if (row1 >= 2 && col1 <= 5 && boardx[row1 - 1][col1 + 1] == -color) {//检查右上边
            int m = 1;
            while (boardx[row1 - m][col1 + m] == -color && row1 - m >= 1 && col1 + m <= 6) {
                m++;
            }
            if (col1 + m <= 7 && row1 - m >= 0 && boardx[row1 - m][col1 + m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 - n][col1 + n] = color;
                }
            }
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (boardx[i][j] == 1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                    repaint();
                } else if (boardx[i][j] == -1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                    repaint();
                }else if (boardx[i][j]==0){
                    chessGrids[i][j].setChessPiece(null);
                    repaint();
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j <8 ; j++) {
                recordGame[step][i][j]=boardx[i][j];
            }
        }
        step++;


    }

    public void computerColorChange(int row, int col, Color color1) {
        int color = translateColor(color1);
        int[][] boardx = new int[8][8];
        for (int i = 0; i < 8; i++) {//输入棋盘
            for (int j = 0; j < 8; j++) {
                if (chessGrids[i][j].getChessPiece() == null) {
                    ;
                } else {
                    boardx[i][j] = translateColor(chessGrids[i][j].getChessPiece().getColor());
                }
            }
        }//输入完毕
        int row1 = row;
        int col1 = col;
        if (col1 <= 5 && boardx[row1][col1 + 1] == -color) {//检查右边
            int m = 1;
            while (boardx[row1][col1 + m] == -color && col1 + m <= 6) {
                m++;
            }
            if (col1 + m <= 7 && boardx[row1][col1 + m] == color) {
                for (int n = col1; n <= col1 + m; ++n) {
                    boardx[row1][n] = color;
                }
            }
        }
        if (col1 >= 2 && boardx[row1][col1 - 1] == -color) {//检查左边
            int m = 1;
            while (boardx[row1][col1 - m] == -color && col1 - m >= 1) {
                m++;
            }
            if (col1 - m >= 0 && boardx[row1][col1 - m] == color) {
                for (int n = col1; n >= col1 - m; --n) {
                    boardx[row1][n] = color;
                }
            }
        }
        if (row1 >= 2 && boardx[row1 - 1][col1] == -color) {//检查上边
            int m = 1;
            while (boardx[row1 - m][col1] == -color && row1 - m >= 1) {
                m++;
            }
            if (row1 - m >= 0 && boardx[row1 - m][col1] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 - n][col1] = color;
                }
            }
        }
        if (row1 <= 5 && boardx[row1 + 1][col1] == -color) {//检查下边
            int m = 1;
            while (boardx[row1 + m][col1] == -color && row1 + m <= 6) {
                m++;
            }
            if (row1 + m <= 7 && boardx[row1 + m][col1] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 + n][col1] = color;
                }
            }
        }
        if (row1 <= 5 && col1 <= 5 && boardx[row1 + 1][col1 + 1] == -color) {//检查右下边
            int m = 1;
            while (boardx[row1 + m][col1 + m] == -color && col1 + m <= 6 && row1 + m <= 6) {
                m++;
            }
            if (col1 + m <= 7 && row1 + m <= 7 && boardx[row1 + m][col1 + m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 + n][col1 + n] = color;
                }
            }
        }
        if (row1 >= 2 && col1 >= 2 && boardx[row1 - 1][col1 - 1] == -color) {//检查左上边
            int m = 1;
            while (boardx[row1 - m][col1 - m] == -color && col1 - m >= 1 && row1 - m >= 1) {
                m++;
            }
            if (col1 - m >= 0 && row1 - m >= 0 && boardx[row1 - m][col1 - m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 - n][col1 - n] = color;
                }
            }
        }
        if (row1 <= 5 && col1 >= 2 && boardx[row1 + 1][col1 - 1] == -color) {//检查左下边
            int m = 1;
            while (boardx[row1 + m][col1 - m] == -color && row1 + m <= 6 && col1 - m >= 1) {
                m++;
            }
            if (col1 - m >= 0 && row1 + m <= 7 && boardx[row1 + m][col1 - m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 + n][col1 - n] = color;
                }
            }
        }
        if (row1 >= 2 && col1 <= 5 && boardx[row1 - 1][col1 + 1] == -color) {//检查右上边
            int m = 1;
            while (boardx[row1 - m][col1 + m] == -color && row1 - m >= 1 && col1 + m <= 6) {
                m++;
            }
            if (col1 + m <= 7 && row1 - m >= 0 && boardx[row1 - m][col1 + m] == color) {
                for (int n = 0; n <= m - 1; ++n) {
                    boardx[row1 - n][col1 + n] = color;
                }
            }
        }

        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (boardx[i][j] == 1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                    repaint();
                } else if (boardx[i][j] == -1) {
                    chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                    repaint();
                }else if (boardx[i][j]==0){
                    chessGrids[i][j].setChessPiece(null);
                    repaint();
                }
            }
        }
        /*try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public void unDoAction(){
        step--;
        if (step>1){
            for (int i = 0; i <8 ; i++) {
                for (int j = 0; j <8 ; j++) {
                    if (recordGame[step][i][j] == 1) {
                        chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                        repaint();
                    } else if (recordGame[step][i][j] == -1) {
                        chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                        repaint();
                    }else if (recordGame[step][i][j]==0){
                        chessGrids[i][j].setChessPiece(null);
                        repaint();
                    }
                }
            }
        }
        if(step==1){
            for (int i = 0; i <8 ; i++) {
                for (int j = 0; j <8 ; j++) {
                    if (((i==3&&j==4)||(i==4&&j==3))) {
                        chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                        repaint();
                    } else if (((i==4&&j==4)||(i==3&&j==3))) {
                        chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                        repaint();
                    }else {
                        chessGrids[i][j].setChessPiece(null);
                        repaint();
                    }
                }
            }
        }
        if(step>0){
            GameFrame.controller.swapPlayer();
        }
    }

    public boolean judgeturn() {
        return true;
    }


    public int[][] getWhereCanChick(ChessGridComponent[][] chessGrids, ChessPiece currentPlayer) {
        int[][] bd = new int[8][8];
        int[][] output = new int[8][8];//用来储存可以放置的位置
        for (int i = 0; i < 8; ++i) {//把棋盘中的数据转换成数值
            for (int j = 0; j < 8; ++j) {
                if (chessGrids[i][j].getChessPiece() == null) {
                    ;
                } else {
                    bd[i][j] = translateColor(chessGrids[i][j].getChessPiece().getColor());
                }
            }
        }
        int color = translateColor(currentPlayer.getColor());
        for (int a = 0; a < 8; a++) {//方位放入
            for (int b = 0; b < 8; b++) {
                if (b <= 5 && bd[a][b] == color && bd[a][b + 1] == -color) {//往右检查
                    int m = 1;
                    while (b + m <= 6 && bd[a][b + m] == -color) {
                        m++;
                    }
                    if (b + m <= 7 && bd[a][b + m] == 0) {
                        output[a][b + m] = 1;
                    }
                }
                if (b >= 2 && bd[a][b] == color && bd[a][b - 1] == -color) {//往左检查
                    int m = 1;
                    while (b - m >= 1 && bd[a][b - m] == -color) {
                        m++;
                    }
                    if (b - m >= 0 && bd[a][b - m] == 0) {
                        output[a][b - m] = 1;
                    }
                }
                if (a >= 2 && bd[a][b] == color && bd[a - 1][b] == -color) {//往上检查
                    int m = 1;
                    while (a - m >= 1 && bd[a - m][b] == -color) {
                        m++;
                    }
                    if (a - m >= 0 && bd[a - m][b] == 0) {
                        output[a - m][b] = 1;
                    }
                }
                if (a <= 5 && bd[a][b] == color && bd[a + 1][b] == -color) {//往下检查
                    int m = 1;
                    while (a + m <= 6 && bd[a + m][b] == -color) {
                        m++;
                    }
                    if (a + m <= 7 && bd[a + m][b] == 0) {
                        output[a + m][b] = 1;
                    }
                }
                if (a <= 5 && b <= 5 && bd[a][b] == color && bd[a + 1][b + 1] == -color) {//往右下检查
                    int m = 1;
                    while (a + m <= 6 && b + m <= 6 && bd[a + m][b + m] == -color) {
                        m++;
                    }
                    if (a + m <= 7 && b + m <= 7 && bd[a + m][b + m] == 0) {
                        output[a + m][b + m] = 1;
                    }
                }
                if (a >= 2 && b >= 2 && bd[a][b] == color && bd[a - 1][b - 1] == -color) {//往左上检查
                    int m = 1;
                    while (a - m >= 1 && b - m >= 1 && bd[a - m][b - m] == -color) {
                        m++;
                    }
                    if (a - m >= 0 && b - m >= 0 && bd[a - m][b - m] == 0) {
                        output[a - m][b - m] = 1;
                    }
                }
                if (a >= 2 && b <= 5 && bd[a][b] == color && bd[a - 1][b + 1] == -color) {//往右上检查
                    int m = 1;
                    while (a - m >= 1 && b + m <= 6 && bd[a - m][b + m] == -color) {
                        m++;
                    }
                    if (a - m >= 0 && b + m <= 7 && bd[a - m][b + m] == 0) {
                        output[a - m][b + m] = 1;
                    }
                }
                if (a <= 5 && b >= 2 && bd[a][b] == color && bd[a + 1][b - 1] == -color) {//往左下检查
                    int m = 1;
                    while (a + m <= 6 && b - m >= 1 && bd[a + m][b - m] == -color) {
                        m++;
                    }
                    if (a + m <= 7 && b - m >= 0 && bd[a + m][b - m] == 0) {
                        output[a + m][b - m] = 1;
                    }
                }
            }
        }//方位放入完毕

        for (int i = 0; i <8; i++) {
            for (int j = 0; j <8; j++) {
                if(output[i][j]==1){
                    chessGrids[i][j].setChessPiece(ChessPiece.yellow);
                    repaint();
                }
            }
        }
        return output;
    }

    public boolean checkWhetherCanChick(ChessGridComponent[][] chessGrids, ChessPiece currentPlayer) {
        int a = 0;
        for (int i = 0; i < 8; ++i) {
            for (int j = 0; j < 8; ++j) {
                if (getWhereCanChick(chessGrids, currentPlayer)[i][j] != 1) {
                    ;
                } else {
                    ++a;
                }
            }
        }
        if (a > 0) {
            return true;
        } else {
            return false;
        }
    }

}
