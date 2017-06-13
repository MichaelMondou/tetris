package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_T extends Tetromino {
    public Piece_T() {
        this.setHeight(2);
        this.setWidth(3);
        int[][] matrix = {{1, 1, 1}, {0, 1, 0}};
        this.setMatrix(matrix);
        this.setPos_i(0);
        this.setPos_j(4);
        this.setColor(R.drawable.piece_t);
    }

    @Override
    public void rotate() {

    }

    @Override
    public void left() {

    }

    @Override
    public void right() {

    }
}
