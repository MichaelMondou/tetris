package com.iut.michael.mondou.tetris.tetrominos;

import com.iut.michael.mondou.tetris.IMovement;
import com.iut.michael.mondou.tetris.IPossibleMovement;

import java.util.Objects;

public abstract class Tetromino implements IMovement, IPossibleMovement {
    private int height;
    private int width;
    private int[][] matrix;
    private int pos_i;
    private int pos_j;
    private int color;

    public int getHeight() {
        return height;
    }

    void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    void setWidth(int width) {
        this.width = width;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public int getPos_i() {
        return pos_i;
    }

    void setPos_i(int pos_i) {
        this.pos_i = pos_i;
    }

    public int getPos_j() {
        return pos_j;
    }

    void setPos_j(int pos_j) {
        this.pos_j = pos_j;
    }

    public int getColor() {
        return color;
    }

    void setColor(int color) {
        this.color = color;
    }

    @Override
    public void down() {
        setPos_i(getPos_i() + 1);
    }

    @Override
    public void left() { setPos_j(getPos_j() - 1); }

    @Override
    public void right() { setPos_j(getPos_j() + 1);}

    @Override
    public void rotate() {

    }

    @Override
    public boolean isPossibleMovement(String direction) {
        if (Objects.equals(direction, "down")) {
            return getPos_i() + getHeight() + 1 <= 20;
        } else if (Objects.equals(direction, "left")) {
            return getPos_j() - 1 >= 0;
        } else if (Objects.equals(direction, "right")) {
            return getPos_j() + getWidth() < 10;
        }
        return false;
    }
}
