package Service;

import Results.CreateGameResult;
import Requests.CreateGameRequest;
import dataaccess.DataAccessException;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;

import java.sql.SQLException;

public class CreateGameService {

    public CreateGameService() {}

    public CreateGameResult createGame(CreateGameRequest req, String authToken, SQLGameDAO gameMemory, SQLAuthDAO authMemory) throws DataAccessException {
        try {
            if (authMemory.verifyAuth(authToken) != null) {
                return new CreateGameResult(gameMemory.createGame(req.gameName()));
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
