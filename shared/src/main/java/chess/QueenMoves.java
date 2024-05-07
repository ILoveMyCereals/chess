package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class QueenMoves {
    public Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[] {}));
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

    //public ChessMove moves_helper(ChessBoard new_board, int row, int column) {}
}
//ChessPiece.PieceType.QUEEN for promotion piece to promote to queen
//use the ChessBoard.getPiece method and see if that is null to check if the space is a valid move or not
//use helper method and pass in two integers, a row and a column