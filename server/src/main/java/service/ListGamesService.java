package service;

import results.ListGamesResult;
import dataaccess.DataAccessException;
import model.GameData;
import model.AuthData;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListGamesService {

    public ListGamesService() {}

    public ListGamesResult listGames(String authToken, SQLAuthDAO authMemory, SQLGameDAO gameMemory) throws DataAccessException {
        try {
            AuthData verified = authMemory.verifyAuth(authToken);
            if (verified != null) {
                ArrayList<GameData> games = gameMemory.listGames();
                return new ListGamesResult(games, null, null);
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
