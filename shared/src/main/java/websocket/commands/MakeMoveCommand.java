package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    public MakeMoveCommand(Integer gameID, ChessMove move) {this.gameID = gameID; this.move = move;}

    Integer gameID;

    ChessMove move;

    public Integer getGameID() {return gameID;}

    public ChessMove getMove() {return move;}
}
