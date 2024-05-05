package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class KnightMoves {
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[]{}));
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();
        ChessPosition upRightSpace = new ChessPosition(currentRow + 2, currentColumn + 1);
        ChessPosition rightUpSpace = new ChessPosition(currentRow + 1, currentColumn + 2);
        ChessPosition rightDownSpace = new ChessPosition(currentRow - 1, currentColumn + 2);
        ChessPosition downRightSpace = new ChessPosition(currentRow - 2, currentColumn + 1);
        ChessPosition downLeftSpace = new ChessPosition(currentRow -2, currentColumn - 1);
        ChessPosition leftDownSpace = new ChessPosition(currentRow - 1, currentColumn -2);
        ChessPosition leftUpSpace = new ChessPosition(currentRow + 1, currentColumn - 2);
        ChessPosition upLeftSpace = new ChessPosition(currentRow + 2, currentColumn - 1);

        if (board.getPiece(upRightSpace) == null || board.getPiece(upRightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upRightSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(rightUpSpace) == null || board.getPiece(rightUpSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, rightUpSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(rightDownSpace) == null || board.getPiece(rightDownSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, rightDownSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(downRightSpace) == null || board.getPiece(downRightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downRightSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(downLeftSpace) == null || board.getPiece(downLeftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downLeftSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(leftDownSpace) == null || board.getPiece(leftDownSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, leftDownSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(leftUpSpace) == null || board.getPiece(leftUpSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, leftUpSpace, null);
            movesArray.add(newMove);
        }
        if (board.getPiece(upLeftSpace) == null || board.getPiece(upLeftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upLeftSpace, null);
            movesArray.add(newMove);
        }

        return movesArray;
    }
}
