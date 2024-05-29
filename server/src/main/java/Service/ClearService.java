package Service;

import Results.ClearResult;
import Requests.ClearRequest;
import model.GameData;
import model.AuthData;
import model.UserData;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

public class ClearService {

    public ClearService() {}

    public ClearResult clear(MemoryUserDAO userMemory, MemoryAuthDAO authMemory, MemoryGameDAO gameMemory) {
        userMemory.clearUsers();
        authMemory.clearAuths();
        gameMemory.clearGames();
        return new ClearResult(null, null);
    }
}
