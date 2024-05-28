package Service;

import Results.JoinGameResult;
import Requests.JoinGameRequest;
import model.GameData;
import model.AuthData;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryAuthDAO;

public class JoinGameService {

    public JoinGameService() {}

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String authToken, MemoryGameDAO gameMemory, MemoryAuthDAO authMemory) {
        AuthData verified = authMemory.verifyAuth((authToken));
        if (verified != null) {
            GameData requestedGame = gameMemory.getGame(joinGameRequest.gameID());
            if (requestedGame != null) {
                boolean userSet = gameMemory.setTeamUser(requestedGame, verified.username(), joinGameRequest.playerColor());
                if (userSet) {
                    return new JoinGameResult(null, null);
                }
            }
        } else {
            return new JoinGameResult("message", "Error: unauthorized");
        }
        return new JoinGameResult("message", "bad request");
    }
}
