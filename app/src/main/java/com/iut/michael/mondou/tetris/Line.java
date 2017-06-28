package com.iut.michael.mondou.tetris;

public class Line {
    private int pos_i;

    private int[] cases;

    Line(int pos_i, int[] cases) {
        this.pos_i = pos_i;
        this.cases = cases;
    }

    public int[] getCases() {
        return cases;
    }
    public void setCases(int[] cases) {
        this.cases = cases;
    }
}
