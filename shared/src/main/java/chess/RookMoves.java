package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class RookMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {

        ChessPiece.PieceType type = ChessPiece.PieceType.ROOK;
        PieceMoves moves = new PieceMoves();
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[] {}));

        Collection<ChessMove> upArray = moves.moveHelper(board, type, startPosition, 1, 0, movesArray);
        Collection<ChessMove> rightArray = moves.moveHelper(board, type, startPosition, 0, 1, upArray);
        Collection<ChessMove> downArray = moves.moveHelper(board, type, startPosition, -1, 0, rightArray);
        Collection<ChessMove> finalArray = moves.moveHelper(board, type, startPosition, 0, -1, downArray);

        return finalArray;
    }
}
