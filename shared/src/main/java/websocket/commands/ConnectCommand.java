package websocket.commands;

public class ConnectCommand extends UserGameCommand {

    public ConnectCommand(Integer gameID) {this.gameID = gameID;}


    Integer gameID;

    public Integer getGameID() {return gameID;}
}
