package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 5/3/18.
 */

public class SquarePiece extends TetrisPiece {
    public SquarePiece(int resourceImage) {
        super(resourceImage);

        positions = new Cell[][] {
                {new Cell(0, 4), new Cell(0, 5), new Cell(1, 4), new Cell(1, 5)}
        };
    }

    @Override
    public void rotate() {
        return;
    }
}
