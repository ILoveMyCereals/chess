package dataaccess;

import java.util.ArrayList;
import model.GameData;

public interface GameDAO {

    ArrayList<GameData> listGames(String authToken);

    Integer createGame();

    GameData getGame(Integer id);

    boolean setTeamUser(GameData game, String username, String teamColor);
}
