package server;

import Handlers.RegisterHandler;
import Handlers.LoginHandler;
import Handlers.LogoutHandler;
import Handlers.CreateGameHandler;
import Handlers.JoinGameHandler;
import Handlers.ClearHandler;
import Handlers.ListGamesHandler;
import dataaccess.*;
import dataaccess.MemoryUserDAO;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import model.AuthData;
import model.GameData;
import model.UserData;
import spark.*;

public class Server {

    public Server () {
        DatabaseManager dbm = new DatabaseManager();

        try {
            dbm.createDatabase();
            Connection newConn = dbm.getConnection();
            var userTableStatement = """
                CREATE TABLE IF NOT EXISTS Users (
                username VARCHAR(255) NOT NULL,
                password longtext NOT NULL,
                email VARCHAR(255) NOT NULL,
                PRIMARY KEY (username)
                )""";
            var preparedUserStatement = newConn.prepareStatement(userTableStatement);
            preparedUserStatement.executeUpdate();
            var authTableStatement = """
                    CREATE TABLE IF NOT EXISTS Auth (
                    username VARCHAR(255) NOT NULL,
                    authToken VARCHAR(255) NOT NULL,
                    id int NOT NULL AUTO_INCREMENT,
                    PRIMARY KEY (id)
                    )""";
            var preparedAuthStatement = newConn.prepareStatement(authTableStatement);
            preparedAuthStatement.executeUpdate();
            var gameTableStatement = """
                    CREATE TABLE IF NOT EXISTS Game (
                    gameName VARCHAR(255) NOT NULL,
                    gameID int NOT NULL,
                    blackUsername VARCHAR(255),
                    whiteUsername VARCHAR(255),
                    game LONGTEXT NOT NULL,
                    PRIMARY KEY (gameName)
                    )""";
            var preparedGameStatement = newConn.prepareStatement(gameTableStatement);
            preparedGameStatement.executeUpdate();

        }
        catch(DataAccessException ex) {
            System.out.println("Data Access Error");
        }
        catch(java.sql.SQLException ex) {
            System.out.println("SQL Error");
        }
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.



        SQLUserDAO userMemory = new SQLUserDAO();
        SQLAuthDAO authMemory = new SQLAuthDAO();
        SQLGameDAO gameMemory = new SQLGameDAO();


        Spark.post("/user", ((request, response) -> new RegisterHandler(userMemory, authMemory).handleRequest(request, response)
        ));

        Spark.post("/session", ((request, response) -> new LoginHandler(userMemory, authMemory).handleRequest(request, response)
        ));

        Spark.delete("/session", ((request, response) -> new LogoutHandler(userMemory, authMemory).handleRequest(request, response)
        ));

        Spark.post("/game", ((request, response) -> new CreateGameHandler(gameMemory, authMemory).handleRequest(request, response)
        ));

        Spark.put("/game", ((request, response) -> new JoinGameHandler(gameMemory, authMemory).handleRequest(request, response)
        ));

        Spark.delete("/db", ((request, response) -> new ClearHandler(userMemory, authMemory, gameMemory).handleRequest(request, response)
        ));

        Spark.get("/game", ((request, response) -> new ListGamesHandler(authMemory, gameMemory).handleRequest(request, response)
        ));

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
