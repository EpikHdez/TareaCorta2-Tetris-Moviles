package erickhdez.com.tetris;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import erickhdez.com.tetris.tetris.pieces.Cell;
import erickhdez.com.tetris.tetris.pieces.IPiece;
import erickhdez.com.tetris.tetris.pieces.TetrisPiece;

public class GameActivity extends AppCompatActivity {
    public enum CellState {EMPTY, FILLED, PIECE}

    private int rows;
    private int columns;

    private GridLayout playAreaLayout;
    private CellState[][] controlCells;
    private ImageView[][] uiCells;

    private TetrisPiece currentPiece = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        playAreaLayout = findViewById(R.id.playAreaLayout);
        rows = playAreaLayout.getRowCount();
        columns = playAreaLayout.getColumnCount();

        controlCells = new CellState[rows][columns];
        uiCells = new ImageView[rows][columns];

        setUpControlCells();
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
        for(Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.FILLED;
        }
    }

    private synchronized void clearPreviousPostions() {
        for(Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.EMPTY;
            uiCells[cell.getRow()][cell.getColumn()].setImageResource(R.color.background);
        }
    }

    private synchronized void paintNewPositions() {
        for(Cell cell : currentPiece.getPositions()) {
            controlCells[cell.getRow()][cell.getColumn()] = CellState.PIECE;
            uiCells[cell.getRow()][cell.getColumn()].setImageResource(currentPiece.getResourceImage());
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
            currentPiece.rotate();
        }

        paintNewPositions();
    }

    public void onBtnClicked(View view) {
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
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(currentPiece == null) {
                            currentPiece = new IPiece(R.drawable.taz);
                            paintNewPositions();
                        }

                        movePieceDown();
                    }
                });
            }
        }, 0, 700);
    }
}
