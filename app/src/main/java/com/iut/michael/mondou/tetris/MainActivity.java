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
import java.util.Objects;

public class MainActivity extends Activity {

    ImageAdapter adapter;
    ArrayList<IMovement> pieces;
    ArrayList<Integer> list;
    PieceFactory pieceFactory;
    boolean needMorePiece;
    boolean endOfGame;
    LinearLayout resetLayout;
    Button resetButton;
    Button leftButton;
    Button downButton;
    Button rightButton;
    Button rotateButton;
    TextView resetTextView;

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

        this.resetLayout = (LinearLayout) findViewById(R.id.resetLayout);
        resetLayout.setVisibility(View.INVISIBLE);

        this.resetButton = (Button) findViewById(R.id.resetButton);
        resetButton.setVisibility(View.INVISIBLE);

        this.leftButton = (Button) findViewById(R.id.leftButton);
        this.downButton = (Button) findViewById(R.id.downButton);
        this.rightButton = (Button) findViewById(R.id.rightButton);
        this.rotateButton = (Button) findViewById(R.id.rotateButton);

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveLastPiece("left");
                refresh();
            }
        });
        downButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveLastPiece("down");
                refresh();
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveLastPiece("right");
                refresh();
            }
        });
        rotateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                moveLastPiece("rotate");
                refresh();
            }
        });

        this.resetTextView = (TextView) findViewById(R.id.resetTextView);
        resetTextView.setVisibility(View.INVISIBLE);

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                play();
                refresh();
                handler.postDelayed(this, 500);
            }
        };
        handler.post(r);
    }

    public void play() {
        moveLastPiece("down");
        if (!this.endOfGame) {
            isNeededOneMorePiece();
        } else {
            displayResetPanel();
        }
    }

    public void displayResetPanel() {
        resetLayout.setVisibility(View.VISIBLE);
        resetButton.setVisibility(View.VISIBLE);
        resetTextView.setVisibility(View.VISIBLE);

        resetButton.setOnClickListener(new View.OnClickListener() {
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
        resetLayout.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        resetTextView.setVisibility(View.INVISIBLE);
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

    public boolean canMoveLeft(Tetromino piece) {
        int[][] grid = this.adapter.getGrid();
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.getMatrix()[i][j] == 1) {
                    if (j - 1 >= 0) {
                        if (piece.getMatrix()[i][j - 1] == 1) {
                            continue;
                        }
                    }
                    if (grid[piece.getPos_i() + i][piece.getPos_j() + j  - 1] != R.drawable.square) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canMoveRight(Tetromino piece) {
        int[][] grid = this.adapter.getGrid();
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.getMatrix()[i][j] == 1) {
                    if (j + 1 < piece.getWidth()) {
                        if (piece.getMatrix()[i][j + 1] == 1) {
                            continue;
                        }
                    }
                    if (grid[piece.getPos_i() + i][piece.getPos_j() + j  + 1] != R.drawable.square) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveLastPiece(String direction) {
        if (pieces.size() > 0) {
            Tetromino piece = (Tetromino) pieces.get(pieces.size() - 1);
            endOfGame(piece);

            if (Objects.equals(direction, "down")) {
                if (piece.isPossibleMovement(direction) && canMoveDown(piece)) {
                    piece.down();
                } else {
                    needMorePiece = true;
                }
            } else if (Objects.equals(direction, "left")) {
                if (piece.isPossibleMovement(direction) && canMoveLeft(piece)) {
                    piece.left();
                }
            } else if (Objects.equals(direction, "right")) {
                if (piece.isPossibleMovement(direction) && canMoveRight(piece)) {
                    piece.right();
                }
            } else if (Objects.equals(direction, "rotate")) {
                if (piece.isPossibleMovement(direction) && canMoveRight(piece) && canMoveLeft(piece)) {
                    piece.rotate();
                }
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