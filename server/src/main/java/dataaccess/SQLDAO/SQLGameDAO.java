package dataaccess.SQLDAO;

import java.util.ArrayList;

import Handlers.ConvertJSON;
import dataaccess.GameDAO;
import model.GameData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Random;
import chess.ChessGame;

import com.google.gson.Gson;

public class SQLGameDAO implements GameDAO {

    public ArrayList<GameData> listGames(Connection conn) throws SQLException {
        ArrayList<GameData> games = new ArrayList<>();
        try (var preparedStatement = conn.prepareStatement("SELECT gameName, gameID, blackUsername, whiteUsername, game")) {
            try (var query = preparedStatement.executeQuery()) {
                while(query.next()) {
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
    }

    public Integer createGame(Connection conn, String givenName) throws SQLException {
        Random rand = new Random();
        int newGameID = rand.nextInt(1000);
        try (var preparedStatement = conn.prepareStatement("INSERT INTO chess (Game) VALUES(?, ?, null, null, new ChessGame())")) {
            preparedStatement.setString(1, givenName);
            preparedStatement.setInt(2, newGameID);
            preparedStatement.executeUpdate();
            return newGameID;
        }
    }

    public GameData getGame(Connection conn, Integer givenID) throws SQLException  {
        try (var preparedStatement = conn.prepareStatement("SELECT game FROM Game WHERE gameID=?")) {
            preparedStatement.setInt(1, givenID);
            var queryResult = preparedStatement.executeQuery();
            var jsonResult = queryResult.getString("game");
            var gameResult = new Gson().fromJson(jsonResult, GameData.class);
            return gameResult;
        }
    }

    public boolean setTeamUser(Connection conn, GameData game, String givenName, String givenColor) throws SQLException {
        if (givenColor == "WHITE" && game.getWhiteUsername() == null || givenColor == "BLACK" && game.getBlackUsername() == null) {
            try (var preparedStatement = conn.prepareStatement("UPDATE chess SET teamColor=? WHERE gameID=?")) {
                preparedStatement.setString(1, givenColor);
                preparedStatement.setInt(2, game.getGameID());
                preparedStatement.executeUpdate();
                return true;
            }
        }
        else {
            return false;
        }
    }
}
