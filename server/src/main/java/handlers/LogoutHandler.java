package handlers;
import results.ExceptionResult;
import results.LogoutResult;
import service.LogoutService;
import dataaccess.DataAccessException;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLUserDAO;

public class LogoutHandler {

    private SQLUserDAO userMemory;
    private SQLAuthDAO authMemory;

    public LogoutHandler(SQLUserDAO userMemory, SQLAuthDAO authMemory) {
        this.userMemory = userMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        String authToken = req.headers("authorization");
        LogoutService service = new LogoutService();
        try {
            LogoutResult result = service.logout(authToken, userMemory, authMemory);
            res.status(200);
            String json = ConvertJSON.toJSON(result);
            return json;
        }
        catch (DataAccessException ex) {
            res.status(401);
            ExceptionResult exception = new ExceptionResult(ex.getMessage());
            String json = ConvertJSON.toJSON(exception);
            return json;
        }
    }
}
