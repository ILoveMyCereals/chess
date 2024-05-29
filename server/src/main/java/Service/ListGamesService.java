package Service;

import Results.ListGamesResult;
import model.GameData;
import model.AuthData;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryAuthDAO;
import java.util.ArrayList;

public class ListGamesService {

    public ListGamesService() {}

    public ListGamesResult listGames(String authToken, MemoryAuthDAO authMemory, MemoryGameDAO gameMemory) {
        AuthData verified = authMemory.verifyAuth(authToken);
        if (verified != null) {
            ArrayList<GameData> games = gameMemory.listGames();
            return new ListGamesResult(games, null, null);
        }
        else {
            return new ListGamesResult(null, "message", "Error: unauthorized");
        }
    }
}
