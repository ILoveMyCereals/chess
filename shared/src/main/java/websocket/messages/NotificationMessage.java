package websocket.messages;

public class NotificationMessage extends ServerMessage {

    public NotificationMessage(String message) {this.message = message;}

    String message;
}
