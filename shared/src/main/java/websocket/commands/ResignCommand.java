package websocket.commands;

public class ResignCommand {

    public ResignCommand(Integer gameID) {this.gameID = gameID;}

    UserGameCommand.CommandType commandType = UserGameCommand.CommandType.RESIGN;

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
