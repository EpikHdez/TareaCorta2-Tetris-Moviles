package erickhdez.com.tetris.tetris.factory;

import android.content.Context;
import android.content.res.Resources;

import java.util.Random;

import erickhdez.com.tetris.R;
import erickhdez.com.tetris.tetris.pieces.IPiece;
import erickhdez.com.tetris.tetris.pieces.JPiece;
import erickhdez.com.tetris.tetris.pieces.LPiece;
import erickhdez.com.tetris.tetris.pieces.SPiece;
import erickhdez.com.tetris.tetris.pieces.SquarePiece;
import erickhdez.com.tetris.tetris.pieces.TPiece;
import erickhdez.com.tetris.tetris.pieces.TetrisPiece;
import erickhdez.com.tetris.tetris.pieces.ZPiece;

/**
 * Created by erickhdez on 4/3/18.
 */

public class TetrisPiecesFactory {
    private enum PieceType {IPiece, SPiece, ZPiece, LPiece, JPiece, SquarePiece, TPiece}

    public static TetrisPiece createPiece(Context context) {
        Random random = new Random();

        int pieceTypeIndex = Math.abs(random.nextInt() % PieceType.values().length);
        PieceType piece = PieceType.values()[pieceTypeIndex];

        switch (piece) {
            case JPiece:
                return new JPiece(R.drawable.taz);

            case LPiece:
                return new LPiece(R.drawable.tn);

            case ZPiece:
                return new ZPiece(R.drawable.tr);

            case SPiece:
                return new SPiece(R.drawable.tv);

            case IPiece:
                return new IPiece(R.drawable.tc);

            case TPiece:
                return new TPiece(R.drawable.tm);

            default:
                return new SquarePiece(R.drawable.tam);
        }
    }
}
