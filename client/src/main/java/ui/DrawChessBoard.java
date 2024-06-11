package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

import static ui.EscapeSequences.*;

public class DrawChessBoard {

    private static final int BOARD_SIZE_IN_SQUARES = 8;
    private static final int SQUARE_SIZE_IN_CHARS = 1;
    private static final int LINE_WIDTH_IN_CHARS = 0;
    private static final String EMPTY = "  ";
    private static final String PAWN = " P ";
    private static final String ROOK = " R ";
    private static final String KNIGHT = " N ";
    private static final String BISHOP = " B ";
    private static final String QUEEN = " Q ";
    private static final String KING = " K ";
    private static Random rand = new Random();
    private static String squareColor = "GREY";
    private static ChessBoard board = new ChessBoard();

    private static ArrayList<String> rows1 = new ArrayList<String>() {
        {
            add("1");
            add("2");
            add("3");
            add("4");
            add("5");
            add("6");
            add("7");
            add("8");
        }
    };

    private static ArrayList<String> rows2 = new ArrayList<String>() {
        {
            add("8");
            add("7");
            add("6");
            add("5");
            add("4");
            add("3");
            add("2");
            add("1");
        }
    };


    public static void main(String[] args) {
        drawChessBoard("WHITE");
    }

    public static void drawChessBoard(String teamColor) {
        board.resetBoard();

        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

        out.print(ERASE_SCREEN);

        drawHeaders(out, teamColor);

        drawTicTacToeBoard(out, teamColor);

        drawHeaders(out, teamColor);

        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void drawHeaders(PrintStream out, String teamColor) {

        setBlack(out);

        String[] headers = {"a", "b", "c", "d", "e", "f", "g", "h"};
        String[] headers2 = {"h", "g", "f", "e", "d", "c", "b", "a"};
        out.print(EMPTY);
        for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
            if (teamColor.equals("WHITE")) {
                drawHeader(out, headers[boardCol]);
            } else if (teamColor.equals("BLACK")) {
                drawHeader(out, headers2[boardCol]);
            }

            if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
            }
        }

        out.println();
    }

    private static void drawHeader(PrintStream out, String headerText) {
        int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
        int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

        out.print(EMPTY.repeat(prefixLength));
        printHeaderText(out, headerText);
        out.print(EMPTY.repeat(suffixLength));
    }

    private static void printHeaderText(PrintStream out, String player) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(player);

        setBlack(out);
    }

    private static void drawTicTacToeBoard(PrintStream out, String teamColor) {

        for (int boardRow = 0; boardRow < BOARD_SIZE_IN_SQUARES; ++boardRow) {

            drawRowOfSquares(out, boardRow, teamColor);

            if (boardRow < BOARD_SIZE_IN_SQUARES - 1) {
                //drawVerticalLine(out);
                setBlack(out);
            }
        }
    }

    private static void drawRowOfSquares(PrintStream out, int rowNum, String teamColor) {

        for (int squareRow = 0; squareRow < SQUARE_SIZE_IN_CHARS; ++squareRow) {
            if (teamColor.equals("WHITE")) {
                String nextNum = rows1.get(0);
                printHeaderChar(out, nextNum);
            } else if (teamColor.equals("BLACK")) {
                String nextNum = rows2.get(0);
                printHeaderChar(out, nextNum);
            }

            for (int boardCol = 0; boardCol < BOARD_SIZE_IN_SQUARES; ++boardCol) {
                setWhite(out);

                if (squareRow == SQUARE_SIZE_IN_CHARS / 2) {
                    int prefixLength = SQUARE_SIZE_IN_CHARS / 2;
                    int suffixLength = SQUARE_SIZE_IN_CHARS - prefixLength - 1;

                    out.print(EMPTY.repeat(prefixLength));
                    ChessPosition currentPosition = new ChessPosition(rowNum + 1, boardCol + 1);
                    ChessPiece currentPiece = board.getPiece(currentPosition);
                    printPlayer(out, currentPiece, squareColor);
                    if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                        if (squareColor == "WHITE") {
                            squareColor = "GREY";
                        } else {
                            squareColor = "WHITE";
                        }
                    }
                    out.print(EMPTY.repeat(suffixLength));
                }
                else {
                    out.print(EMPTY.repeat(SQUARE_SIZE_IN_CHARS));
                }

                if (boardCol < BOARD_SIZE_IN_SQUARES - 1) {
                    // Draw right line
                    setRed(out);
                    out.print(EMPTY.repeat(LINE_WIDTH_IN_CHARS));
                }

                setBlack(out);
            }

            if (teamColor.equals("WHITE")) {
                String nextRightNum = rows1.get(0);
                printHeaderChar(out, nextRightNum);
                rows1.remove(0);
            }
            else if (teamColor.equals("BLACK")) {
                String nextRightNum = rows2.get(0);
                printHeaderChar(out, nextRightNum);
                rows2.remove(0);
            }
            out.println();
        }
    }


    private static void setWhite(PrintStream out) {
        out.print(SET_BG_COLOR_WHITE);
        out.print(SET_TEXT_COLOR_WHITE);
    }

    private static void setRed(PrintStream out) {
        out.print(SET_BG_COLOR_RED);
        out.print(SET_TEXT_COLOR_RED);
    }

    private static void setBlack(PrintStream out) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_BLACK);
    }

    private static void printPlayer(PrintStream out, ChessPiece piece, String squareColor) {
        String player;
        if (squareColor == "GREY") {
            out.print(SET_BG_COLOR_WHITE);
        }
        else {
            out.print(SET_BG_COLOR_LIGHT_GREY);
        }

        if (piece == null) {
            player = EMPTY + " ";
        }

        else {
            if (piece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                out.print(SET_TEXT_COLOR_BLUE);
            } else {
                out.print(SET_TEXT_COLOR_RED);
            }
            ChessPiece.PieceType type = piece.getPieceType();

            if (type == ChessPiece.PieceType.PAWN) {
                player = PAWN;
            } else if (type == ChessPiece.PieceType.ROOK) {
                player = ROOK;
            } else if (type == ChessPiece.PieceType.KNIGHT) {
                player = KNIGHT;
            } else if (type == ChessPiece.PieceType.BISHOP) {
                player = BISHOP;
            } else if (type == ChessPiece.PieceType.QUEEN) {
                player = QUEEN;
            } else if (type == ChessPiece.PieceType.KING) {
                player = KING;
            } else {
                player = " ";
            }
        }
        out.print(player);

        setWhite(out);
    }

    private static void printHeaderChar(PrintStream out, String headerText) {
        out.print(SET_BG_COLOR_BLACK);
        out.print(SET_TEXT_COLOR_GREEN);

        out.print(headerText);
    }
}

