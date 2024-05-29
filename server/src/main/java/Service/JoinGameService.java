package Service;

import Results.JoinGameResult;
import Requests.JoinGameRequest;
import dataaccess.DataAccessException;
import model.GameData;
import model.AuthData;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryAuthDAO;

public class JoinGameService {

    public JoinGameService() {}

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String authToken, MemoryGameDAO gameMemory, MemoryAuthDAO authMemory) throws DataAccessException {
        AuthData verified = authMemory.verifyAuth((authToken));
        if (verified != null) {
            GameData requestedGame = gameMemory.getGame(joinGameRequest.gameID());
            if (requestedGame != null) {
                boolean userSet = gameMemory.setTeamUser(requestedGame, verified.username(), joinGameRequest.playerColor());
                if (userSet) {
                    return new JoinGameResult();
                } else {
                    throw new DataAccessException("Error: already taken");
                }
            } else {
                throw new DataAccessException("Error: bad request");
            }
        } else {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
