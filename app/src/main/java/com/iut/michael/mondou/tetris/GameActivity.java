package com.iut.michael.mondou.tetris;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iut.michael.mondou.tetris.tetrominos.Tetromino;

import java.util.ArrayList;

public class GameActivity extends Activity {

    static int LEFT = 1;
    static int DOWN = 2;
    static int RIGHT = 3;
    static int ROTATE = 4;

    ImageAdapter m_adapter;
    PieceFactory m_pieceFactory;
    ArrayList<IMovement> m_pieces;
    ArrayList<Integer> m_list;
    ArrayList<Line> m_lines;
    LinearLayout m_resetLayout;
    Button m_resetButton;
    ImageButton m_menuImageButton;
    ImageButton m_pauseImageButton;
    ImageButton m_muteImageButton;
    TextView m_scoreView;
    TextView m_highScoreView;
    MediaPlayer m_mediaPlayer;
    int m_score;
    int m_highScore;
    boolean m_needMorePiece;
    boolean m_endOfGame;
    boolean m_playState = true;
    boolean m_musicState = true;

    GestureDetector m_gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        m_pieceFactory = new PieceFactory();

        final GridView gridview = (GridView) findViewById(R.id.gridview);
        m_adapter = new ImageAdapter(this);
        gridview.setAdapter(m_adapter);

        Android_Gesture_Detector android_gesture_detector  =  new Android_Gesture_Detector(this);
        m_gestureDetector = new GestureDetector(this, android_gesture_detector);

        gridview.setOnTouchListener(new OnSwipeListener(this));

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

        m_list = m_adapter.getArrayList();

        m_pieces = new ArrayList<>();

        m_needMorePiece = true;
        m_endOfGame = false;

        m_resetLayout = (LinearLayout) findViewById(R.id.resetLayout);
        m_resetLayout.setVisibility(View.INVISIBLE);

        m_resetButton = (Button) findViewById(R.id.resetButton);
        m_resetButton.setVisibility(View.INVISIBLE);

        m_menuImageButton = (ImageButton) findViewById(R.id.menuButton);
        m_menuImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        m_pauseImageButton = (ImageButton) findViewById(R.id.pauseButton);
        m_pauseImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (m_playState) {
                    m_playState = false;
                    m_pauseImageButton.setBackgroundResource(R.drawable.play);
                } else {
                    m_playState = true;
                    m_pauseImageButton.setBackgroundResource(R.drawable.pause);
                }
            }
        });

        final Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                if (m_playState) {
                    play();
                    refresh();
                }
                handler.postDelayed(this, 500);
            }
        };
        handler.post(r);

        m_scoreView = (TextView) findViewById(R.id.scoreView);
        m_highScoreView = (TextView) findViewById(R.id.highScoreView);
        m_score = 0;
        SharedPreferences prefs = this.getSharedPreferences(
                "m_highScore", Context.MODE_PRIVATE);
        m_highScore = prefs.getInt("m_highScore", 0);
        m_highScoreView.setText(getString(R.string.best_score) + " : " + m_highScore);
        m_lines = new ArrayList<>();
        initLines();

        m_mediaPlayer = MediaPlayer.create(this, R.raw.song);
        m_mediaPlayer.start();

        m_muteImageButton = (ImageButton) findViewById(R.id.muteButton);
        m_muteImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (m_musicState) {
                    m_musicState = false;
                    m_muteImageButton.setBackgroundResource(R.drawable.mute);
                    m_mediaPlayer.setVolume(0.0f,0.0f);
                } else {
                    m_musicState = true;
                    m_muteImageButton.setBackgroundResource(R.drawable.music);
                    m_mediaPlayer.setVolume(1.0f,1.0f);
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (m_mediaPlayer != null) {
            m_mediaPlayer.pause();
            if (isFinishing()) {
                m_mediaPlayer.stop();
                m_mediaPlayer.release();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        m_gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void play() {
        moveLastPiece(DOWN);
        if (!m_endOfGame) {
            isNeededOneMorePiece();
        } else {
            saveScore();
            displayResetPanel();
        }
    }

    public void saveScore() {
        if (m_score > m_highScore) {
            SharedPreferences prefs = this.getSharedPreferences(
                    "m_highScore", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("m_highScore", m_score);
            editor.apply();
            m_highScoreView.setText(getString(R.string.best_score) + " : " + m_highScore);
        }
    }

    public void computeScore() {
        ArrayList<Integer> rows = new ArrayList<>();
        for (int i = m_adapter.getNbLines() - 1; i >= 0; i--) {
            int[] cases = m_lines.get(i).getCases();
            for (int j = 0; j < m_adapter.getNbColumns(); j++) {
                if (cases[j] == R.drawable.square) {
                    break;
                }
                if (j == m_adapter.getNbColumns() - 1) {
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

        m_score += score;

        int[] cases = new int[m_adapter.getNbColumns()];
        for (int j = 0; j < m_adapter.getNbColumns(); j++) {
            cases[j] = R.drawable.square;
        }

        for (int k = 0; k < rows.size(); k++) {
            m_lines.remove((int) rows.get(k));
        }
        for (int k = 0; k < rows.size(); k++) {
            m_lines.add(0, new Line(0, cases));
        }
    }

    public void displayResetPanel() {
        m_resetLayout.setVisibility(View.VISIBLE);
        m_resetButton.setVisibility(View.VISIBLE);

        m_resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetData();
            }
        });
    }

    public void resetData() {
        m_adapter.resetGrid();
        initLines();
        m_score = 0;
        m_list = m_adapter.getArrayList();
        m_pieces = new ArrayList<>();
        m_needMorePiece = true;
        m_endOfGame = false;
        m_resetLayout.setVisibility(View.INVISIBLE);
        m_resetButton.setVisibility(View.INVISIBLE);
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
                    if (m_lines.get(piece.getPos_i() + i + 1).getCases()[piece.getPos_j() + j] != R.drawable.square) {
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
                    if (m_lines.get(piece.getPos_i() + i).getCases()[piece.getPos_j() + j - 1] != R.drawable.square) {
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
                    if (m_lines.get(piece.getPos_i() + i).getCases()[piece.getPos_j() + j + 1] != R.drawable.square) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void moveLastPiece(int direction) {
        if (m_pieces.size() > 0 && m_playState) {
            Tetromino piece = (Tetromino) m_pieces.get(m_pieces.size() - 1);
            endOfGame(piece);

            if (direction == DOWN) {
                if (piece.isPossibleMovement(direction) && canMoveDown(piece)) {
                    showLastPlace(piece);
                    piece.down();
                } else {
                    computeScore();
                    m_scoreView.setText("@string/m_score" + " : " + m_score);
                    m_needMorePiece = true;
                }
            } else if (direction == LEFT) {
                if (piece.isPossibleMovement(direction) && canMoveLeft(piece)) {
                    showLastPlace(piece);
                    piece.left();
                }
            } else if (direction == RIGHT) {
                if (piece.isPossibleMovement(direction) && canMoveRight(piece)) {
                    showLastPlace(piece);
                    piece.right();
                }
            } else if (direction == ROTATE) {
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
                    m_adapter.getGrid()[piece.getPos_i() + k][piece.getPos_j() + l] = -1;
                }
            }
        }
    }

    public void initLines() {
        m_lines.clear();
        for (int i = 0; i < m_adapter.getNbLines(); i++) {
            int[] cases = new int[m_adapter.getNbColumns()];
            for (int j = 0; j < m_adapter.getNbColumns(); j++) {
                cases[j] = R.drawable.square;
            }
            Line line = new Line(i, cases);
            m_lines.add(line);
        }
    }

    public void endOfGame(Tetromino piece) {
        if (piece.getPos_i() == 0 && !canMoveDown(piece)) {
            m_endOfGame = true;
        }
    }

    public void isNeededOneMorePiece() {
        if (m_needMorePiece) {
            IMovement item = m_pieceFactory.getRandomPiece();
            Tetromino newItem = (Tetromino) item;
            m_pieces.add(newItem);
            m_needMorePiece = false;
        }
    }

    public void updateGrid() {
        int[][] grid = m_adapter.getGrid();

        Tetromino piece = (Tetromino) m_pieces.get(m_pieces.size() - 1);

        for (int k = 0; k < piece.getHeight(); k++) {
            for (int l = 0; l < piece.getWidth(); l++) {
                if (piece.getMatrix()[k][l] == 1) {
                    grid[piece.getPos_i() + k][piece.getPos_j() + l] = piece.getColor();
                }
            }
        }
    }

    public void updateLines() {
        int[][] grid = m_adapter.getGrid();
        for (int i = 0; i < m_adapter.getNbLines(); i++) {
            int[] cases = new int[m_adapter.getNbColumns()];
            for (int j = 0; j < m_adapter.getNbColumns(); j++) {
                if (grid[i][j] == -1) {
                    cases[j] = R.drawable.square;
                } else if (grid[i][j] != R.drawable.square) {
                    cases[j] = grid[i][j];
                } else {
                    cases[j] = m_lines.get(i).getCases()[j];
                }
            }
            m_lines.get(i).setCases(cases);
        }
        m_adapter.resetGrid();
    }

    public void refresh() {
        updateGrid();
        updateLines();

        m_scoreView.setText(getString(R.string.score) + " : " + m_score);

        for (int i = 0; i < m_adapter.getNbLines(); i++) {
            for (int j = 0; j < m_adapter.getNbColumns(); j++) {
                m_list.set(i * m_adapter.getNbColumns() + j, m_lines.get(i).getCases()[j]);
            }
        }

        m_adapter.updateResults(m_list);
    }
}