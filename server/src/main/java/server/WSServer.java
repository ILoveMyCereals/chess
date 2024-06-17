package server;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import dataaccess.sqldao.SQLAuthDAO;
import handlers.ConvertJSON;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session; //I may or may not have to change this to javax
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

    private Map <String, Integer> authToGameID = new HashMap<>();
    private Map <String, Session> authToSession = new HashMap<>();
    private Map <Integer, Boolean> gameToIsInPlay = new HashMap<>();

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
                if (!gameToIsInPlay.containsKey(secondCommand.getGameID())) { //If the game is not in the gameisinplay map
                    gameToIsInPlay.put(secondCommand.getGameID(), true);
                }

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

                ChessMove move = secondCommand.getMove();
                GameData game = gameDAO.getGame(secondCommand.getGameID());
                String startPosition = move.getStartPosition().getReadablePosition();
                String endPosition = move.getEndPosition().getReadablePosition();
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();

                if (!gameToIsInPlay.containsKey(secondCommand.getGameID()) || !gameToIsInPlay.get(secondCommand.getGameID())) {
                    ErrorMessage errorMessage = new ErrorMessage("Error: game cannot be played");
                    String jsonErrorMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonErrorMessage);
                    return;
                }

                if (!game.getBlackUsername().equals(username) && game.getGame().getTeamTurn() == ChessGame.TeamColor.BLACK) {
                    ErrorMessage errorMessage = new ErrorMessage("Error: it is not your turn");
                    String jsonErrorMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonErrorMessage);
                    return;
                } else if (!game.getWhiteUsername().equals(username) && game.getGame().getTeamTurn() == ChessGame.TeamColor.WHITE) {
                    ErrorMessage errorMessage = new ErrorMessage("Error: it is not your turn");
                    String jsonErrorMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonErrorMessage);
                    return;
                }

                if (game.getWhiteUsername().equals(username) || game.getBlackUsername().equals(username)) {
                game.getGame().makeMove(move);
                gameDAO.updateGame(secondCommand.getGameID(), game.getGame());
                String moveMessage = username + " has moved from " + startPosition + " to " + endPosition;
                NotificationMessage notification = new NotificationMessage(moveMessage);
                String jsonMessage = ConvertJSON.toJSON(notification);

                ServerMessage alertMessage = alertMessageGenerator(secondCommand, gameDAO, authDAO);

                LoadGameMessage loadMessage = new LoadGameMessage(game.getGame());
                String loadJson = ConvertJSON.toJSON(loadMessage);

                if (alertMessage != null) {
                    jsonAlertMessage = ConvertJSON.toJSON(alertMessage);
                }

                for (String newAuth : authToGameID.keySet()) {
                    Integer gameID = authToGameID.get(newAuth);
                    Session newSession = authToSession.get(newAuth);
                    if (gameID.equals(secondCommand.getGameID()) && newSession.isOpen()) { //If I find an authToken associated with the current gameID
                        if (!newAuth.equals(secondCommand.getAuthString())) { //If it's not the authToken of the user making the move
                            newSession.getRemote().sendString(jsonMessage); //Notify them of the move
                        }
                        newSession.getRemote().sendString(loadJson); //send everyone with the gameID a loadGame message
                        if (alertMessage != null) {
                            newSession.getRemote().sendString(jsonAlertMessage); //send everyone with the gameID an alert message, if it exists
                        }
                    }
                }
                } else {
                    ErrorMessage errorMessage = new ErrorMessage("Error: cannot make a move as an observer");
                    String jsonMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonMessage);
                }
            } catch (InvalidMoveException ex) {
                ErrorMessage errorMessage = new ErrorMessage("Error: invalid move");
                String jsonMessage = ConvertJSON.toJSON(errorMessage);
                try {
                    session.getRemote().sendString(jsonMessage);
                } catch (Exception ex1) {
                    return; //don't bother
                }
            } catch (SQLException ex) {
                try {
                    ErrorMessage errorMessage = new ErrorMessage("Error: unauthorized");
                    String jsonMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonMessage);
                } catch (Exception ex1) {
                    return; //don't bother
                }
            } catch (IOException ex) {
                try {
                    ErrorMessage errorMessage = new ErrorMessage("Error: IOException");
                    String jsonMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonMessage);
                } catch (Exception ex2) {
                    return;
                }
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
                ErrorMessage errorMessage = new ErrorMessage("Error 1");
                String json = ConvertJSON.toJSON(errorMessage);
                try {
                    session.getRemote().sendString(json);
                } catch (Exception ex1) {
                    return; //don't bother
                }
            }

        } else if (commandType == UserGameCommand.CommandType.RESIGN) {
            ResignCommand secondCommand = ConvertJSON.fromJSON(message, ResignCommand.class);

            try {
                GameData game = gameDAO.getGame(secondCommand.getGameID());
                String username = authDAO.verifyAuth(secondCommand.getAuthString()).username();

                if (game.getWhiteUsername().equals(username) || game.getBlackUsername().equals(username)) {
                    if (gameToIsInPlay.containsKey(secondCommand.getGameID()) && gameToIsInPlay.get(secondCommand.getGameID())) {
                        gameToIsInPlay.put(secondCommand.getGameID(), false);
                        NotificationMessage notification = new NotificationMessage(username + "has resigned");
                        String jsonMessage = ConvertJSON.toJSON(notification);

                        for (String newAuth : authToGameID.keySet()) {
                            Integer gameID = authToGameID.get(newAuth);
                            if (gameID.equals(secondCommand.getGameID())) {
                                Session newSession = authToSession.get(newAuth);
                                newSession.getRemote().sendString(jsonMessage);
                            }
                        }
                    } else {
                        ErrorMessage errorMessage = new ErrorMessage("Error: game cannot be resigned");
                        String jsonMessage = ConvertJSON.toJSON(errorMessage);
                        session.getRemote().sendString(jsonMessage);
                    }
                } else {
                    ErrorMessage errorMessage = new ErrorMessage("Error: you cannot resign as an observer");
                    String jsonMessage = ConvertJSON.toJSON(errorMessage);
                    session.getRemote().sendString(jsonMessage);
                }
            } catch (Exception ex) {
                    ErrorMessage errorMessage = new ErrorMessage("Error 2");
                    String json = ConvertJSON.toJSON(errorMessage);
                    try {
                        session.getRemote().sendString(json);
                    } catch (Exception ex1) {
                        return; //don't bother
                    }
                }
            }

        }

    private ServerMessage alertMessageGenerator(MakeMoveCommand command, SQLGameDAO gameDAO, SQLAuthDAO authDAO) {
        String message = null;

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
                gameToIsInPlay.put(command.getGameID(), false);
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
