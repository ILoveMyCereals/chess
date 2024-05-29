package Handlers;

import Requests.ListGamesRequest;
import Results.ExceptionResult;
import Results.ListGamesResult;
import Service.ListGamesService;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import javax.xml.crypto.Data;

public class ListGamesHandler {

    private MemoryAuthDAO authMemory;
    private MemoryGameDAO gameMemory;

    public ListGamesHandler(MemoryAuthDAO authMemory, MemoryGameDAO gameMemory) {
        this.authMemory = authMemory;
        this.gameMemory = gameMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        String authToken = req.headers("authorization");
        ListGamesService service = new ListGamesService();
        try {
            ListGamesResult result = service.listGames(authToken, authMemory, gameMemory);
            res.status(200);
            res.body(ConvertJSON.toJSON(result));
            return ConvertJSON.toJSON(result);
        }
        catch(DataAccessException ex) {
            ExceptionResult exception = new ExceptionResult(ex.getMessage());
            res.status(401);
            String json = ConvertJSON.toJSON(exception);
            return json;
        }
    }
}
