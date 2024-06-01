package dataaccess.SQLDAO;

import java.util.ArrayList;
import dataaccess.GameDAO;
import model.GameData;
import java.sql.Connection;
import java.sql.SQLException;
import com.google.gson.Gson;

public class SQLGameDAO implements GameDAO {

    public ArrayList<GameData> listGames(Connection conn) throws SQLException {
        return null;
    }

    public Integer createGame(Connection conn, String gameName) throws SQLException {
        return null;
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

    public boolean setTeamUser(Connection conn, GameData game, String username, String teamColor) throws SQLException {
        return false;
    }
}
