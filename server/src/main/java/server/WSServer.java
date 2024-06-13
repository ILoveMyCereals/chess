package server;

import chess.ChessGame;
import chess.ChessPosition;
import dataaccess.sqldao.SQLAuthDAO;
import handlers.ConvertJSON;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import dataaccess.sqldao.SQLUserDAO;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;
import websocket.commands.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WSServer {

    private Map <Integer, String> gameIDToAuth = new HashMap<>();
    private Map <String, Session> authToSession = new HashMap<>();

    public static void main(String[] args) {
        Spark.port(8080);
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
                for (Integer gameID : gameIDToAuth.keySet()) {
                    if (gameID.equals(secondCommand.getGameID())) {
                        String newAuth = gameIDToAuth.get(gameID);
                        if (!newAuth.equals(secondCommand.getAuthString())) {
                            Session newSession = authToSession.get(newAuth);
                            newSession.getRemote().sendString(username + " has joined the game");
                        }
                    }
                }
                //I still need to send a load game message to the user who joins

            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.MAKE_MOVE) {
            MakeMoveCommand secondCommand = ConvertJSON.fromJSON(message, MakeMoveCommand.class);

            try {
                ChessGame game = gameDAO.getGame(secondCommand.getGameID()).getGame();
                game.makeMove(secondCommand.getMove());
                ChessPosition startPosition = secondCommand.getMove().getStartPosition();
                ChessPosition endPosition = secondCommand.getMove().getEndPosition();
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();
                for (Integer gameID : gameIDToAuth.keySet()) {
                    if (gameID.equals(secondCommand.getGameID())) {
                        String newAuth = gameIDToAuth.get(gameID);
                        Session newSession = authToSession.get(newAuth);
                        session.getRemote().sendString(username + " moved from " + startPosition + " to " + endPosition); //idk if this will return the string the way I want it to
                    }
                }
            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.LEAVE) {
            LeaveCommand secondCommand = ConvertJSON.fromJSON(message, LeaveCommand.class);

            try {
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();
                for (Integer gameID : gameIDToAuth.keySet()) {
                    if (gameID.equals(secondCommand.getGameID())) {
                        String newAuth = gameIDToAuth.get(gameID);
                        if (!newAuth.equals(secondCommand.getAuthString())) {
                            Session newSession = authToSession.get(newAuth);
                            newSession.getRemote().sendString(username + " has left the game");
                        }
                    }
                }
                gameIDToAuth.values().removeIf(authToken -> authToken.equals(secondCommand.getAuthString())); //IS THIS RIGHT?
                authToSession.remove(secondCommand.getAuthString());
            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.RESIGN) {
            ResignCommand secondCommand = ConvertJSON.fromJSON(message, ResignCommand.class);

        }
    }
}
