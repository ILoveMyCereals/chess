package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {

    public MakeMoveCommand(String authToken, Integer gameID, ChessMove move) {

        super(authToken);

        this.commandType = CommandType.MAKE_MOVE;

        this.gameID = gameID;
        this.move = move;}

    Integer gameID;

    ChessMove move;

    public Integer getGameID() {return gameID;}

    public ChessMove getMove() {return move;}
}
