package websocket.messages;

public class NotificationMessage extends ServerMessage {

    public NotificationMessage(String message) {this.message = message;}

    public String getMessage() {return message;}

    String message;
}
