package websocket.messages;

public class ErrorMessage extends ServerMessage {

    public ErrorMessage(String errorMessage) {this.errorMessage = errorMessage;}

    String errorMessage;
}