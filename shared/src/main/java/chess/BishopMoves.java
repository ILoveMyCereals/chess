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

        return finalArray;
    }

    public static Collection<ChessMove> moveHelper(ChessBoard board, ChessPosition startPosition1, int rowInt, int colInt, Collection<ChessMove> currentArray) {
        int currentRow = startPosition1.getRow();
        int currentColumn = startPosition1.getColumn();

        while (currentRow >= 1 && currentColumn >= 1 && currentRow <= 8 && currentColumn <= 8) {
            ChessPosition nextSpace = new ChessPosition(currentRow + rowInt, currentColumn + colInt);
            ChessMove newMove = new ChessMove(startPosition1, nextSpace, null);
            if (nextSpace.getRow() >= 1 && nextSpace.getColumn() >= 1 && nextSpace.getRow() <= 8 && nextSpace.getColumn() <= 8) {
            if (board.getPiece(nextSpace) == null) {
                currentArray.add(newMove);
            } else {
                if (board.getPiece(nextSpace).getTeamColor() != board.getPiece(startPosition1).getTeamColor()) {
                    currentArray.add(newMove);
                }
                break;
            }
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