package Handlers;

import Requests.LoginRequest;
import Results.ExceptionResult;
import Results.LoginResult;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLUserDAO;
import Service.LoginService;

public class LoginHandler {

    private SQLUserDAO userMemory;
    private SQLAuthDAO authMemory;

    public LoginHandler(SQLUserDAO userMemory, SQLAuthDAO authMemory) {
        this.userMemory = userMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        LoginRequest loginReq = ConvertJSON.fromJSON(req.body(), LoginRequest.class);
        LoginService service = new LoginService();
        try {
            LoginResult result = service.login(loginReq, userMemory, authMemory);
            res.status(200);
            res.body(result.username() + result.authToken());
            String json = ConvertJSON.toJSON(result);
            return json;
        }
        catch (Exception ex) {
            ExceptionResult exception = new ExceptionResult(ex.getMessage());
            res.status(401);
            String json = ConvertJSON.toJSON(exception);
            return json;
        }
    }
}
