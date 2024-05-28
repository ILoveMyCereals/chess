package Handlers;
import Requests.LogoutRequest;
import Results.LogoutResult;
import Service.LogoutService;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryUserDAO;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Convert;

public class LogoutHandler {

    private MemoryUserDAO userMemory;
    private MemoryAuthDAO authMemory;

    public LogoutHandler(MemoryUserDAO userMemory, MemoryAuthDAO authMemory) {
        this.userMemory = userMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        String authToken = req.headers("authorization");
        LogoutService service = new LogoutService();
        LogoutResult result = service.logout(authToken, userMemory, authMemory);
        return ConvertJSON.toJSON(result);
    }
}
