package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class BishopMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {

        ChessPiece.PieceType type = ChessPiece.PieceType.BISHOP;
        PieceMoves moves = new PieceMoves();
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[] {}));

        Collection<ChessMove> upRightArray = moves.moveHelper(board, type, startPosition, 1, 1, movesArray);
        Collection<ChessMove> downRightArray = moves.moveHelper(board, type, startPosition, -1, 1, upRightArray);
        Collection<ChessMove> downLeftArray = moves.moveHelper(board, type, startPosition, -1, -1, downRightArray);
        Collection<ChessMove> finalArray = moves.moveHelper(board, type, startPosition, 1, -1, downLeftArray);

        return finalArray;
    }
}