package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_S extends Tetromino {
    public Piece_S() {
        this.setHeight(2);
        this.setWidth(3);
        int[][] matrix = {{0, 1, 1}, {1, 1, 0}};
        this.setMatrix(matrix);
        this.setPos_i(0);
        this.setPos_j(7);
        this.setColor(R.drawable.piece_s);
    }

    @Override
    public void rotate() {

    }
}
