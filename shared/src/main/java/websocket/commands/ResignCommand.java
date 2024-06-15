package websocket.commands;

public class ResignCommand extends UserGameCommand {

    public ResignCommand(String authToken, Integer gameID) {

        super(authToken);

        this.gameID = gameID;
    }

    Integer gameID;

    public Integer getGameID() {return gameID;}
}
