package Service;

import Results.RegisterResult;
import Requests.RegisterRequest;
import dataaccess.MemoryUserDAO;
import dataaccess.MemoryAuthDAO;

public class RegisterService {

    public RegisterService() {}

    public RegisterResult register (RegisterRequest req, MemoryUserDAO userMemory, MemoryAuthDAO authMemory) {
        String getResult = userMemory.getUser(req.username());
        if (getResult != null) {
            userMemory.createUser(req.username(), req.password(), req.email());
            String newAuth = authMemory.createAuth(req.username());
            return new RegisterResult(getResult, newAuth);
        } else {
            return new RegisterResult("message", "Error: already taken");
        }
    }
}
