package Handlers;

import Requests.JoinGameRequest;
import Results.JoinGameResult;
import Service.JoinGameService;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

public class JoinGameHandler {

    private MemoryGameDAO gameMemory;
    private MemoryAuthDAO authMemory;

    public JoinGameHandler(MemoryGameDAO gameMemory, MemoryAuthDAO authMemory) {
        this.gameMemory = gameMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        JoinGameRequest joinGameReq = ConvertJSON.fromJSON(req.body(), JoinGameRequest.class);
        String authToken = req.headers("Authorization");
        JoinGameService service = new JoinGameService();
        JoinGameResult result = service.joinGame(joinGameReq, authToken, gameMemory, authMemory);
        return ConvertJSON.toJSON(result);
    }
}
