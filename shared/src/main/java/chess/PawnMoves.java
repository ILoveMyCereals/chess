package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class PawnMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[]{}));
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();
        ChessPosition upSpace = new ChessPosition(currentRow + 1, currentColumn);
        ChessPosition downSpace = new ChessPosition(currentRow - 1, currentColumn);
        ChessPosition doubleUpSpace = new ChessPosition(currentRow + 2, currentColumn);
        ChessPosition doubleDownSpace = new ChessPosition(currentRow - 2, currentColumn);
        ChessPosition upLeftSpace = new ChessPosition(currentRow + 1, currentColumn - 1);
        ChessPosition upRightSpace = new ChessPosition(currentRow + 1, currentColumn + 1);
        ChessPosition downLeftSpace = new ChessPosition(currentRow - 1, currentColumn - 1);
        ChessPosition downRightSpace = new ChessPosition(currentRow - 1, currentColumn + 1);

        if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.WHITE) {
            if (board.getPiece(upSpace) == null) {
                if (currentRow == 7) {
                    ChessMove queenPromote = new ChessMove(startPosition, upSpace, ChessPiece.PieceType.QUEEN);
                    movesArray.add(queenPromote);
                    ChessMove bishopPromote = new ChessMove(startPosition, upSpace, ChessPiece.PieceType.BISHOP);
                    movesArray.add(bishopPromote);
                    ChessMove rookPromote = new ChessMove(startPosition, upSpace, ChessPiece.PieceType.ROOK);
                    movesArray.add(rookPromote);
                    ChessMove knightPromote = new ChessMove(startPosition, upSpace, ChessPiece.PieceType.KNIGHT);
                    movesArray.add(knightPromote);
                } else {
                    ChessMove newMove = new ChessMove(startPosition, upSpace, null);
                    movesArray.add(newMove);
                }
            }
            if (currentRow == 2 && board.getPiece(upSpace) == null && board.getPiece(doubleUpSpace) == null) {
                ChessMove newMove = new ChessMove(startPosition, doubleUpSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(upLeftSpace) != null && board.getPiece(upLeftSpace).getTeamColor() == ChessGame.TeamColor.BLACK) {
                ChessMove newMove = new ChessMove(startPosition, upLeftSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(upRightSpace) != null && board.getPiece(upRightSpace).getTeamColor() == ChessGame.TeamColor.BLACK) {
                ChessMove newMove = new ChessMove(startPosition, upRightSpace, null);
                movesArray.add(newMove);
            }
        } else if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (board.getPiece(downSpace) == null) {
                if (currentRow == 2) {
                    ChessMove queenPromote = new ChessMove(startPosition, downSpace, ChessPiece.PieceType.QUEEN);
                    movesArray.add(queenPromote);
                    ChessMove bishopPromote = new ChessMove(startPosition, downSpace, ChessPiece.PieceType.BISHOP);
                    movesArray.add(bishopPromote);
                    ChessMove rookPromote = new ChessMove(startPosition, downSpace, ChessPiece.PieceType.ROOK);
                    movesArray.add(rookPromote);
                    ChessMove knightPromote = new ChessMove(startPosition, downSpace, ChessPiece.PieceType.KNIGHT);
                    movesArray.add(knightPromote);
                } else {
                    ChessMove newMove = new ChessMove(startPosition, downSpace, null);
                    movesArray.add(newMove);
                }
            }
            if (currentRow == 7 && board.getPiece(downSpace) == null && board.getPiece(doubleDownSpace) == null) {
                ChessMove newMove = new ChessMove(startPosition, doubleDownSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(downLeftSpace) != null && board.getPiece(downLeftSpace).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (currentRow == 2) {
                    ChessMove queenPromote = new ChessMove(startPosition, downLeftSpace, ChessPiece.PieceType.QUEEN);
                    movesArray.add(queenPromote);
                    ChessMove bishopPromote = new ChessMove(startPosition, downLeftSpace, ChessPiece.PieceType.BISHOP);
                    movesArray.add(bishopPromote);
                    ChessMove rookPromote = new ChessMove(startPosition, downLeftSpace, ChessPiece.PieceType.ROOK);
                    movesArray.add(rookPromote);
                    ChessMove knightPromote = new ChessMove(startPosition, downLeftSpace, ChessPiece.PieceType.KNIGHT);
                    movesArray.add(knightPromote);
                } else {
                    ChessMove newMove = new ChessMove(startPosition, downLeftSpace, null);
                    movesArray.add(newMove);
                }
            }
            if (board.getPiece(downRightSpace) != null && board.getPiece(downRightSpace).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (currentRow == 2) {
                    ChessMove queenPromote = new ChessMove(startPosition, downRightSpace, ChessPiece.PieceType.QUEEN);
                    movesArray.add(queenPromote);
                    ChessMove bishopPromote = new ChessMove(startPosition, downRightSpace, ChessPiece.PieceType.BISHOP);
                    movesArray.add(bishopPromote);
                    ChessMove rookPromote = new ChessMove(startPosition, downRightSpace, ChessPiece.PieceType.ROOK);
                    movesArray.add(rookPromote);
                    ChessMove knightPromote = new ChessMove(startPosition, downRightSpace, ChessPiece.PieceType.KNIGHT);
                    movesArray.add(knightPromote);
                } else {
                    ChessMove newMove = new ChessMove(startPosition, downRightSpace, null);
                    movesArray.add(newMove);
                }
            }
        }
        return movesArray;
    }
}
