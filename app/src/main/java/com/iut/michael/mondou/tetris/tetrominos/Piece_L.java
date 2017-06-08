package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.R;

public class Piece_L extends Tetromino {
    public Piece_L() {
        this.setHeight(2);
        this.setWidth(4);
        int[][] matrix = {{1, 0, 0, 0}, {1, 1, 1, 1}};
        this.setMatrix(matrix);
        this.setPos_i(14);
        this.setPos_j(4);
        this.setColor(R.drawable.piece_l);
    }
}
