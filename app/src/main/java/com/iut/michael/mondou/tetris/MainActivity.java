package com.iut.michael.mondou.tetris;

import android.os.Bundle;
import android.app.Activity;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.iut.michael.mondou.tetris.tetrominos.Piece_I;
import com.iut.michael.mondou.tetris.tetrominos.Piece_J;
import com.iut.michael.mondou.tetris.tetrominos.Piece_L;
import com.iut.michael.mondou.tetris.tetrominos.Piece_O;
import com.iut.michael.mondou.tetris.tetrominos.Piece_S;
import com.iut.michael.mondou.tetris.tetrominos.Piece_T;
import com.iut.michael.mondou.tetris.tetrominos.Piece_Z;
import com.iut.michael.mondou.tetris.tetrominos.Tetromino;

import java.util.ArrayList;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        ImageAdapter adapter = new ImageAdapter(this);
        gridview.setAdapter(adapter);

        gridview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gridview.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int iDisplayWidth = gridview.getWidth();

                int nbColumns = gridview.getNumColumns();
                int iImageSize = iDisplayWidth / nbColumns - nbColumns;

                gridview.setColumnWidth(iImageSize);
            }
        });

        ArrayList<Integer> list = adapter.getmArrayList();

        Piece_O piece_o = new Piece_O();
        Piece_I piece_i = new Piece_I();
        Piece_T piece_t = new Piece_T();
        Piece_L piece_l = new Piece_L();
        Piece_J piece_j = new Piece_J();
        Piece_Z piece_z = new Piece_Z();
        Piece_S piece_s = new Piece_S();

        ArrayList<Tetromino> pieces = new ArrayList<>();

        pieces.add(piece_o);
        pieces.add(piece_i);
        pieces.add(piece_t);
        pieces.add(piece_l);
        pieces.add(piece_j);
        pieces.add(piece_z);
        pieces.add(piece_s);

        int[][] grid = adapter.getGrid();

        for (Tetromino piece : pieces) {
            for (int i=0; i<piece.getHeight(); i++) {
                for (int j=0; j<piece.getWidth(); j++) {
                    if (piece.getMatrix()[i][j] == 1) {
                        grid[piece.getPos_i() + i][piece.getPos_j() + j] = piece.getColor();
                    }
                }
            }
        }

        for (int i=0; i<adapter.getNbLines(); i++) {
            for (int j=0; j<adapter.getNbColumns(); j++) {
                list.set(i * adapter.getNbColumns() + j, grid[i][j]);
            }
        }
    }
}