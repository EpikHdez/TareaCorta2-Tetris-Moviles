package erickhdez.com.tetris;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import erickhdez.com.tetris.tetris.factory.TetrisPiecesFactory;
import erickhdez.com.tetris.tetris.pieces.Cell;
import erickhdez.com.tetris.tetris.pieces.TetrisPiece;

public class GameActivity extends AppCompatActivity {
    public enum CellState {EMPTY, FILLED, PIECE}

    private static final int rowCompletedScore = 100;

    MediaPlayer mediaPlayer;
    TextView scoreTextView;

    private int rows;
    private int columns;
    private int score;

    private GridLayout playAreaLayout;
    private RelativeLayout gameOverLayout;

    private CellState[][] controlCells;
    private ImageView[][] uiCells;
    private ImageView[][] uiCellsNext;

    private TetrisPiece nextPiece = null;
    private TetrisPiece currentPiece = null;
    private boolean gameOver = false;
    private boolean canMove = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playAreaLayout = findViewById(R.id.playAreaLayout);
        gameOverLayout = findViewById(R.id.gameOverLayout);

        rows = playAreaLayout.getRowCount();
        columns = playAreaLayout.getColumnCount();

        controlCells = new CellState[rows][columns];
        uiCells = new ImageView[rows][columns];
        uiCellsNext = new ImageView[4][4];

        scoreTextView = findViewById(R.id.scoreTextView);
        score = 0;

        setUpControlCells();
        setUpUiCellsNext();
        setUpUiCells();
    }

    private void setUpControlCells() {
        for(int row = 0; row < rows; row++) {
            for(int column = 0; column < columns; column++) {
                controlCells[row][column] = CellState.EMPTY;
            }
        }
    }

    private void setUpUiCells() {
        View view;
        int[] position;

        for(int index = 0; index < playAreaLayout.getChildCount(); index++) {
            view = playAreaLayout.getChildAt(index);

            if(view instanceof ImageView) {
                position = getImageViewPosition((ImageView) view);

                uiCells[position[0]][position[1]] = (ImageView) view;
            }
        }
    }

    private void setUpUiCellsNext() {
        GridLayout nextLayout = findViewById(R.id.nextLayout);
        View view;
        int[] position;

        for(int index = 0; index < nextLayout.getChildCount(); index++) {
            view = nextLayout.getChildAt(index);

            if(view instanceof ImageView) {
                position = getImageViewPosition((ImageView) view);

                uiCellsNext[position[0]][position[1]] = (ImageView) view;
            }
        }
    }

    private int[] getImageViewPosition(ImageView imageView) {
        String[] tag = imageView.getTag().toString().split(",");
        int[] position = new int[2];

        position[0] = Integer.parseInt(tag[0]);
        position[1] = Integer.parseInt(tag[1]);

        return position;
    }

    @Override
    protected void onStart() {
        super.onStart();

        startGame();
    }

    private void checkForCompletedRows() {
        int rowCellsCount;
        int combo = 0;

        for (int row = rows - 1; row >= 0; row--) {
            rowCellsCount = 0;

            for (int column = columns - 1; column >= 0; column--) {
                if (controlCells[row][column] == CellState.FILLED) {
                    rowCellsCount++;
                }
            }

            if (rowCellsCount == columns) {
                deleteRow(row++);
                score += ++combo * rowCompletedScore;
            }
        }

        scoreTextView.setText(String.valueOf(score));
    }

    private void deleteRow(int startRow) {
        for(int row = startRow; row > 0; row--) {
            for (int column = 0; column < columns; column++) {
                controlCells[row][column] = controlCells[row - 1][column];
                uiCells[row][column].setImageDrawable(uiCells[row - 1][column].getDrawable());
            }
        }
    }

    private synchronized boolean collidesBelow() {
        for(Cell cell : currentPiece.getPositions()) {
            if(cell.getRow() + 1 >= rows ||
                    controlCells[cell.getRow() + 1][cell.getColumn()] == CellState.FILLED){
                return true;
            }
        }

        return false;
    }

    private boolean collidesLeft() {
        for(Cell cell : currentPiece.getPositions()) {
            if(cell.getColumn() - 1 < 0  ||
                    controlCells[cell.getRow()][cell.getColumn() - 1] == CellState.FILLED){
                return true;
            }
        }

        return false;
    }

    private boolean collidesRight() {
        for(Cell cell : currentPiece.getPositions()) {
            if(cell.getColumn() + 1 >= columns  ||
                    controlCells[cell.getRow()][cell.getColumn() + 1] == CellState.FILLED){
                return true;
            }
        }

        return false;
    }

    private synchronized void setPositionsAsFilled() {
        canMove = false;

        for(Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.FILLED;
        }

        checkForCompletedRows();
    }

    private synchronized void clearPreviousPostions() {
        for(Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.EMPTY;
            uiCells[cell.getRow()][cell.getColumn()].setImageResource(android.R.color.black);
        }
    }

    private synchronized void paintNewPositions() {
        for(Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.PIECE;
            uiCells[cell.getRow()][cell.getColumn()].setImageResource(currentPiece.getResourceImage());
        }
    }

    private void clearNextCells() {
        for(Cell cell : nextPiece.getPositions()) {
            uiCellsNext[cell.getRow()][cell.getColumn() % 3].setImageResource(android.R.color.black);
        }
    }

    private void paintNextPositions() {
        for(Cell cell : nextPiece.getPositions()) {
            uiCellsNext[cell.getRow()][cell.getColumn() % 3].setImageResource(nextPiece.getResourceImage());
        }
    }

    private synchronized void movePieceDown() {
        if(collidesBelow()) {
            setPositionsAsFilled();
            currentPiece = null;
            return;
        }

        clearPreviousPostions();
        currentPiece.moveDown();
        paintNewPositions();
    }

    private synchronized void movePieceLeft() {
        if(collidesLeft()) {
            return;
        }

        clearPreviousPostions();
        currentPiece.moveLeft();
        paintNewPositions();
    }

    private synchronized void movePieceRight() {
        if(collidesRight()) {
            return;
        }

        clearPreviousPostions();
        currentPiece.moveRight();
        paintNewPositions();
    }

    private void rotatePiece() {
        clearPreviousPostions();
        currentPiece.rotate();

        if(collidesRight() || collidesLeft()) {
            currentPiece.rotateBack();
        }

        paintNewPositions();
    }

    public void onBtnClicked(View view) throws IllegalStateException {
        if(!canMove) {
            return;
        }

        switch (view.getId()) {
            case R.id.btnDown:
                movePieceDown();
                break;

            case R.id.btnLeft:
                movePieceLeft();
                break;

            case R.id.btnRight:
                movePieceRight();
                break;

            case R.id.btnRotate:
                rotatePiece();
                break;

            default:
                return;
        }
    }

    private void startGame() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tetris_theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        nextPiece = TetrisPiecesFactory.createPiece(getApplicationContext());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(currentPiece == null) {
                            currentPiece = nextPiece;
                            clearNextCells();

                            nextPiece = TetrisPiecesFactory.createPiece(getApplicationContext());
                            paintNextPositions();

                            if(collidesBelow()) {
                                gameOver = true;


                                gameOverLayout.setVisibility(View.VISIBLE);
                                gameOverLayout.bringToFront();
                                gameOverLayout.invalidate();

                                return;
                            }

                            paintNewPositions();
                            canMove = true;
                        }

                        movePieceDown();
                    }
                });

                if(gameOver) {
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    this.cancel();
                }
            }
        }, 0, 1000);
    }
}
