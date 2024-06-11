package client;

import requests.*;
import results.*;
import net.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;
import Service.ClearService;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeEach
    void clearDB() {
        //ServerFacade facade = new ServerFacade();
        try {
            ClearService service = new ClearService();
            service.clear(new SQLUserDAO(), new SQLAuthDAO(), new SQLGameDAO());
        }
        catch (Exception ex){
            return;
        }
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        facade = new ServerFacade(port);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    @Order(1)
    public void registerValidUser() {
        //ServerFacade facade = new ServerFacade(8080);
        RegisterRequest req = new RegisterRequest("big username", "big password", "big email");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            Assertions.assertEquals(res.username(), "big username");
        } catch (Exception ex) {
            Assertions.fail("Couldn't register valid user");
        }
    }

    @Test
    @Order(2)
    public void registerInvalidUser() {
        //ServerFacade facade = new ServerFacade(8080);
        RegisterRequest req = new RegisterRequest("big username", null, "big email");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
        } catch (Exception ex) {
            Assertions.assertEquals("Error: bad request", ex.getMessage());
        }
    }

    @Test
    @Order(3)
    public void loginValidUser() {
        //ServerFacade facade = new ServerFacade(8080);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        LoginRequest req1 = new LoginRequest("u", "p");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            LoginResult res1 = facade.sendLoginRequest(req1);
            Assertions.assertEquals("u", res1.username());
        } catch (Exception ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Test
    @Order(4)
    public void loginInvalidUser() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        LoginRequest req1 = new LoginRequest("u", "not the password");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            LoginResult res1 = facade.sendLoginRequest(req1);
        } catch (Exception ex) {
            Assertions.assertEquals("Error: unauthorized", ex.getMessage());
        }

    }

    @Test
    @Order(5)
    public void logoutValidIUser() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        LogoutRequest req1 = new LogoutRequest();

        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            LogoutResult res1 = facade.sendLogoutRequest(req1, res.authToken());
            Assertions.assertNotNull(res1);
        } catch (Exception ex) {
        }
    }

    @Test
    @Order(6)
    public void logoutInvalidUser() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        LogoutRequest req1 = new LogoutRequest();

        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            LogoutResult res1 = facade.sendLogoutRequest(req1, "probably not an authToken");
        } catch (Exception ex) {
            Assertions.assertEquals("Error: unauthorized", ex.getMessage());
        }
    }

    @Test
    @Order(7)
    public void createValidGame() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        CreateGameRequest req1 = new CreateGameRequest("big game");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            CreateGameResult res1 = facade.sendCreateGameRequest(req1, res.authToken());
            Assertions.assertTrue(res1.gameID() != null);
        } catch (Exception ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Test
    @Order(8)
    public void createInvalidGame() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        CreateGameRequest req1 = new CreateGameRequest("big game");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            CreateGameResult res1 = facade.sendCreateGameRequest(req1, "probably not an authToken");
        } catch (Exception ex) {
            Assertions.assertEquals("Error: unauthorized", ex.getMessage());
        }
    }

    @Test
    @Order(9)
    public void joinValidGame() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        CreateGameRequest req1 = new CreateGameRequest("big game");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            CreateGameResult res1 = facade.sendCreateGameRequest(req1, res.authToken());
            JoinGameRequest req2 = new JoinGameRequest("BLACK", res1.gameID());
            JoinGameResult res2 = facade.sendJoinGameRequest(req2, res.authToken());
            Assertions.assertNotNull(res2);
        } catch (Exception ex) {
            return;
        }
    }

    @Test
    @Order(10)
    public void joinInvalidGame() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        CreateGameRequest req1 = new CreateGameRequest("big game");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            CreateGameResult res1 = facade.sendCreateGameRequest(req1, res.authToken());
            JoinGameRequest req2 = new JoinGameRequest("BLACK", 3492);
            JoinGameResult res2 = facade.sendJoinGameRequest(req2, res.authToken());
        } catch (Exception ex) {
            Assertions.assertEquals("Error: bad request", ex.getMessage());
        }
    }

    @Test
    @Order(11)
    public void validListGames() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        CreateGameRequest req1 = new CreateGameRequest("big game");
        ListGamesRequest req2 = new ListGamesRequest();
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            CreateGameResult res1 = facade.sendCreateGameRequest(req1, res.authToken());
            ListGamesResult res2 = facade.sendListGamesRequest(req2, res.authToken());
            Assertions.assertNotNull(res2);
        } catch (Exception ex) {
            Assertions.fail(ex.getMessage());
        }
    }

    @Test
    @Order(12)
    public void invalidListGames() {
        //ServerFacade facade = new ServerFacade(0);
        RegisterRequest req = new RegisterRequest("u", "p", "big email");
        CreateGameRequest req1 = new CreateGameRequest("big game");
        ListGamesRequest req2 = new ListGamesRequest();
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            CreateGameResult res1 = facade.sendCreateGameRequest(req1, res.authToken());
            ListGamesResult res2 = facade.sendListGamesRequest(req2, "probably not an authToken");
        } catch (Exception ex) {
            Assertions.assertEquals("Error: unauthorized", ex.getMessage());
        }
    }

}
