package client;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.RegisterResult;
import net.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerFacadeTests {

    private static Server server;

    @BeforeEach
    void clearDB() {
        ServerFacade facade = new ServerFacade(8080);
        try {
            facade.sendClearRequest();
        }
        catch (Exception ex){
            return;
        }
    }

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(8080);
        ServerFacade facade = new ServerFacade(port);
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
        ServerFacade facade = new ServerFacade(8080);
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
        ServerFacade facade = new ServerFacade(8080);
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
        ServerFacade facade = new ServerFacade(8080);
        RegisterRequest req = new RegisterRequest("big username", "big password", "big email");
        LoginRequest req1 = new LoginRequest("big username", "big password");
    }

    @Test
    @Order(4)
    public void loginInvalidUser() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(5)
    public void logoutValidIUser() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(6)
    public void logoutInvalidUser() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(7)
    public void createValidGame() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(8)
    public void createInvalidGame() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(9)
    public void joinValidGame() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(10)
    public void joinInvalidGame() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(11)
    public void validListGames() {
        ServerFacade facade = new ServerFacade(8080);
    }

    @Test
    @Order(12)
    public void invalidListGame() {
        ServerFacade facade = new ServerFacade(8080);
    }

}
