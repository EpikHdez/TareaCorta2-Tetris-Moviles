package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 5/3/18.
 */

public class JPiece extends TetrisPiece {
    public JPiece(int resourceImage) {
        super(resourceImage);

        positions = new Cell[][] {
                {new Cell(0, 5), new Cell(1, 5), new Cell(2, 5), new Cell(2, 4)},
                {new Cell(0, 4), new Cell(0, 5), new Cell(0, 6), new Cell(1, 6)},
                {new Cell(0, 4), new Cell(1, 4), new Cell(2, 4), new Cell(0, 5)},
                {new Cell(0, 4), new Cell(1, 4), new Cell(1, 5), new Cell(1, 6)}
        };
    }
}
