package dataaccess;

import java.util.ArrayList;
import model.GameData;
import java.sql.Connection;
import java.sql.SQLException;

public interface GameDAO {

    ArrayList<GameData> listGames() throws SQLException;

    Integer createGame(String gameName) throws SQLException;

    GameData getGame(Integer id) throws SQLException;

    boolean setTeamUser(GameData game, String username, String teamColor) throws SQLException;
}
