package chess;
import java.util.Collection;
import java.util.Arrays;
import java.util.ArrayList;

public class KingMoves {
    public static Collection<ChessMove> moves(ChessBoard board, ChessPosition startPosition) {
        Collection<ChessMove> movesArray = new ArrayList<ChessMove>(Arrays.asList(new ChessMove[]{}));
        Collection<ChessMove> upRightArray = moveHelper(board, startPosition, 1, 1, movesArray);
        Collection<ChessMove> downRightArray = moveHelper(board, startPosition, -1, 1, upRightArray);
        Collection<ChessMove> downLeftArray = moveHelper(board, startPosition, -1, -1, downRightArray);
        Collection<ChessMove> upLeftArray = moveHelper(board, startPosition, 1, -1, downLeftArray);
        Collection<ChessMove> upArray = moveHelper(board, startPosition, 1, 0, upLeftArray);
        Collection<ChessMove> rightArray = moveHelper(board, startPosition, 0, 1, upArray);
        Collection<ChessMove> downArray = moveHelper(board, startPosition, -1, 0, rightArray);
        Collection<ChessMove> finalArray = moveHelper(board, startPosition, 0, -1, downArray);

        return finalArray;
    }
    public static Collection<ChessMove> moveHelper(ChessBoard board, ChessPosition startPosition1, int rowInt, int colInt, Collection<ChessMove> currentArray) {
        int currentRow = startPosition1.getRow();
        int currentColumn = startPosition1.getColumn();

        if (currentRow >= 1 && currentColumn >= 1 && currentRow <= 8 && currentColumn <= 8) {
            ChessPosition nextSpace = new ChessPosition(currentRow + rowInt, currentColumn + colInt);
            ChessMove newMove = new ChessMove(startPosition1, nextSpace, null);
            if (nextSpace.getRow() >= 1 && nextSpace.getColumn() >= 1 && nextSpace.getRow() <= 8 && nextSpace.getColumn() <= 8) {
                if (board.getPiece(nextSpace) == null) {
                    currentArray.add(newMove);
                } else {
                    if (board.getPiece(nextSpace).getTeamColor() != board.getPiece(startPosition1).getTeamColor()) {
                        currentArray.add(newMove);
                    }
                }
            }
        }
        return currentArray;
    }
}
