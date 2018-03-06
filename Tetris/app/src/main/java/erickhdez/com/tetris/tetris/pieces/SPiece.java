package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 5/3/18.
 */

public class SPiece extends TetrisPiece {
    public SPiece(int resourceImage) {
        super(resourceImage);

        positions[0] = new Cell(0, 6);
        positions[1] = new Cell(1, 6);
        positions[2] = new Cell(1, 7);
        positions[3] = new Cell(2, 7);
    }
}
