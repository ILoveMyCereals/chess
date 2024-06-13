package server;

import chess.ChessGame;
import dataaccess.sqldao.SQLAuthDAO;
import handlers.ConvertJSON;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import dataaccess.sqldao.SQLUserDAO;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;
import websocket.commands.ConnectCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WSServer {

    private Map <Integer, String> gameIDToAuth = new HashMap<>();
    private Map <String, Session> authToSession = new HashMap<>();

    public static void main(String[] args) {
        Spark.port(8080);
        //I could have two maps, one that maps GameID to authToken and another that maps authToken to session
        //Or I could have one map that maps GameID to another map, essentially the same as described above
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) {
        SQLUserDAO userDAO = new SQLUserDAO();
        SQLAuthDAO authDAO = new SQLAuthDAO();
        SQLGameDAO gameDAO = new SQLGameDAO();
        UserGameCommand firstCommand = ConvertJSON.fromJSON(message, UserGameCommand.class);
        UserGameCommand.CommandType commandType = firstCommand.getCommandType();
        if (commandType == UserGameCommand.CommandType.CONNECT) {
            ConnectCommand secondCommand = ConvertJSON.fromJSON(message, ConnectCommand.class);
            gameIDToAuth.put(secondCommand.getGameID(), secondCommand.getAuthString());
            authToSession.put(secondCommand.getAuthString(), session);
            try {
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();
                session.getRemote().sendString(username + " has joined the game");
            } catch (Exception ex) {
                return;
            }
            //Will I need to use the DAOs at all for this option?

        } else if (commandType == UserGameCommand.CommandType.MAKE_MOVE) {
            MakeMoveCommand secondCommand = ConvertJSON.fromJSON(message, MakeMoveCommand.class);
            try {
                ChessGame game = gameDAO.getGame(secondCommand.getGameID()).getGame();

            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.LEAVE) {
            return;

        } else if (commandType == UserGameCommand.CommandType.RESIGN) {
            return;

        }
    }
}
