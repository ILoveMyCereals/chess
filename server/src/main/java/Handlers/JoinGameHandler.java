package Handlers;

import Requests.JoinGameRequest;
import Results.ExceptionResult;
import Results.JoinGameResult;
import Service.JoinGameService;
import dataaccess.DataAccessException;
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
        String authToken = req.headers("authorization");
        JoinGameService service = new JoinGameService();
        try {
            JoinGameResult result = service.joinGame(joinGameReq, authToken, gameMemory, authMemory);
            res.status(200);
            String json = ConvertJSON.toJSON(result);
            return json;
        }
        catch (DataAccessException ex) {
            ExceptionResult exception = new ExceptionResult(ex.getMessage());
            if (exception.message().equals("Error: bad request")) {
                res.status(400);
            } else if (exception.message().equals("Error: unauthorized")) {
                res.status(401);
            } else if (exception.message().equals("Error: already taken")) {
                res.status(403);
            }
            String json = ConvertJSON.toJSON(exception);
            return json;
        }
    }
}
