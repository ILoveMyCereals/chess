package Handlers;

import Requests.ClearRequest;
import Results.ClearResult;
import Service.ClearService;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;

public class ClearHandler {

    private SQLUserDAO userMemory;
    private SQLAuthDAO authMemory;
    private SQLGameDAO gameMemory;

    public ClearHandler (SQLUserDAO userMemory, SQLAuthDAO authMemory, SQLGameDAO gameMemory) {
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
