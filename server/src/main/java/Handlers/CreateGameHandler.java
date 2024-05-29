package Handlers;
import Requests.CreateGameRequest;
import Results.CreateGameResult;
import Results.ExceptionResult;
import Service.CreateGameService;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

import javax.xml.crypto.Data;

public class CreateGameHandler {

    private MemoryGameDAO gameMemory;
    private MemoryAuthDAO authMemory;

    public CreateGameHandler(MemoryGameDAO gameMemory, MemoryAuthDAO authMemory) {
        this.gameMemory = gameMemory;
        this.authMemory = authMemory;
    }

    public Object handleRequest(spark.Request req, spark.Response res) {
        String authToken = req.headers("authorization");
        CreateGameRequest createGameReq = ConvertJSON.fromJSON(req.body(), CreateGameRequest.class);
        CreateGameService service = new CreateGameService();
        try {
            CreateGameResult result = service.createGame(createGameReq, authToken, gameMemory, authMemory);
            res.status(200);
            res.body(result.gameID().toString());
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
