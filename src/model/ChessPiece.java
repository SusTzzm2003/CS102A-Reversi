package model;

import java.awt.*;

public enum ChessPiece/*棋子*/ {
    BLACK(Color.BLACK), WHITE(Color.WHITE),yellow(Color.yellow);


    private final Color color;

    ChessPiece(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

}
