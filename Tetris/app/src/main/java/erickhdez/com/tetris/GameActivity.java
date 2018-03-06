package erickhdez.com.tetris;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import erickhdez.com.tetris.tetris.factory.TetrisPiecesFactory;
import erickhdez.com.tetris.tetris.pieces.Cell;
import erickhdez.com.tetris.tetris.pieces.TetrisPiece;

public class GameActivity extends AppCompatActivity {
    public enum CellState {EMPTY, WALL, FILLED, PIECE}
    public enum MoveState {FREE, RESTRAINEDLEFT, RESTRAINEDRIGHT, STOP}

    private LayoutInflater layoutInflater;

    private int rowsCount;
    private int columnsCount;

    private ImageView[][] screenCells;
    private CellState[][] controlCells;

    private TetrisPiece currentPiece = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initVariables();
        setUpGridLayout();

        startGame();
    }

    private void initVariables() {
        GridLayout playArea = findViewById(R.id.playArea);

        rowsCount = playArea.getRowCount();
        columnsCount = playArea.getColumnCount();

        screenCells = new ImageView[rowsCount][columnsCount];
        controlCells = new CellState[rowsCount][columnsCount];
    }

    private void setUpGridLayout() {
        GridLayout playArea = findViewById(R.id.playArea);
        View view;

        for(int position = 0; position < playArea.getChildCount(); position++) {
            view = playArea.getChildAt(position);

            if(view instanceof ImageView) {
                setUpImageView((ImageView) view);
            }
        }
    }

    private void setUpImageView(ImageView imageView) {
        String tag = imageView.getTag().toString();
        String[] position = tag.split(",");

        int row = Integer.parseInt(position[0]);
        int column = Integer.parseInt(position[1]);

        screenCells[row][column] = imageView;

        if(isWall(row, column)) {
            imageView.setImageResource(R.drawable.tg);
            controlCells[row][column] = CellState.WALL;
        } else {
            imageView.setImageResource(R.color.backgroung);
            controlCells[row][column] = CellState.EMPTY;
        }
    }

    private boolean isWall(int row, int column) {
        if(row + 1 == rowsCount || column == 0 || column + 1 == columnsCount) {
            return true;
        }

        return false;
    }

    private synchronized MoveState collides() {
        for(Cell cell : currentPiece.getPositions()) {
            int row = cell.getRow();
            int column = cell.getColumn();

            if(controlCells[row + 1][column] == CellState.FILLED ||
                    controlCells[row + 1][column] == CellState.WALL) {
                return MoveState.STOP;
            }

            if(controlCells[row][column - 1] == CellState.WALL ||
                    controlCells[row][column - 1] == CellState.FILLED) {
                return MoveState.RESTRAINEDLEFT;
            }

            if(controlCells[row][column + 1] == CellState.WALL ||
                    controlCells[row][column + 1] == CellState.FILLED) {
                return MoveState.RESTRAINEDRIGHT;
            }
        }

        return MoveState.FREE;
    }

    public void onBtnClicked(View view) {
        if(!(view instanceof Button)) {
            return;
        }

        MoveState currentState = collides();

        if(currentState == MoveState.STOP) {
            return;
        }

        clearPreviousPositions();

        switch (view.getId()) {
            case R.id.btnDown:
                currentPiece.moveDown();
                break;

            case R.id.btnLeft:
                if(currentState != MoveState.RESTRAINEDLEFT) {
                    currentPiece.moveLeft();
                }
                break;

            case R.id.btnRight:
                if(currentState != MoveState.RESTRAINEDRIGHT) {
                    currentPiece.moveRight();
                }
                break;

            default:
                return;
        }

        paintNewPositions();
    }

    private synchronized void clearPreviousPositions() {
        for(Cell cell : currentPiece.getPositions()) {
            screenCells[cell.getRow()][cell.getColumn()].setImageResource(R.color.backgroung);
            controlCells[cell.getRow()][cell.getColumn()] = CellState.EMPTY;
        }
    }

    private synchronized void paintNewPositions() {
        int imageId = currentPiece.getResourceImage();

        for(Cell cell : currentPiece.getPositions()) {
            screenCells[cell.getRow()][cell.getColumn()].setImageResource(imageId);
            controlCells[cell.getRow()][cell.getColumn()] = CellState.PIECE;
        }
    }

    private synchronized void setPositionsAsFilled() {
        for (Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.FILLED;
        }
    }

    private void startGame() {
        final Handler handler = new Handler();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    MoveState currentState;

                    @Override
                    public void run() {
                        if(currentPiece == null) {
                            currentPiece = TetrisPiecesFactory.createPiece(getApplicationContext());
                            paintNewPositions();
                        }

                        currentState = collides();

                        if(currentState == MoveState.FREE ||
                                currentState == MoveState.RESTRAINEDLEFT ||
                                currentState == MoveState.RESTRAINEDRIGHT) {
                            clearPreviousPositions();
                            currentPiece.moveDown();
                            paintNewPositions();
                        } else {
                            setPositionsAsFilled();
                            currentPiece = null;
                        }
                    }
                });
            }
        }, 0, 1000);
    }
}
