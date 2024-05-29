package Handlers;

import Requests.ListGamesRequest;
import Results.ListGamesResult;
import Service.ListGamesService;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

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
        ListGamesResult result = service.listGames(authToken, authMemory, gameMemory);
        return ConvertJSON.toJSON(result);
    }
}
