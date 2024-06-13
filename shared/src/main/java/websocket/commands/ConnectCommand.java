package websocket.commands;

public class ConnectCommand extends UserGameCommand {

    public ConnectCommand(Integer gameID) {this.gameID = gameID;}


    CommandType commandType = CommandType.CONNECT;

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
