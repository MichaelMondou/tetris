package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_L extends Tetromino {
    public Piece_L() {
        this.setHeight(2);
        this.setWidth(3);
        int[][] matrix = {{1, 1, 1}, {1, 0, 0}};
        this.setMatrix(matrix);
        this.setPos_i(0);
        this.setPos_j(2);
        this.setColor(R.drawable.piece_l);
    }
}