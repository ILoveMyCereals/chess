package handlers;

import results.ClearResult;
import service.ClearService;
import dataaccess.sqldao.SQLUserDAO;
import dataaccess.sqldao.SQLAuthDAO;
import dataaccess.sqldao.SQLGameDAO;

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
