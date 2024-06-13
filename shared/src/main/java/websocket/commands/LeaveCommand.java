package websocket.commands;

public class LeaveCommand extends UserGameCommand {

    public LeaveCommand(Integer gameID) {this.gameID = gameID;}

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
