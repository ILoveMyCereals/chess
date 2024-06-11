package Service;

import results.LogoutResult;
import dataaccess.DataAccessException;
import model.AuthData;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLUserDAO;

import java.sql.SQLException;

public class LogoutService {

    public LogoutService() {}

    public LogoutResult logout(String authToken, SQLUserDAO userMemory, SQLAuthDAO authMemory) throws DataAccessException {
        try {
            AuthData verified = authMemory.verifyAuth(authToken);
            if (verified != null) {
                authMemory.deleteAuth(authToken);
                return new LogoutResult(null, null);
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        } catch (SQLException ex) {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
