package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 4/3/18.
 */

public abstract class TetrisPiece {
    protected int resourceImage;
    protected Cell[] positions;
    protected Cell[] valuesToAddOnRotate;

    public TetrisPiece(int resourceImage) {
        positions = new Cell[4];
        valuesToAddOnRotate = new Cell[4];
        this.resourceImage = resourceImage;
    }

    public Cell[] getPositions() {
        return positions;
    }

    public int getResourceImage() {
        return resourceImage;
    }

    public void moveDown() {
        for(int i = 0; i < positions.length; i++) {
            positions[i].setRow(positions[i].getRow() + 1);
        }
    }

    public void moveLeft() {
        for(int i = 0; i < positions.length; i++) {
            positions[i].setColumn(positions[i].getColumn() - 1);
        }
    }

    public void moveRight() {
        for(int i = 0; i < positions.length; i++) {
            positions[i].setColumn(positions[i].getColumn() + 1);
        }
    }

    public abstract void rotate();
}
