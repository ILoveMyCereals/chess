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
import websocket.messages.ErrorMessage;
import websocket.messages.LoadGameMessage;
import websocket.messages.NotificationMessage;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@WebSocket
public class WSServer {

    private Map <String, Integer> authToGameID = new HashMap<>(); // I need to change this to authToGameID, the way I have it now only allows one user per game
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


            try {
                GameData game = gameDAO.getGame(secondCommand.getGameID());
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();

                authToGameID.put(secondCommand.getAuthString(), secondCommand.getGameID());
                authToSession.put(secondCommand.getAuthString(), session);

                NotificationMessage notification = new NotificationMessage(username + " has joined the game");
                String jsonMessage = ConvertJSON.toJSON(notification);

                for (String newAuth : authToGameID.keySet()) {
                    Integer gameID = authToGameID.get(newAuth);
                    if (gameID.equals(secondCommand.getGameID()) && !newAuth.equals(secondCommand.getAuthString())) {
                            Session newSession = authToSession.get(newAuth);
                            newSession.getRemote().sendString(jsonMessage);
                    }
                }

                LoadGameMessage loadMessage = new LoadGameMessage(game.getGame());
                jsonMessage = ConvertJSON.toJSON(loadMessage);
                session.getRemote().sendString(jsonMessage);

            } catch (Exception ex) { //WebSocketException
                ErrorMessage errorMessage = new ErrorMessage("Error: bad request"); //I should handle both bad gameID and invalid authToken errors
                String jsonMessage = ConvertJSON.toJSON(errorMessage);
                try {
                    session.getRemote().sendString(jsonMessage);
                } catch (IOException ex1) {
                    return;
                }
            }

        } else if (commandType == UserGameCommand.CommandType.MAKE_MOVE) {
            MakeMoveCommand secondCommand = ConvertJSON.fromJSON(message, MakeMoveCommand.class);


            try {

                String jsonAlertMessage = null;

                GameData game = gameDAO.getGame(secondCommand.getGameID());

                ChessMove move = secondCommand.getMove();
                ChessPosition startPosition = move.getStartPosition();
                ChessPosition endPosition = move.getEndPosition();

                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();
                game.getGame().makeMove(move);
                String moveMessage = username + "has moved from " + startPosition + " to " + endPosition;
                String jsonMessage = ConvertJSON.toJSON(moveMessage);

                ServerMessage alertMessage = alertMessageGenerator(secondCommand, gameDAO, authDAO);

                LoadGameMessage loadMessage = new LoadGameMessage(game.getGame());
                String loadJson = ConvertJSON.toJSON(loadMessage);

                if (alertMessage != null) {
                    jsonAlertMessage = ConvertJSON.toJSON(alertMessage);
                }

                for (String newAuth : authToGameID.keySet()) {
                    Integer gameID = authToGameID.get(newAuth);
                    Session newSession = authToSession.get(newAuth);
                    if (gameID.equals(secondCommand.getGameID())) { //If I find an authToken associated with the current gameID
                        if  (!newAuth.equals(secondCommand.getAuthString())) { //If it's not the authToken of the user making the move
                            newSession.getRemote().sendString(jsonMessage); //Notify them of the move
                        }
                        newSession.getRemote().sendString(loadJson); //send everyone with the gameID a loadGame message
                        if (alertMessage != null) {
                            newSession.getRemote().sendString(jsonAlertMessage); //send everyone with the gameID an alert message, if it exists
                        }
                    }
                }
            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.LEAVE) {
            LeaveCommand secondCommand = ConvertJSON.fromJSON(message, LeaveCommand.class);

            try {
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();
                GameData game = gameDAO.getGame(secondCommand.getGameID());
                if (username.equals(game.getWhiteUsername())) {
                    gameDAO.dropUser(ChessGame.TeamColor.WHITE, game);
                } else {
                    gameDAO.dropUser(ChessGame.TeamColor.BLACK, game);
                }

                NotificationMessage notification = new NotificationMessage(username + "has left the game");
                String jsonMessage = ConvertJSON.toJSON(notification);

                for (String newAuth : authToGameID.keySet()) {
                    Integer gameID = authToGameID.get(newAuth);
                    if (gameID.equals(secondCommand.getGameID()) && !newAuth.equals(secondCommand.getAuthString())) {
                        Session newSession = authToSession.get(newAuth);
                        newSession.getRemote().sendString(jsonMessage);
                    }
                }
                authToGameID.remove(secondCommand.getAuthString());
                authToSession.remove(secondCommand.getAuthString());
            } catch (Exception ex) {
                return;
            }

        } else if (commandType == UserGameCommand.CommandType.RESIGN) {
            ResignCommand secondCommand = ConvertJSON.fromJSON(message, ResignCommand.class);
            //What do I do when a game ends?
            //I can implement a boolean flag indicating whether or not the game is in play

        }
    }

    private ServerMessage alertMessageGenerator(MakeMoveCommand command, SQLGameDAO gameDAO, SQLAuthDAO authDAO) {
        String message = null;

        ChessMove move = command.getMove();
        ChessPosition startPosition = command.getMove().getStartPosition();
        ChessPosition endPosition = command.getMove().getEndPosition();

        try {
            ChessGame.TeamColor defColor;

            ChessGame game = gameDAO.getGame(command.getGameID()).getGame();
            GameData gameData = gameDAO.getGame(command.getGameID());

            String username = authDAO.verifyAuth(command.getAuthString()).username(); //I should modify the toString() method in my ChessPosition class, or implement a new method within that class
            if (game.getTeamTurn().equals(ChessGame.TeamColor.WHITE)) {
                defColor = ChessGame.TeamColor.BLACK;
            } else {
                defColor = ChessGame.TeamColor.WHITE;
            }
            if (game.isInCheck(defColor)) {
                if (defColor == ChessGame.TeamColor.BLACK) {
                    message = "\n" + gameData.getBlackUsername() + "is in check";
                } else {
                    message = "\n" + gameData.getWhiteUsername() + "is in check";
                }
            } else if (game.isInCheckmate(defColor)) {
                if (defColor == ChessGame.TeamColor.BLACK) {
                    message = "\n" + gameData.getBlackUsername() + "is in checkmate";
                } else {
                    message = "\n" + gameData.getWhiteUsername() + "is in checkmate";
                } //I need to figure out how to handle when a game ends
            } else if (game.isInCheckmate(defColor) || game.isInCheckmate(game.getTeamTurn())) {
                message = "\nThe game has ended in a stalemate.";
            }
            if (message != null) {
                return new NotificationMessage(message);
            } else {
                return null;
            }

        } catch (SQLException ex) {
            return null;
        }
    }
}
