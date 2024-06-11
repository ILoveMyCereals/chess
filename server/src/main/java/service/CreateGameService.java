package service;

import results.CreateGameResult;
import requests.CreateGameRequest;
import dataaccess.DataAccessException;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;

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
