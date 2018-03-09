package erickhdez.com.tetris.tetris.pieces;

import erickhdez.com.tetris.MainActivity;

/**
 * Created by erickhdez on 4/3/18.
 */

public abstract class TetrisPiece {
    protected int previousPosition;
    protected int currentPosition;

    protected int resourceImage;
    protected Cell[][] positions;

    public TetrisPiece(int resourceImage) {
        currentPosition = 0;
        this.resourceImage = resourceImage;
    }

    public Cell[] getPositions() {
        return positions[currentPosition];
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void moveDown() {
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions[i].length; j++) {
                int newRow = positions[i][j].getRow() + 1;
                positions[i][j].setRow(newRow);
            }
        }
    }

    public void moveLeft() {
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions[i].length; j++) {
                int newColumn = positions[i][j].getColumn() - 1;
                positions[i][j].setColumn(newColumn);
            }
        }
    }

    public void moveRight() {
        for(int i = 0; i < positions.length; i++) {
            for(int j = 0; j < positions[i].length; j++) {
                int newColumn = positions[i][j].getColumn() + 1;
                positions[i][j].setColumn(newColumn);
            }
        }
    }

    public void rotate() {
        previousPosition = currentPosition;
        currentPosition++;
        currentPosition %= 4;
    }

    public void rotateBack() {
        currentPosition = previousPosition;
    }
}
