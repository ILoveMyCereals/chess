package Handlers;
import Requests.LogoutRequest;
import Results.ExceptionResult;
import Results.LogoutResult;
import Service.LogoutService;
import dataaccess.DataAccessException;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLUserDAO;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;

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
