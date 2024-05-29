package Handlers;

import Requests.ClearRequest;
import Results.ClearResult;
import Service.ClearService;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;

public class ClearHandler {

    private MemoryUserDAO userMemory;
    private MemoryAuthDAO authMemory;
    private MemoryGameDAO gameMemory;

    public ClearHandler (MemoryUserDAO userMemory, MemoryAuthDAO authMemory, MemoryGameDAO gameMemory) {
        this.userMemory = userMemory;
        this.authMemory = authMemory;
        this.gameMemory = gameMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        ClearService service = new ClearService();
        ClearResult result = service.clear(userMemory, authMemory, gameMemory);
        return ConvertJSON.toJSON(result);
    }
}
