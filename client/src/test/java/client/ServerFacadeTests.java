package client;

import Requests.RegisterRequest;
import Results.RegisterResult;
import net.ServerFacade;
import org.junit.jupiter.api.*;
import server.Server;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ServerFacadeTests {

    private static Server server;

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
        //var port = server.run(8080);
        ServerFacade facade = new ServerFacade(8080);
        RegisterRequest req = new RegisterRequest("big username", "big password", "big email");
        try {
            RegisterResult res = facade.sendRegisterRequest(req);
            Assertions.assertEquals(res.username(), "big username");
        } catch (Exception ex) {
            return;
        }
    }

    @Test
    @Order(2)
    public void registerInvalidUser() {
        var port = server.run(8080);
        ServerFacade facade = new ServerFacade(port);
    }

    @Test
    @Order(3)
    public void loginValidUser() {
        var port = server.run(8080);
        ServerFacade facade = new ServerFacade(port);
    }

    @Test
    @Order(4)
    public void loginInvalidUser() {
        var port = server.run(8080);
        ServerFacade facade = new ServerFacade(port);
    }

    @Test
    @Order(5)
    public void logoutValidIUser() {
        var port = server.run(8080);
        ServerFacade facade = new ServerFacade(port);
    }

    @Test
    @Order(6)
    public void logoutInvalidUser() {
        var port = server.run(8080);
        ServerFacade facade = new ServerFacade(port);
    }

    //@Test
    //@Order(7)

}
