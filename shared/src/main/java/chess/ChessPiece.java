package chess;

import java.util.Collection;
import java.util.Objects;

import static chess.ChessPiece.PieceColor.WHITE;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private ChessGame.TeamColor color1;
    private PieceType type1;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        color1 = pieceColor;
        type1 = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    public enum PieceColor {
        BLACK,
        WHITE
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return color1;
    }
    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type1;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection <ChessMove> movesList = BishopMoves.moves(board, myPosition);
        return movesList;
    }

    @Override
    public String toString() {
        return "ChessPiece{" +
                "color1=" + color1 +
                ", type1=" + type1 +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return Objects.equals(color1, that.color1) && type1 == that.type1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color1, type1);
    }
}
