package erickhdez.com.tetris.tetris.pieces;

/**
 * Created by erickhdez on 5/3/18.
 */

public class IPiece extends TetrisPiece {
    public IPiece(int resourceImage) {
        super(resourceImage);

        positions[0] = new Cell(0, 5);
        positions[1] = new Cell(1, 5);
        positions[2] = new Cell(2, 5);
        positions[3] = new Cell(3, 5);

        valuesToAddOnRotate[0] = new Cell(1, -1);
        valuesToAddOnRotate[1] = new Cell(0,0);
        valuesToAddOnRotate[2] = new Cell(-1, 1);
        valuesToAddOnRotate[3] = new Cell(-2, 2);
    }

    @Override
    public void rotate() {
        for(int index = 0; index < positions.length; index++) {
            int currentRow = positions[index].getRow();
            int currentColumn = positions[index].getColumn();

            int addRowValue = valuesToAddOnRotate[index].getRow();
            int addColumnValue = valuesToAddOnRotate[index].getColumn();

            int newRow = currentRow + addRowValue;
            int newColumn = currentColumn + addColumnValue;

            positions[index].setRow(newRow);
            positions[index].setColumn(newColumn);

            valuesToAddOnRotate[index].setRow(currentRow - newRow);
            valuesToAddOnRotate[index].setColumn(currentColumn - newColumn);
        }
    }
}
