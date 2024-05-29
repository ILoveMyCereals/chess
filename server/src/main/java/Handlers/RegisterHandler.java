package Handlers;
import Requests.RegisterRequest;
import Results.RegisterResult;
import Results.ExceptionResult;
import Service.RegisterService;
import dataaccess.DataAccessException;
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
        //try/catch block here, any time the exception is thrown the catch block will execute
        try {
            RegisterResult result = service.register(registerReq, userMemory, authMemory);
            res.status(200);
            res.body(result.username() + result.authToken());
            String json = ConvertJSON.toJSON(result);
            return json;
        }
        catch (DataAccessException ex) {
            //make a new class for exceptions that has one string variable, put ex.getMessage into this object
            ExceptionResult exception = new ExceptionResult(ex.getMessage());
            if (exception.message().equals("Error: already taken")) {
                res.status(403);
            } else if (exception.message().equals("Error: bad request")) {
                res.status(400);
            }
            // I can have several throw errors in my service/DAO, and I can have different conditionals here depending on the ex.getMessage()
            String json = ConvertJSON.toJSON(exception);
            return json;
        }
    }

}
