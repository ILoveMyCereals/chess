package service;

import Requests.*;
import Results.*;
import Service.ClearService;
import Service.RegisterService;
import Service.LoginService;
import Service.LogoutService;
import Service.CreateGameService;
import Service.JoinGameService;
import Service.ListGamesService;
import chess.ChessGame;
import dataaccess.DataAccessException;
import dataaccess.MemoryAuthDAO;
import dataaccess.MemoryGameDAO;
import dataaccess.MemoryUserDAO;
import model.*;
import org.junit.jupiter.api.*;
import passoff.model.*;

import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServiceUnitTests {

    private static MemoryUserDAO userMemory;
    private static MemoryAuthDAO authMemory;
    private static MemoryGameDAO gameMemory;

@BeforeAll
static void init() {
    userMemory = new MemoryUserDAO(new ArrayList<>());
    authMemory = new MemoryAuthDAO(new ArrayList<>());
    gameMemory = new MemoryGameDAO(new ArrayList<>());
}

@BeforeEach
    void clearMemory() {
    ClearService service = new ClearService();
    service.clear(userMemory, authMemory, gameMemory);
}

@Test
    @Order(1)
    public void validRegisterUser() {
    RegisterService service = new RegisterService();
    try {
        RegisterResult result = service.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        Assertions.assertEquals("big password", userMemory.getPassword("big name"));
    }
    catch (DataAccessException ex) {
        return;
    }
}

@Test
    @Order(2)
    public void nullEmailRegister() {
    RegisterService service = new RegisterService();
    try {
        service.register(new RegisterRequest("big name", "big password", null), userMemory, authMemory);

    }
    catch (DataAccessException ex) {
        Assertions.assertEquals(ex.getMessage(), "Error: bad request");

    }
}

@Test
    @Order(3)
    public void validLogin() {
    LoginService service = new LoginService();
    RegisterService regService = new RegisterService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult result = service.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        Assertions.assertNotEquals(null, authMemory.verifyAuth(result.authToken()));
    }
    catch (DataAccessException ex){
        return;
    }
}

@Test
    @Order(4)
    public void invalidUsernameLogin() {
    LoginService service = new LoginService();
    RegisterService regService = new RegisterService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult result = service.login(new LoginRequest("big name", "big password1"), userMemory, authMemory);

    }
    catch (DataAccessException ex){
        Assertions.assertEquals(ex.getMessage(), "Error: unauthorized");
    }
}

@Test
    @Order(5)
    public void validLogout() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    LogoutService service = new LogoutService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        LogoutResult result = service.logout(inresult.authToken(), userMemory, authMemory);
        Assertions.assertEquals(null, result.message());
    }
    catch (DataAccessException ex){
        return;
    }
}

@Test
    @Order(6)
    public void invalidAuthLogout() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    LogoutService service = new LogoutService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        LogoutResult result = service.logout("this is probably not a valid token", userMemory, authMemory);
    }
    catch (DataAccessException ex) {
        Assertions.assertEquals(ex.getMessage(), "Error: unauthorized");
    }
}

@Test
    @Order(7)
    public void createValidGame() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    CreateGameService service = new CreateGameService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        CreateGameResult result = service.createGame(new CreateGameRequest("big chess game"), inresult.authToken(), gameMemory, authMemory);
        Assertions.assertNotEquals(null, result.gameID());
    }
    catch (DataAccessException ex){
        return;
    }
}

@Test
    @Order(8)
    public void invalidAuthCreateGame() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    CreateGameService service = new CreateGameService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        CreateGameResult result = service.createGame(new CreateGameRequest("big chess game"), "this is probably not a valid token", gameMemory, authMemory);
    }
    catch (DataAccessException ex){
        Assertions.assertEquals(ex.getMessage(), "Error: unauthorized");
    }
}

@Test
    @Order(9)
    public void joinValidGame() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    CreateGameService createService = new CreateGameService();
    JoinGameService service = new JoinGameService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        CreateGameResult createResult = createService.createGame(new CreateGameRequest("big chess game"), inresult.authToken(), gameMemory, authMemory);
        JoinGameResult result = service.joinGame(new JoinGameRequest("BLACK", createResult.gameID()), inresult.authToken(), gameMemory, authMemory);
        Assertions.assertEquals("big name", gameMemory.getGame(createResult.gameID()).getBlackUsername());
    }
    catch (DataAccessException ex) {
        return;
    }
}

@Test
    @Order(10)
    public void joinSameTeamTwice() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    CreateGameService createService = new CreateGameService();
    JoinGameService service = new JoinGameService();
    JoinGameService service2 = new JoinGameService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        CreateGameResult createResult = createService.createGame(new CreateGameRequest("big chess game"), inresult.authToken(), gameMemory, authMemory);
        JoinGameResult result = service.joinGame(new JoinGameRequest("BLACK", createResult.gameID()), inresult.authToken(), gameMemory, authMemory);
        JoinGameResult result2 = service.joinGame(new JoinGameRequest("BLACK", createResult.gameID()), inresult.authToken(), gameMemory, authMemory);
    }
    catch (DataAccessException ex) {
        Assertions.assertEquals(ex.getMessage(), "Error: already taken");
    }
}

@Test
    @Order(11)
    public void validListEmptyGamesList() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    ListGamesService service = new ListGamesService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        ListGamesResult result = service.listGames(inresult.authToken(), authMemory, gameMemory);
        Assertions.assertEquals(new ArrayList<GameData>(), result.games());
    }
    catch (DataAccessException ex) {
        return;
    }
}

@Test
    @Order(12)
    public void invalidAuthListGames() {
    RegisterService regService = new RegisterService();
    LoginService inService = new LoginService();
    ListGamesService service = new ListGamesService();
    try {
        RegisterResult regResult = regService.register(new RegisterRequest("big name", "big password", "nathan15@gmail.com"), userMemory, authMemory);
        LoginResult inresult = inService.login(new LoginRequest("big name", "big password"), userMemory, authMemory);
        ListGamesResult result = service.listGames("this is probably not a valid auth token", authMemory, gameMemory);
    }
    catch (DataAccessException ex) {
        Assertions.assertEquals(ex.getMessage(), "Error: unauthorized");
    }
}

@Test
    @Order(13)
    public void clearEmptyMemory() {
    ClearService service = new ClearService();
    service.clear(userMemory, authMemory, gameMemory);
    Assertions.assertNotNull(userMemory);
}
}
