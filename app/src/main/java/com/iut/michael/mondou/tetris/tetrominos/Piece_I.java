package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.IMovement;
import com.iut.michael.mondou.tetris.IPossibleMovement;
import com.iut.michael.mondou.tetris.R;

public class Piece_I extends Tetromino implements IMovement, IPossibleMovement {
    public Piece_I() {
        this.setHeight(1);
        this.setWidth(4);
        int[][] matrix = {{1, 1, 1, 1}};
        this.setMatrix(matrix);
        this.setPos_i(0);
        this.setPos_j(0);
        this.setColor(R.drawable.piece_i);
    }
}
