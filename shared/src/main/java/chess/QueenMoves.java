package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class QueenMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {

        ChessPiece.PieceType type = ChessPiece.PieceType.QUEEN;
        PieceMoves moves = new PieceMoves();
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[]{}));

        Collection<ChessMove> upRightArray = moves.moveHelper(board, type, startPosition, 1, 1, movesArray);
        Collection<ChessMove> downRightArray = moves.moveHelper(board, type, startPosition, -1, 1, upRightArray);
        Collection<ChessMove> downLeftArray = moves.moveHelper(board, type, startPosition, -1, -1, downRightArray);
        Collection<ChessMove> upLeftArray = moves.moveHelper(board, type, startPosition, 1, -1, downLeftArray);
        Collection<ChessMove> upArray = moves.moveHelper(board, type, startPosition, 1, 0, upLeftArray);
        Collection<ChessMove> rightArray = moves.moveHelper(board, type, startPosition, 0, 1, upArray);
        Collection<ChessMove> downArray = moves.moveHelper(board, type, startPosition, -1, 0, rightArray);
        Collection<ChessMove> finalArray = moves.moveHelper(board, type, startPosition, 0, -1, downArray);


        return finalArray;
    }
}


//ChessPiece.PieceType.QUEEN for promotion piece to promote to queen
//use the ChessBoard.getPiece method and see if that is null to check if the space is a valid move or not
//use helper method and pass in two integers, a row and a column