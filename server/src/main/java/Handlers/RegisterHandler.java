package Handlers;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Service.RegisterService;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;

public class RegisterHandler {

    private MemoryUserDAO userMemory;
    private MemoryAuthDAO authMemory;

    public RegisterHandler(MemoryUserDAO userMemory, MemoryAuthDAO authMemory) {
        this.userMemory = userMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        RegisterRequest registerReq = ConvertJSON.fromJSON(req.body(), RegisterRequest.class);
        RegisterService service  = new RegisterService();
        RegisterResult result = service.register(registerReq, userMemory, authMemory);
        if (result.username() != null && result.username().equals("message")) {
            res.status(400);
        }
        else {
            res.status(200);
            res.body(result.username());
        }
        String json = ConvertJSON.toJSON(result);
        return json;

    }

}
