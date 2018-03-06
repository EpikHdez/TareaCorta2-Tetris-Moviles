package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 4/3/18.
 */

public class LPiece extends TetrisPiece {
    public LPiece(int resourceImage) {
        super(resourceImage);

        positions[0] = new Cell(0, 6);
        positions[1] = new Cell(1, 6);
        positions[2] = new Cell(2, 6);
        positions[3] = new Cell(2, 7);
    }
}
