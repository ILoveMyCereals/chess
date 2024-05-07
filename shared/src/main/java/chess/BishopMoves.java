package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class BishopMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[] {}));
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();

        Collection<ChessMove> upRightArray = moveHelper(board, startPosition, 1, 1, movesArray);
        Collection<ChessMove> downRightArray = moveHelper(board, startPosition, -1, 1, upRightArray);
        Collection<ChessMove> downLeftArray = moveHelper(board, startPosition, -1, -1, downRightArray);
        Collection<ChessMove> finalArray = moveHelper(board, startPosition, 1, -1, downLeftArray);

        /*
        ChessPosition upLeftSpace = new ChessPosition(currentRow + 1, currentColumn - 1);
        ChessPosition upRightSpace = new ChessPosition(currentRow + 1, currentColumn + 1);
        ChessPosition downLeftSpace = new ChessPosition(currentRow - 1, currentColumn - 1);
        ChessPosition downRightSpace = new ChessPosition(currentRow - 1, currentColumn + 1);

        while (currentRow < 8 && currentColumn >= 0 && board.getPiece(upLeftSpace) == null) {
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
        while (board.getPiece(upRightSpace) == null && currentRow < 8 && currentColumn < 8) {
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
        while (board.getPiece(downLeftSpace) == null && currentRow > 0 && currentColumn > 0) {
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
        while (board.getPiece(downRightSpace) == null && currentRow > 0 && currentColumn < 0) {
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
         */
        return finalArray;
    }

    public static Collection<ChessMove> moveHelper(ChessBoard board, ChessPosition startPosition, int rowInt, int colInt, Collection<ChessMove> currentArray) {
        int newRow = rowInt;
        int newCol = colInt;
        int currentRow = startPosition.getRow();
        int currentColumn = startPosition.getColumn();

        while (currentRow > 1 && currentColumn > 1 && currentRow < 8 && currentColumn < 8) {
            ChessPosition nextSpace = new ChessPosition(currentRow + rowInt, currentColumn + colInt);
            ChessMove newMove = new ChessMove(startPosition, nextSpace, null);
            if (board.getPiece(nextSpace) == null) {
                currentArray.add(newMove);
            } else {
                if (board.getPiece(nextSpace).getTeamColor() != board.getPiece(startPosition).getTeamColor()) {
                    currentArray.add(newMove);
                }
                break;
            }
            currentRow += rowInt;
            currentColumn += colInt; //THESE TWO LINES SHOULD BE HERE ONLY FOR THE QUEEN, BISHOP AND ROOK
        }
        return currentArray;
    }
}

/*
moves helper function:


Parameters: accepts board, start position, row integer, column integer, array?

new variables for row and column integers

while within the boundaries of the board:

check for piece at start position plus row and column integers:
	If null, add the piece to the array of moves
	Else, check if the piece is of the opposing color
		If so, add to the array of moves
		break

return the array
 */