package Service;

import Results.LoginResult;
import Requests.LoginRequest;
import dataaccess.DataAccessException;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.SQLException;

public class LoginService {

    public LoginService() {}

    public LoginResult login(LoginRequest req, SQLUserDAO userMemory, SQLAuthDAO authMemory) throws DataAccessException {
        try {
            String password = userMemory.getPassword(req.username());
            if (password != null && BCrypt.checkpw(req.password(), password)) {
                String newAuth = authMemory.createAuth(req.username());
                return new LoginResult(req.username(), newAuth);
            } else {
                throw new DataAccessException("Error: unauthorized");
            }
        } catch (SQLException ex) {
            try {
                String authToken = authMemory.getAuth(req.username());
                authMemory.deleteAuth(authToken);
                String newAuth = authMemory.createAuth(req.username());
                return new LoginResult(req.username(), newAuth);
            } catch (SQLException ex1) {
                throw new DataAccessException("Error: unauthorized");
            }
        }
    }
}
