package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_J extends Tetromino {
    public Piece_J() {
        this.setHeight(2);
        this.setWidth(4);
        int[][] matrix = {{1, 1, 1, 1}, {0, 0, 0, 1}};
        this.setMatrix(matrix);
        this.setPos_i(0);
        this.setPos_j(1);
        this.setColor(R.drawable.piece_j);
    }

    @Override
    public void rotate() {

    }
}
