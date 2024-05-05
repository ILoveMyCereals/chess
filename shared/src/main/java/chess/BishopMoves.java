package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class BishopMoves {
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[] {}));
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();
        ChessPosition upLeftSpace = new ChessPosition(currentRow + 1, currentColumn - 1);
        ChessPosition upRightSpace = new ChessPosition(currentRow + 1, currentColumn + 1);
        ChessPosition downLeftSpace = new ChessPosition(currentRow - 1, currentColumn - 1);
        ChessPosition downRightSpace = new ChessPosition(currentRow - 1, currentColumn + 1);
        while (board.getPiece(upLeftSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, upLeftSpace, null);
            movesArray.add(newMove);
            currentColumn -= 1;
            currentRow += 1;
            upLeftSpace = new ChessPosition(currentRow + 1, currentColumn - 1);
        }
        if (board.getPiece(upLeftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upLeftSpace, null);
            movesArray.add(newMove);
        }
        while (board.getPiece(upRightSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, upRightSpace, null);
            movesArray.add(newMove);
            currentColumn += 1;
            currentRow += 1;
            upRightSpace = new ChessPosition(currentRow + 1, currentColumn + 1);
        }
        if (board.getPiece(upRightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upRightSpace, null);
            movesArray.add(newMove);
        }
        while (board.getPiece(downLeftSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, downLeftSpace, null);
            movesArray.add(newMove);
            currentColumn -= 1;
            currentRow -= 1;
            downLeftSpace = new ChessPosition(currentRow - 1, currentColumn - 1);
        }
        if (board.getPiece(downLeftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downLeftSpace, null);
            movesArray.add(newMove);
        }
        while (board.getPiece(downRightSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, downRightSpace, null);
            movesArray.add(newMove);
            currentColumn += 1;
            currentRow -= 1;
            downRightSpace = new ChessPosition(currentRow - 1, currentColumn + 1);
        }
        if (board.getPiece(downRightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downRightSpace, null);
            movesArray.add(newMove);
        }
        return movesArray;
    }
}
