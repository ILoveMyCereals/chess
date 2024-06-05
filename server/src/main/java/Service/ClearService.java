package Service;

import Results.ClearResult;
import Requests.ClearRequest;
import model.GameData;
import model.AuthData;
import model.UserData;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;

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
