package erickhdez.com.tetris.tetris.factory;

import android.content.Context;
import android.content.res.Resources;

import java.util.Random;

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
    private enum PieceType {IPIECE, SPIECE, ZPIECE, LPIECE, JPIECE, SQUAREPIECE, TPIECE}

    private static String[] images = {"tam", "taz", "tc", "tm", "tn", "tr", "tv"};

    public static TetrisPiece createPiece(Context context) {
        Random random = new Random();

        int pieceTypeIndex = Math.abs(random.nextInt() % PieceType.values().length);
        PieceType piece = PieceType.values()[pieceTypeIndex];

        int imageIndex = Math.abs(random.nextInt() % images.length);
        String imageName = images[imageIndex];
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(imageName, "drawable", context.getPackageName());

        switch (piece) {
            case JPIECE:
                return new JPiece(resourceId);

            case LPIECE:
                return new LPiece(resourceId);

            case ZPIECE:
                return new ZPiece(resourceId);

            case SPIECE:
                return new SPiece(resourceId);

            case IPIECE:
                return new IPiece(resourceId);

            case TPIECE:
                return new TPiece(resourceId);

            default:
                return new SquarePiece(resourceId);
        }
    }
}
