package Service;

import Results.LogoutResult;
import Requests.LogoutRequest;
import dataaccess.DataAccessException;
import model.UserData;
import model.AuthData;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryAuthDAO;

public class LogoutService {

    public LogoutService() {}

    public LogoutResult logout(String authToken, MemoryUserDAO userMemory, MemoryAuthDAO authMemory) throws DataAccessException {
        AuthData verified = authMemory.verifyAuth(authToken);
        if (verified != null) {
            authMemory.deleteAuth(authToken);
            return new LogoutResult(null, null);
        } else {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
