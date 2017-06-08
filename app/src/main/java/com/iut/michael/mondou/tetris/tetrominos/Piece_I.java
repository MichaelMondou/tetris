package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_I extends Tetromino {
    public Piece_I() {
        this.setHeight(1);
        this.setWidth(4);
        int[][] matrix = {{1, 1, 1, 1}};
        this.setMatrix(matrix);
        this.setPos_i(17);
        this.setPos_j(3);
        this.setColor(R.drawable.piece_i);
    }
}
