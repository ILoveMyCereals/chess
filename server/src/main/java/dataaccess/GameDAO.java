package dataaccess;

import java.util.ArrayList;
import model.GameData;
import java.sql.Connection;
import java.sql.SQLException;

public interface GameDAO {

    ArrayList<GameData> listGames(Connection conn) throws SQLException;

    Integer createGame(Connection conn, String gameName) throws SQLException;

    GameData getGame(Connection conn, Integer id) throws SQLException;

    boolean setTeamUser(Connection conn, GameData game, String username, String teamColor) throws SQLException;
}
