package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import dataaccess.sqldao.SQLAuthDAO;
import handlers.ConvertJSON;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import spark.Spark;
import dataaccess.sqldao.SQLUserDAO;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;
import websocket.commands.*;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

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
                ServerMessage newMessage = moveMessageGenerator(secondCommand, gameDAO, authDAO);
                String jsonMessage = ConvertJSON.toJSON(newMessage);
                for (Integer gameID : gameIDToAuth.keySet()) {
                    if (gameID.equals(secondCommand.getGameID())) {
                        String newAuth = gameIDToAuth.get(gameID);
                        Session newSession = authToSession.get(newAuth); // Instead of just these strings, I should create a ServerMessage object, serialize it to a JSON which will later be deserialized
                        newSession.getRemote().sendString(jsonMessage);
                    }
                }
            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.LEAVE) {
            LeaveCommand secondCommand = ConvertJSON.fromJSON(message, LeaveCommand.class);

            try {
                //I should make a new method in my gameDAO to remove a user from a game, and I'll call that method here
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
                gameIDToAuth.values().removeIf(authToken -> authToken.equals(secondCommand.getAuthString())); //Is this right?
                authToSession.remove(secondCommand.getAuthString());
            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.RESIGN) {
            ResignCommand secondCommand = ConvertJSON.fromJSON(message, ResignCommand.class);

        }
    }

    private ServerMessage moveMessageGenerator(MakeMoveCommand command, SQLGameDAO gameDAO, SQLAuthDAO authDAO) {
        String message;

        ChessMove move = command.getMove();
        ChessPosition startPosition = command.getMove().getStartPosition();
        ChessPosition endPosition = command.getMove().getEndPosition();

        try {
            ChessGame.TeamColor defColor;

            ChessGame game = gameDAO.getGame(command.getGameID()).getGame();
            GameData gameData = gameDAO.getGame(command.getGameID());

            String username = authDAO.verifyAuth(command.getAuthString()).username();
            game.makeMove(move);
            message = username + "has moved from " + startPosition + " to " + endPosition; //I still don't know if the positions will output how I want
            if (game.getTeamTurn().equals(ChessGame.TeamColor.WHITE)) {
                defColor = ChessGame.TeamColor.BLACK;
            } else {
                defColor = ChessGame.TeamColor.WHITE;
            }
            if (game.isInCheck(defColor)) {
                if (defColor == ChessGame.TeamColor.BLACK) {
                    message += "\n" + gameData.getBlackUsername() + "is in check";
                } else {
                    message += "\n" + gameData.getWhiteUsername() + "is in check";
                }
            } else if (game.isInCheckmate(defColor)) {
                if (defColor == ChessGame.TeamColor.BLACK) {
                    message += "\n" + gameData.getBlackUsername() + "is in checkmate";
                } else {
                    message += "\n" + gameData.getWhiteUsername() + "is in checkmate";
                } //I need to figure out how to handle when a game ends
            } else if (game.isInCheckmate(defColor) || game.isInCheckmate(game.getTeamTurn())) {
                message += "\nThe game has ended in a stalemate.";
            }

            return new NotificationMessage(message);

        } catch (SQLException ex) {
            return null;
        } catch (InvalidMoveException ex) {
            return null; //This is a case I'll definitely have to consider, return an error message about an invalid move
        }
    }
}
