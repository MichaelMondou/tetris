package com.iut.michael.mondou.tetris;

public interface IMovement {
    static int LEFT = 1;
    static int DOWN = 2;
    static int RIGHT = 3;
    static int ROTATE = 4;

    void left();
    void down();
    void right();
    void rotate();
}
