package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class RookMoves {
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[] {}));
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();
        ChessPosition upSpace = new ChessPosition(currentRow + 1, currentColumn);
        ChessPosition downSpace = new ChessPosition(currentRow - 1, currentColumn);
        ChessPosition leftSpace = new ChessPosition(currentRow, currentColumn - 1);
        ChessPosition rightSpace = new ChessPosition(currentRow, currentColumn + 1);
        while (board.getPiece(upSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, upSpace, null);
            movesArray.add(newMove);
            currentRow += 1;
            upSpace = new ChessPosition(currentRow + 1, currentColumn);
        }
        if (board.getPiece(upSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, upSpace, null);
            movesArray.add(newMove);
        }
        while (board.getPiece(downSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, downSpace, null);
            movesArray.add(newMove);
            currentRow -= 1;
            downSpace = new ChessPosition(currentRow - 1, currentColumn);
        }
        if (board.getPiece(downSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, downSpace, null);
            movesArray.add(newMove);
        }
        while (board.getPiece(leftSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, leftSpace, null);
            movesArray.add(newMove);
            currentColumn -= 1;
            leftSpace = new ChessPosition(currentRow, currentColumn - 1);
        }
        if (board.getPiece(leftSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, leftSpace, null);
            movesArray.add(newMove);
        }
        while (board.getPiece(rightSpace) == null) {
            ChessMove newMove = new ChessMove(startPosition, rightSpace, null);
            movesArray.add(newMove);
            currentColumn += 1;
            rightSpace = new ChessPosition(currentRow, currentColumn + 1);
        }
        if (board.getPiece(rightSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
            ChessMove newMove = new ChessMove(startPosition, rightSpace, null);
            movesArray.add(newMove);
        }
        return movesArray;
    }
}
