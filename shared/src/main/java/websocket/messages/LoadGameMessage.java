package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {

    public LoadGameMessage(ChessGame game) {this.game = game;}

    ChessGame game;

}
