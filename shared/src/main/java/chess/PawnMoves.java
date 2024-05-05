package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class PawnMoves {
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
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
                ChessMove newMove = new ChessMove(startPosition, upSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(doubleUpSpace) == null && currentRow == 1) {
                ChessMove newMove = new ChessMove(startPosition, doubleUpSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(upLeftSpace).getTeamColor() == ChessGame.TeamColor.BLACK) {
                ChessMove newMove = new ChessMove(startPosition, upLeftSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(upRightSpace).getTeamColor() == ChessGame.TeamColor.BLACK) {
                ChessMove newMove = new ChessMove(startPosition, upRightSpace, null);
                movesArray.add(newMove);
            }
        } else if (board.getPiece(startPosition).getTeamColor() == ChessGame.TeamColor.BLACK) {
            if (board.getPiece(downSpace) == null) {
                ChessMove newMove = new ChessMove(startPosition, downSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(doubleDownSpace) == null && currentRow == 6) {
                ChessMove newMove = new ChessMove(startPosition, doubleDownSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(downLeftSpace).getTeamColor() == ChessGame.TeamColor.WHITE) {
                ChessMove newMove = new ChessMove(startPosition, downLeftSpace, null);
                movesArray.add(newMove);
            }
            if (board.getPiece(downRightSpace).getTeamColor() == ChessGame.TeamColor.WHITE) {
                ChessMove newMove = new ChessMove(startPosition, downRightSpace, null);
                movesArray.add(newMove);
            }
        }
        return movesArray;
    }
}
