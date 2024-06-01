package dataaccess.SQLDAO;

import java.util.ArrayList;
import dataaccess.GameDAO;
import model.GameData;

public class SQLGameDAO implements GameDAO {

    public ArrayList<GameData> listGames() {
        return null;
    }

    public Integer createGame(String gameName) {
        return null;
    }

    public GameData getGame(Integer gameID) {
        return null;
    }

    public boolean setTeamUser(GameData game, String username, String teamColor) {
        return false;
    }
}
