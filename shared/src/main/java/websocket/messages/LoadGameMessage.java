package websocket.messages;

import chess.ChessGame;

public class LoadGameMessage extends ServerMessage {

    public LoadGameMessage(ChessGame game) {
        super(ServerMessageType.LOAD_GAME);

        this.game = game;
    }

    public ChessGame getGame() {
        return game;
    }

    ChessGame game;

}
