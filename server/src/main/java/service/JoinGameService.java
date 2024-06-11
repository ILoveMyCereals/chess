package service;

import results.JoinGameResult;
import requests.JoinGameRequest;
import dataaccess.DataAccessException;
import model.GameData;
import model.AuthData;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;

import java.sql.SQLException;

public class JoinGameService {

    public JoinGameService() {}

    public JoinGameResult joinGame(JoinGameRequest joinGameRequest, String authToken, SQLGameDAO gameMemory, SQLAuthDAO authMemory) throws DataAccessException {
        try {
            AuthData verified = authMemory.verifyAuth((authToken));
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unauthorized");
        }
        try {
            AuthData verified = authMemory.verifyAuth((authToken));
            if (verified != null) {
                GameData requestedGame = gameMemory.getGame(joinGameRequest.gameID());
                if (requestedGame != null && joinGameRequest.playerColor() != null) {
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
        } catch (SQLException ex) {
            throw new DataAccessException("Error: bad request");
        }
    }
}
