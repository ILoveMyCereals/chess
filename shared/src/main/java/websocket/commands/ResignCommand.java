package websocket.commands;

public class ResignCommand {

    public ResignCommand(Integer gameID) {this.gameID = gameID;}

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
