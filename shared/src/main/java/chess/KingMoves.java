package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class KingMoves {
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[]{}));
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();
        ChessPosition upSpace = new ChessPosition(currentRow + 1, currentColumn);
        ChessPosition downSpace = new ChessPosition(currentRow - 1, currentColumn);
        ChessPosition leftSpace = new ChessPosition(currentRow, currentColumn - 1);
        ChessPosition rightSpace = new ChessPosition(currentRow, currentColumn + 1);
        ChessPosition upLeftSpace = new ChessPosition(currentRow + 1, currentColumn - 1);
        ChessPosition upRightSpace = new ChessPosition(currentRow + 1, currentColumn + 1);
        ChessPosition downLeftSpace = new ChessPosition(currentRow - 1, currentColumn - 1);
        ChessPosition downRightSpace = new ChessPosition(currentRow - 1, currentColumn + 1);
        if (board.getPiece(upSpace) == null || board.getPiece(upSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(downSpace) == null || board.getPiece(downSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(leftSpace) == null || board.getPiece(leftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, leftSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(rightSpace) == null || board.getPiece(rightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, rightSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(upLeftSpace) == null || board.getPiece(upLeftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upLeftSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(upRightSpace) == null || board.getPiece(upRightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upRightSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(downLeftSpace) == null || board.getPiece(downLeftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downLeftSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(downRightSpace) == null || board.getPiece(downRightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downRightSpace, null);
            movesArray.add(newMove);
        }
        return movesArray;
    }
}
