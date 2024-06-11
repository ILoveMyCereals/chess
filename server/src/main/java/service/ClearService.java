package service;

import results.ClearResult;
import dataaccess.sqldao.SQLUserDAO;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;

import java.sql.SQLException;

public class ClearService {

    public ClearService() {}

    public ClearResult clear(SQLUserDAO userMemory, SQLAuthDAO authMemory, SQLGameDAO gameMemory) {
        try {
            userMemory.clearUsers();
            authMemory.clearAuths();
            gameMemory.clearGames();
            return new ClearResult(null, null);
        } catch (SQLException ex) {
            return null;
        }
    }
}
