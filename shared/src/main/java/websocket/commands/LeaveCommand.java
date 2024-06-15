package websocket.commands;

public class LeaveCommand extends UserGameCommand {

    public LeaveCommand(String authToken, Integer gameID) {

        super(authToken);

        this.commandType = commandType.LEAVE;

        this.gameID = gameID;
    }

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
