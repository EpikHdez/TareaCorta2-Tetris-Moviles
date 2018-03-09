package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 5/3/18.
 */

public class SquarePiece extends TetrisPiece {
    public SquarePiece(int resourceImage) {
        super(resourceImage);

        positions = new Cell[][] {
                {new Cell(0, 5), new Cell(0, 6), new Cell(1, 5), new Cell(1, 6)}
        };
    }

    @Override
    public void rotate() {
        return;
    }
}
