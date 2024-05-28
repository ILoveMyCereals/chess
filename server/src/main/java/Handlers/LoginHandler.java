package Handlers;

import Requests.LoginRequest;
import Results.LoginResult;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import Service.LoginService;

public class LoginHandler {

    private MemoryUserDAO userMemory;
    private MemoryAuthDAO authMemory;

    public LoginHandler(MemoryUserDAO userMemory, MemoryAuthDAO authMemory) {
        this.userMemory = userMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        LoginRequest loginReq = ConvertJSON.fromJSON(req.body(), LoginRequest.class);
        LoginService service = new LoginService();
        LoginResult result = service.login(loginReq, userMemory, authMemory);
        String json = ConvertJSON.toJSON(result);
        return json;
    }
}
