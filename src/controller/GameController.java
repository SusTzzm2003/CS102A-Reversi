package controller;

import model.ChessPiece;


import project.LogPanel;
import view.GameFrame;

import view.ChessBoardPanel;
import view.StatusPanel;

import java.awt.*;
import java.io.*;


public class GameController/*游戏控制器*/ {


    private ChessBoardPanel gamePanel;
    private StatusPanel statusPanel;
    private ChessPiece currentPlayer;
    public int blackScore;
    public int whiteScore;
    private boolean isBlackPlay=true;//是否是黑棋先手


    public GameController(ChessBoardPanel gamePanel, StatusPanel statusPanel) {
        this.gamePanel = gamePanel;
        this.statusPanel = statusPanel;
        this.currentPlayer = ChessPiece.BLACK;
        blackScore = 2;
        whiteScore = 2;
    }

    public int gameOver(){
        if(blackScore>whiteScore){
            System.out.println("BLACK WIN");
            return 1;
        }else if(blackScore<whiteScore){
            System.out.println("WHITE WIN");
            return -1;
        }else{
            System.out.println("PLAY EVEN");
            return 0;
        }
    }

    public void swapPlayer() {//交换玩家
        countScore();
        currentPlayer = (currentPlayer == ChessPiece.BLACK) ? ChessPiece.WHITE : ChessPiece.BLACK;
        statusPanel.setPlayerText(currentPlayer.name());
        statusPanel.setScoreText(blackScore, whiteScore);
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

    public void countScore() {
        //todo: modify the countScore method
        int blackScore = 0;
        int whiteScore =0 ;
        for(int i=0;i<8;++i){
            for(int j=0;j<8;++j){
                if (gamePanel.getChessGrids()[i][j].getChessPiece()==null){
                    ;
                }else if(translateColor(gamePanel.getChessGrids()[i][j].getChessPiece().getColor())==-1){
                    blackScore++;
                }else if(translateColor(gamePanel.getChessGrids()[i][j].getChessPiece().getColor())==1){
                    whiteScore++;
                }
            }
        }
        this.blackScore=blackScore;
        this.whiteScore=whiteScore;
    }


    public ChessPiece getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessBoardPanel getGamePanel() {
        return gamePanel;
    }


    public void setGamePanel(ChessBoardPanel gamePanel) {
        this.gamePanel = gamePanel;
    }


    public void readFileData(String fileName) {
        //todo: read date from file
        /*List<String> fileData = new ArrayList<>();
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileData));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileData.add(line);
            }
            fileData.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
         System.out.println(fileData.listIterator());
        //GameFrame.controller.getGamePanel()*/

        // TODO Auto-generated method stub
        try{

            BufferedReader br=new BufferedReader(new FileReader(fileName));
            String line=br.readLine();
            line = br.readLine();
            int num=0;
            int[][] data=new int[8][8];
            String currentPlayerData = " ";
            while(line!=null&&num<8){
                String[] temp=line.split(",");
                for(int i=0;i<temp.length;i++){
                    data[num][i]=Integer.parseInt(temp[i]);
                }
                line=br.readLine();
                num++;
            }
            currentPlayerData=line;
            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){
                    if(data[i][j]==1){
                        GameFrame.controller.getGamePanel().chessGrids[i][j].setChessPiece(ChessPiece.BLACK);
                    }else if(data[i][j]==2){
                        GameFrame.controller.getGamePanel().chessGrids[i][j].setChessPiece(ChessPiece.WHITE);
                    }
                }
            }
            System.out.println(currentPlayerData);
            if(currentPlayerData.equals("WHITE")){
                GameFrame.controller.currentPlayer=ChessPiece.WHITE;
                GameFrame.controller.statusPanel.setPlayerText("WHITE");
            }
        }catch (Exception e2) {
            // TODO: handle exception
            e2.printStackTrace();
        }

    }

    public void writeDataToFile(String fileName) {
        //todo: write data into file

        int[][] data=new int[8][8];
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(GameFrame.controller.getGamePanel().chessGrids[i][j].getChessPiece()==null) {
                    data[i][j] = 0;
                }
                else if(GameFrame.controller.getGamePanel().chessGrids[i][j].getChessPiece().getColor()==Color.WHITE) {
                    data[i][j] = 2;
                }
                else if(GameFrame.controller.getGamePanel().chessGrids[i][j].getChessPiece().getColor()==Color.BLACK){
                    data[i][j] = 1;
                }//空白棋盘为0
            }
        }


/*        try {
            file.createNewFile(); // 创建文件
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
        // 向文件写入内容(输出流)
        try {
            BufferedWriter in = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName,true)));
            try {in.write("\n");
                for (int i = 0; i <8 ; i++) {
                    for (int j = 0; j < 8; j++) {
                        in.write(String.format("%s,",String.valueOf(data[i][j])));
                        //System.out.printf("data[i][j] is %d\n",data[i][j]);

                    }in.write("\n");
                }
                in.write(String.format("%s",String.valueOf(currentPlayer.name())));
                in.close();
                // boolean success=true;
                System.out.println("写入文件成功");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public boolean canClick(int row, int col) {
        return gamePanel.canClickGrid(row, col, currentPlayer);
    }

}
