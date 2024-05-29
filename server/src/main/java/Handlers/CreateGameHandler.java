package Handlers;
import Requests.CreateGameRequest;
import Results.CreateGameResult;
import Service.CreateGameService;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;

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
        CreateGameResult result = service.createGame(createGameReq, authToken, gameMemory, authMemory);
        //res.body(result.gameID().toString());
        return ConvertJSON.toJSON(result);
    }
}
