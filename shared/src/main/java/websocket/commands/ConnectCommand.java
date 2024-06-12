package websocket.commands;

public class ConnectCommand extends UserGameCommand {

    public ConnectCommand(String authToken) {this.authToken = authToken;}

    CommandType commandType = CommandType.CONNECT;

    String authToken;
}
