package server;

import Handlers.RegisterHandler;
import Handlers.LoginHandler;
import Handlers.LogoutHandler;
import Handlers.CreateGameHandler;
import Handlers.JoinGameHandler;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import java.util.ArrayList;

import model.AuthData;
import model.GameData;
import model.UserData;
import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.

        MemoryUserDAO userMemory = new MemoryUserDAO(new ArrayList<UserData>());
        MemoryAuthDAO authMemory = new MemoryAuthDAO(new ArrayList<AuthData>());
        MemoryGameDAO gameMemory = new MemoryGameDAO(new ArrayList<GameData>());

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

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
