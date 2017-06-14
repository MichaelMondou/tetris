package com.iut.michael.mondou.tetris;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iut.michael.mondou.tetris.tetrominos.Tetromino;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ImageAdapter adapter;
    ArrayList<IMovement> pieces;
    ArrayList<Integer> list;
    PieceFactory pieceFactory;
    boolean needMorePiece;
    boolean endOfGame;
    LinearLayout layout;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.pieceFactory = new PieceFactory();

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        this.adapter = new ImageAdapter(this);
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

        this.list = this.adapter.getmArrayList();

        this.pieces = new ArrayList<>();

        this.needMorePiece = true;
        this.endOfGame = false;

        this.layout = (LinearLayout) findViewById(R.id.linearLayout);
        layout.setVisibility(View.INVISIBLE);

        this.button = (Button) findViewById(R.id.button);
        button.setVisibility(View.INVISIBLE);

        this.textView = (TextView) findViewById(R.id.textView);
        textView.setVisibility(View.INVISIBLE);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                play();
                refresh();
                handler.postDelayed(this, 100);
            }
        };
        handler.post(r);
    }

    public void play() {
        moveLastPiece();
        if (!this.endOfGame) {
            isNeededOneMorePiece();
        } else {
            displayResetPanel();
        }
    }

    public void displayResetPanel() {
        layout.setVisibility(View.VISIBLE);
        button.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetData();
            }
        });
    }

    public void resetData() {
        adapter.initGrid();
        list = adapter.getmArrayList();
        pieces = new ArrayList<>();
        needMorePiece = true;
        endOfGame = false;
        layout.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
    }

    public boolean canMoveDown(Tetromino piece) {
        int[][] grid = this.adapter.getGrid();
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.getMatrix()[i][j] == 1) {
                    if (i + 1 < piece.getHeight()) {
                        if (piece.getMatrix()[i + 1][j] == 1) {
                            continue;
                        }
                    }
                    if (grid[piece.getPos_i() + i + 1][piece.getPos_j() + j] != R.drawable.square) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveLastPiece() {
        if (pieces.size() > 0) {
            Tetromino piece = (Tetromino) pieces.get(pieces.size() - 1);
            endOfGame(piece);
            if (piece.isPossibleMovement() && canMoveDown(piece)) {
                piece.down();
            } else {
                needMorePiece = true;
            }
        }
    }

    public void endOfGame(Tetromino piece) {
        if (piece.getPos_i() == 0 && !canMoveDown(piece)) {
            this.endOfGame = true;
        }
    }

    public void isNeededOneMorePiece() {
        if (needMorePiece) {
            IMovement item = this.pieceFactory.getRandomPiece();
            Tetromino newItem = (Tetromino) item;
            pieces.add(newItem);
            needMorePiece = false;
        }
    }

    public void refresh() {
        this.adapter.resetGrid();
        int[][] grid = this.adapter.getGrid();

        for (IMovement item : this.pieces) {
            Tetromino piece = (Tetromino) item;
            for (int i = 0; i < piece.getHeight(); i++) {
                for (int j = 0; j < piece.getWidth(); j++) {
                    if (piece.getMatrix()[i][j] == 1) {
                        grid[piece.getPos_i() + i][piece.getPos_j() + j] = piece.getColor();
                    }
                }
            }
        }

        for (int i = 0; i < this.adapter.getNbLines(); i++) {
            for (int j = 0; j < this.adapter.getNbColumns(); j++) {
                this.list.set(i * this.adapter.getNbColumns() + j, grid[i][j]);
            }
        }

        this.adapter.updateResults(this.list);
    }
}