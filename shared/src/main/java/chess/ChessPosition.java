package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPosition that = (ChessPosition) o;
        return rowPosition == that.rowPosition && colPosition == that.colPosition;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowPosition, colPosition);
    }

    public ChessPosition(int row, int col) {
        rowPosition = row;
        colPosition = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return rowPosition;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return colPosition;
    }

    private int rowPosition;
    private int colPosition;
}
