package chess;

import java.util.Collection;

public class PieceMoves {

    public static Collection<ChessMove> moveHelper(ChessBoard board, ChessPiece.PieceType type, ChessPosition startPosition1, int rowInt, int colInt, Collection<ChessMove> currentArray) {
        int currentRow = startPosition1.getRow();
        int currentColumn = startPosition1.getColumn();

        if (type == ChessPiece.PieceType.QUEEN || type == ChessPiece.PieceType.BISHOP || type == ChessPiece.PieceType.ROOK) {

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
        }
        else {
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
        }
        return currentArray;
    }
}
