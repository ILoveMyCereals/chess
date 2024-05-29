package Service;

import Results.RegisterResult;
import Requests.RegisterRequest;
import dataaccess.DataAccessException;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryAuthDAO;

public class RegisterService {

    public RegisterService() {}

    public RegisterResult register (RegisterRequest req, MemoryUserDAO userMemory, MemoryAuthDAO authMemory) throws DataAccessException {
        String getResult = userMemory.getUser(req.username());
        if (getResult == null) {
            if (req.username() == null || req.password() == null || req.email() == null) {
                throw new DataAccessException("Error: bad request");
            }
            userMemory.createUser(req.username(), req.password(), req.email());
            String newAuth = authMemory.createAuth(req.username());
            return new RegisterResult(req.username(), newAuth);
        } else {
            throw new DataAccessException("Error: already taken");
        }
    }
}
