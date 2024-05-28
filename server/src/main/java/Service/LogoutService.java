package Service;

import Results.LogoutResult;
import Requests.LogoutRequest;
import model.UserData;
import model.AuthData;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryAuthDAO;

public class LogoutService {

    public LogoutService() {}

    public LogoutResult logout(String authToken, MemoryUserDAO userMemory, MemoryAuthDAO authMemory) {
        AuthData verified = authMemory.verifyAuth(authToken);
        if (verified != null) {
            authMemory.deleteAuth(authToken);
            return new LogoutResult(null, null);
        } else {
            return new LogoutResult("message", "Error: unauthorized");
        }
    }
}
