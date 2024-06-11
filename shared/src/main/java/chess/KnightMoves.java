package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class KnightMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {

        ChessPiece.PieceType type = ChessPiece.PieceType.KNIGHT;
        PieceMoves moves = new PieceMoves();
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[]{}));

        Collection<ChessMove> upRightArray = moves.moveHelper(board, type, startPosition, 2, 1, movesArray);
        Collection<ChessMove> rightUpArray = moves.moveHelper(board, type, startPosition, 1, 2, upRightArray);
        Collection<ChessMove> rightDownArray = moves.moveHelper(board, type, startPosition, -1, 2, rightUpArray);
        Collection<ChessMove> downRightArray = moves.moveHelper(board, type, startPosition, -2, 1, rightDownArray);
        Collection<ChessMove> downLeftArray = moves.moveHelper(board, type, startPosition, -2, -1, downRightArray);
        Collection<ChessMove> leftDownArray = moves.moveHelper(board, type, startPosition, -1, -2, downLeftArray);
        Collection<ChessMove> leftUpArray = moves.moveHelper(board, type, startPosition, 1, -2, leftDownArray);
        Collection<ChessMove> finalArray = moves.moveHelper(board, type, startPosition, 2, -1, leftUpArray);

        return finalArray;
    }
}
