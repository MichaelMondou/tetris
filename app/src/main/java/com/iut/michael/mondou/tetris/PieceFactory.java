package com.iut.michael.mondou.tetris;

import com.iut.michael.mondou.tetris.tetrominos.Piece_I;
import com.iut.michael.mondou.tetris.tetrominos.Piece_J;
import com.iut.michael.mondou.tetris.tetrominos.Piece_L;
import com.iut.michael.mondou.tetris.tetrominos.Piece_O;
import com.iut.michael.mondou.tetris.tetrominos.Piece_S;
import com.iut.michael.mondou.tetris.tetrominos.Piece_T;
import com.iut.michael.mondou.tetris.tetrominos.Piece_Z;

import java.util.Random;

class PieceFactory {

    PieceFactory() {}

    IMovement getRandomPiece() {
        Random rand = new Random();
        int randomNum = rand.nextInt((7 - 1) + 1) + 1;

        switch (randomNum) {
            case 1:
                return new Piece_I();
            case 2:
                return new Piece_J();
            case 3:
                return new Piece_L();
            case 4:
                return new Piece_O();
            case 5:
                return new Piece_S();
            case 6:
                return new Piece_T();
            case 7:
                return new Piece_Z();
            default:
                return new Piece_I();
        }
    }
}
