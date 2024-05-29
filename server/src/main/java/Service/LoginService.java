package Service;

import Results.LoginResult;
import Requests.LoginRequest;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryAuthDAO;

public class LoginService {

    public LoginService() {}

    public LoginResult login(LoginRequest req, MemoryUserDAO userMemory, MemoryAuthDAO authMemory) throws DataAccessException {
        String password = userMemory.getPassword(req.username());
        if (password != null && password.equals(req.password())) {
            String newAuth = authMemory.createAuth(req.username());
            return new LoginResult(req.username(), newAuth);
        } else {
            throw new DataAccessException("Error: unauthorized");
        }
    }
}
