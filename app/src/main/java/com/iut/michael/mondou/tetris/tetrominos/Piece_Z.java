package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_Z extends Tetromino {
    public Piece_Z() {
        this.setHeight(2);
        this.setWidth(3);
        int[][] matrix = {{1, 1, 0}, {0, 1, 1}};
        this.setMatrix(matrix);
        this.setPos_i(18);
        this.setPos_j(7);
        this.setColor(R.drawable.piece_z);
    }
}
