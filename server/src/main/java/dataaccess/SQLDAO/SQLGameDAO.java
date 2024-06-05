package dataaccess.SQLDAO;

import java.util.ArrayList;

import Handlers.ConvertJSON;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.GameDAO;
import model.GameData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import chess.ChessGame;

import com.google.gson.Gson;

public class SQLGameDAO implements GameDAO {

    public ArrayList<GameData> listGames() throws SQLException {
        ArrayList<GameData> games = new ArrayList<>();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameName, gameID, blackUsername, whiteUsername, game FROM Game")) {
                try (var query = preparedStatement.executeQuery()) {
                    while (query.next()) {
                        var gameName = query.getString("gameName");
                        var gameID = query.getInt("gameID");
                        var blackUsername = query.getString("blackUsername");
                        var whiteUsername = query.getString("whiteUsername");

                        var json = query.getString("game");
                        var chessGame = ConvertJSON.fromJSON(json, ChessGame.class);

                        games.add(new GameData(gameID, whiteUsername, blackUsername, gameName, chessGame));
                    }
                }
            }
            return games;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public Integer createGame(String givenName) throws SQLException {
        Random rand = new Random();
        int newGameID = rand.nextInt(1000);
        ChessGame newGame = new ChessGame();
        var json = ConvertJSON.toJSON(newGame);
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO game (gameName, gameID, blackUsername, whiteUsername, game) VALUES(?, ?, null, null, ?)")) {
                preparedStatement.setString(1, givenName);
                preparedStatement.setInt(2, newGameID);
                preparedStatement.setString(3, json);
                preparedStatement.executeUpdate();
                return newGameID;
            }
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public GameData getGame(Integer givenID) throws SQLException  {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT gameID, gameName, whiteUsername, blackUsername, game FROM Game WHERE gameID=?")) {
                preparedStatement.setInt(1, givenID);
                var queryResult = preparedStatement.executeQuery();
                queryResult.next();
                var gameID = queryResult.getInt("gameID");
                var gameName = queryResult.getString("gameName");
                var whiteUsername = queryResult.getString("whiteUsername");
                var blackUsername = queryResult.getString("blackUsername");
                var jsonResult = queryResult.getString("game");
                var gameResult = ConvertJSON.fromJSON(jsonResult, ChessGame.class);
                GameData game = new GameData(gameID, whiteUsername, blackUsername, gameName, gameResult);
                return game;
            }
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public boolean setTeamUser(GameData game, String givenName, String givenColor) throws SQLException {
        var gameID = game.getGameID();
        if (givenColor.equals("WHITE") && game.getWhiteUsername() == null || givenColor.equals("BLACK") && game.getBlackUsername() == null) {
            if (givenColor.equals("WHITE")) {
            try (var conn = DatabaseManager.getConnection()) {
                try (var preparedStatement = conn.prepareStatement("UPDATE Game SET whiteUsername=? WHERE gameID=?")) {
                    preparedStatement.setString(1, givenName);
                    preparedStatement.setInt(2, gameID);
                    preparedStatement.executeUpdate();
                    return true;
                }
            } catch (DataAccessException ex) {
                return false;
            }
            } else if (givenColor.equals("BLACK")) {
                try (var conn = DatabaseManager.getConnection()) {
                    try (var preparedStatement = conn.prepareStatement("UPDATE Game SET blackUsername=? WHERE gameID=?")) {
                        preparedStatement.setString(1, givenColor);
                        preparedStatement.setInt(2, game.getGameID());
                        preparedStatement.executeUpdate();
                        return true;
                    }
                } catch (DataAccessException ex) {
                    return false;
                }
            }
        }
        else {
            return false;
        }
        return false;
    }

    public void clearGames() throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE TABLE Game")) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException ex) {
            return;
        }
    }
}
