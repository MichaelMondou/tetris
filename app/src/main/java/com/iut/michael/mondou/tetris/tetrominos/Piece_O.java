package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_O extends Tetromino {
    public Piece_O() {
        this.setHeight(2);
        this.setWidth(2);
        int[][] matrix = {{1, 1}, {1, 1}};
        this.setMatrix(matrix);
        this.setPos_i(0);
        this.setPos_j(6);
        this.setColor(R.drawable.piece_o);
    }
}
