package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 4/3/18.
 */

public class LPiece extends TetrisPiece {
    public LPiece(int resourceImage) {
        super(resourceImage);

        positions[0] = new Cell(0, 5);
        positions[1] = new Cell(1, 5);
        positions[2] = new Cell(2, 5);
        positions[3] = new Cell(2, 6);
    }

    @Override
    public void rotate() {

    }
}
