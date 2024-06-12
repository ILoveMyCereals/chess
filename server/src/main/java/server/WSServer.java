package server;

import dataaccess.sqldao.SQLAuthDAO;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import dataaccess.sqldao.SQLGameDAO;

@WebSocket
public class WSServer {

    public static void main(String[] args) {
        Spark.port(8080);
        Spark.webSocket("/ws", WSServer.class); // this line should be in the other server class
    }

    @OnWebSocketMessage
    public void onMessage(String Session, String message) {
        //first thing to do in this class is deserialize message (which is a json) and find what kind of command it is

        //connect command
        if (message.equals("MAKE_MOVE")) { //I deserialize the json to a usercommand object (either deserialize twice or use type adapter), then I can tell which request it is?
            //update the board in the game to reflect the move made (call the makeMove method in ChessGame
            //the makeMove method will check if the move is valid it already calls validMoves
            return;
        } else if (message.equals("LEAVE")) {
            //update the game in the db so that the team of the leaving player becomes null
            return;
        } else if (message.equals("RESIGN")) {
            return;
        }
    }
}
