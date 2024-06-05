package Service;

import Results.RegisterResult;
import Requests.RegisterRequest;
import dataaccess.DataAccessException;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;

public class RegisterService {

    public RegisterService() {}

    public RegisterResult register (RegisterRequest req, SQLUserDAO userMemory, SQLAuthDAO authMemory) throws DataAccessException {
        try {
            String getResult = userMemory.getUser(req.username());
            if (getResult == null) {
                if (req.username() == null || req.password() == null || req.email() == null) {
                    throw new DataAccessException("Error: bad request");
                }

            } else {
                throw new DataAccessException("Error: already taken");
            }
            return null;
        } catch (SQLException ex) {
            if (req.username() == null || req.password() == null || req.email() == null) {
                throw new DataAccessException("Error: bad request");
            }
            try {
                String hashPass = BCrypt.hashpw(req.password(), BCrypt.gensalt());
                userMemory.createUser(req.username(), hashPass, req.email());
                String newAuth = authMemory.createAuth(req.username());
                return new RegisterResult(req.username(), newAuth);
            } catch (SQLException ex2) {
                return null;
            }
        }
    }
}
