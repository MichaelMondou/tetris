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
    int score;
    ArrayList<Line> lines;
    TextView scoreView;

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

        this.scoreView = (TextView) findViewById(R.id.scoreView);
        score = 0;
        this.lines = new ArrayList<>();
        initLines();
    }

    public void play() {
        moveLastPiece("down");
        if (!this.endOfGame) {
            isNeededOneMorePiece();
        } else {
            displayResetPanel();
        }
    }

    public void computeScore() {
        ArrayList<Integer> rows = new ArrayList<>();
        for (int i = this.adapter.getNbLines() - 1; i >= 0; i--) {
            int[] cases = this.lines.get(i).getCases();
            for (int j = 0; j < this.adapter.getNbColumns(); j++) {
                if (cases[j] == R.drawable.square) {
                    break;
                }
                if (j == this.adapter.getNbColumns() - 1) {
                    rows.add(i);
                }
            }
        }

        int score = 0;
        if (rows.size() == 1) {
            score = 40;
        } else if (rows.size() == 2) {
            score = 100;
        } else if (rows.size() == 3) {
            score = 300;
        } else if (rows.size() == 4) {
            score = 1200;
        }

        this.score += score;

        int[] cases = new int[this.adapter.getNbColumns()];
        for (int j=0; j < this.adapter.getNbColumns(); j++) {
            cases[j] = R.drawable.square;
        }

        for (int k = 0; k < rows.size(); k++) {
            this.lines.remove((int) rows.get(k));
        }
        for (int k = 0; k < rows.size(); k++) {
            this.lines.add(0, new Line(0, cases));
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
        adapter.resetGrid();
        this.initLines();
        this.score = 0;
        list = adapter.getmArrayList();
        pieces = new ArrayList<>();
        needMorePiece = true;
        endOfGame = false;
        resetLayout.setVisibility(View.INVISIBLE);
        resetButton.setVisibility(View.INVISIBLE);
        resetTextView.setVisibility(View.INVISIBLE);
    }

    public boolean canMoveDown(Tetromino piece) {
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.getMatrix()[i][j] == 1) {
                    if (i + 1 < piece.getHeight()) {
                        if (piece.getMatrix()[i + 1][j] == 1) {
                            continue;
                        }
                    }
                    if (this.lines.get(piece.getPos_i() + i + 1).getCases()[piece.getPos_j() + j] != R.drawable.square) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canMoveLeft(Tetromino piece) {
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.getMatrix()[i][j] == 1) {
                    if (j - 1 >= 0) {
                        if (piece.getMatrix()[i][j - 1] == 1) {
                            continue;
                        }
                    }
                    if (this.lines.get(piece.getPos_i() + i).getCases()[piece.getPos_j() + j - 1] != R.drawable.square) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canMoveRight(Tetromino piece) {
        for (int i = 0; i < piece.getHeight(); i++) {
            for (int j = 0; j < piece.getWidth(); j++) {
                if (piece.getMatrix()[i][j] == 1) {
                    if (j + 1 < piece.getWidth()) {
                        if (piece.getMatrix()[i][j + 1] == 1) {
                            continue;
                        }
                    }
                    if (this.lines.get(piece.getPos_i() + i).getCases()[piece.getPos_j() + j + 1] != R.drawable.square) {
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
                    showLastPlace(piece);
                    piece.down();
                } else {
                    computeScore();
                    needMorePiece = true;
                }
            } else if (Objects.equals(direction, "left")) {
                if (piece.isPossibleMovement(direction) && canMoveLeft(piece)) {
                    showLastPlace(piece);
                    piece.left();
                }
            } else if (Objects.equals(direction, "right")) {
                if (piece.isPossibleMovement(direction) && canMoveRight(piece)) {
                    showLastPlace(piece);
                    piece.right();
                }
            } else if (Objects.equals(direction, "rotate")) {
                if (piece.isPossibleMovement(direction) && canMoveRight(piece) && canMoveLeft(piece)) {
                    showLastPlace(piece);
                    piece.rotate();
                }
            }
        }
    }

    public void showLastPlace(Tetromino piece) {
        for (int k = 0; k < piece.getHeight(); k++) {
            for (int l = 0; l < piece.getWidth(); l++) {
                if (piece.getMatrix()[k][l] == 1) {
                    this.adapter.getGrid()[piece.getPos_i() + k][piece.getPos_j() + l] = -1;
                }
            }
        }
    }

    public void initLines() {
        this.lines.clear();
        for (int i = 0; i < this.adapter.getNbLines(); i++) {
            int[] cases = new int[this.adapter.getNbColumns()];
            for (int j=0; j < this.adapter.getNbColumns(); j++) {
                cases[j] = R.drawable.square;
            }
            Line line = new Line(i, cases);
            this.lines.add(line);
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

    public void updateGrid() {
        int[][] grid = this.adapter.getGrid();

        Tetromino piece = (Tetromino) pieces.get(pieces.size() - 1);

        for (int k = 0; k < piece.getHeight(); k++) {
            for (int l = 0; l < piece.getWidth(); l++) {
                if (piece.getMatrix()[k][l] == 1) {
                    grid[piece.getPos_i() + k][piece.getPos_j() + l] = piece.getColor();
                }
            }
        }
    }

    public void updateLines() {
        int[][] grid = this.adapter.getGrid();
        for (int i = 0; i < this.adapter.getNbLines(); i++) {
            int[] cases = new int[this.adapter.getNbColumns()];
            for (int j = 0; j < this.adapter.getNbColumns(); j++) {
                if (grid[i][j] == -1) {
                    cases[j] = R.drawable.square;
                }
                else if (grid[i][j] != R.drawable.square) {
                    cases[j] = grid[i][j];
                } else {
                    cases[j] = lines.get(i).getCases()[j];
                }
            }
            lines.get(i).setCases(cases);
        }
        this.adapter.resetGrid();
    }

    public void refresh() {
        updateGrid();
        updateLines();

        this.scoreView.setText("Score : " + this.score);

        for (int i = 0; i < this.adapter.getNbLines(); i++) {
            for (int j = 0; j < this.adapter.getNbColumns(); j++) {
                this.list.set(i * this.adapter.getNbColumns() + j, this.lines.get(i).getCases()[j]);
            }
        }

        this.adapter.updateResults(this.list);
    }
}