package websocket.commands;

public class LeaveCommand extends UserGameCommand {

    public LeaveCommand(Integer gameID) {this.gameID = gameID;}

    UserGameCommand.CommandType commandType = UserGameCommand.CommandType.LEAVE;

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
