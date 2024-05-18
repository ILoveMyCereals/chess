package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private ChessBoard board;
    private TeamColor teamTurn;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> validMoves = new ArrayList<ChessMove>();
        ChessPiece newPiece = board.getPiece(startPosition);
        Collection<ChessMove> startArray = newPiece.pieceMoves(board, startPosition);
        TeamColor color = newPiece.getTeamColor();
        ChessBoard board1;
        try {
            board1 = board.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
        for (ChessMove move : startArray) {
            try {
                //ChessBoard newBoard = board.clone();
                board.addPiece(move.getEndPosition(), newPiece);
                board.addPiece(move.getStartPosition(), null);
                if (isInCheck(color) == false) {
                    validMoves.add(move);
                }
                board = board1.clone();
            } catch (CloneNotSupportedException ex) {
                return null;
            }
        }
        board = board1;
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessBoard newBoard;
        try {
            newBoard = board.clone();
        } catch (CloneNotSupportedException ex) {
            return;
        }
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        if (board.getPiece(startPosition) == null || board.getPiece(startPosition).getTeamColor() != teamTurn) {
            throw new InvalidMoveException("Invalid move");
        }
        if (validMoves(startPosition).contains(move)) {
            ChessPiece newPiece = board.getPiece(startPosition);
            board.addPiece(endPosition, newPiece);
            board.addPiece(startPosition, null);
        } else {
            throw new InvalidMoveException("Invalid move");
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */

    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = getKingPosition(teamColor);
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition newPosition = new ChessPosition(i, j);
                ChessPiece newPiece = board.getPiece(newPosition);
                if (newPiece != null) {
                    Collection<ChessMove> moves = newPiece.pieceMoves(board, newPosition);
                    for (ChessMove move : moves) {
                        ChessPosition endPosition = move.getEndPosition();
                        if (endPosition.equals(kingPosition)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ChessPosition getKingPosition(TeamColor teamColor) {
        ChessPosition kingPosition = null;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPosition newPosition = new ChessPosition(i, j);
                ChessPiece newPiece = board.getPiece(newPosition);
                if (newPiece != null && newPiece.getTeamColor() == teamColor && newPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(i, j);
                }
            }
        }
        return kingPosition;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        boolean inCheckmate = true;
        if (isInCheck(teamColor) == false) {
            return false;
        } else {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPosition = new ChessPosition(i, j);
                    if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == teamColor) {
                        Collection<ChessMove> validArray = validMoves(newPosition);
                        if (validArray.size() > 0) {
                            inCheckmate = false;
                        }
                    }
                }
            }
        }
        return inCheckmate;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        boolean inStalemate = true;
        if (isInCheck(teamColor) == true) {
            return false;
        } else {
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 8; j++) {
                    ChessPosition newPosition = new ChessPosition(i, j);
                    if (board.getPiece(newPosition) != null && board.getPiece(newPosition).getTeamColor() == teamColor) {
                        Collection<ChessMove> validArray = validMoves(newPosition);
                        if (validArray.size() > 0) {
                            inStalemate = false;
                        }
                    }
                }
            }
        }
        return inStalemate;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "board=" + board +
                ", teamTurn=" + teamTurn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(board, chessGame.board) && teamTurn == chessGame.teamTurn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, teamTurn);
    }
}
