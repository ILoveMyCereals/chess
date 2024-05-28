package Service;

import Results.CreateGameResult;
import Requests.CreateGameRequest;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryAuthDAO;

public class CreateGameService {

    public CreateGameService() {}

    public CreateGameResult createGame(CreateGameRequest req, String authToken, MemoryGameDAO gameMemory, MemoryAuthDAO authMemory) {
        if (authMemory.verifyAuth(authToken) != null) {
            return new CreateGameResult(gameMemory.createGame());
        } else {
            return null;
        }
    }
}
