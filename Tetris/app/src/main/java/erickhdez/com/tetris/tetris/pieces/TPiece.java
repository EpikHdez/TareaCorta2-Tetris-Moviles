package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 5/3/18.
 */

public class TPiece extends TetrisPiece {
    public TPiece(int resourceImage) {
        super(resourceImage);

        positions = new Cell[][] {
                {new Cell(0, 6), new Cell(1, 5), new Cell(1, 6), new Cell(1, 7)},
                {new Cell(1, 5), new Cell(0, 6), new Cell(1, 6), new Cell(2, 6)},
                {new Cell(0, 5), new Cell(0, 6), new Cell(0, 7), new Cell(1, 6)},
                {new Cell(0, 5), new Cell(1, 5), new Cell(2, 5), new Cell(1, 6)}
        };
    }
}
