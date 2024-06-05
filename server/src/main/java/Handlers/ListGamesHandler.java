package Handlers;

import Requests.ListGamesRequest;
import Results.ExceptionResult;
import Results.ListGamesResult;
import Service.ListGamesService;
import dataaccess.DataAccessException;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;

import javax.xml.crypto.Data;

public class ListGamesHandler {

    private SQLAuthDAO authMemory;
    private SQLGameDAO gameMemory;

    public ListGamesHandler(SQLAuthDAO authMemory, SQLGameDAO gameMemory) {
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
