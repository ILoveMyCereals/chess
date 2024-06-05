package dataaccess;

import Requests.*;
import Results.*;
import Service.ClearService;
import dataaccess.DataAccessException;
import dataaccess.SQLDAO.SQLUserDAO;
import dataaccess.SQLDAO.SQLAuthDAO;
import dataaccess.SQLDAO.SQLGameDAO;
import model.*;
import org.junit.jupiter.api.*;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataAccessUnitTests {

    private static SQLUserDAO userMemory;
    private static SQLAuthDAO authMemory;
    private static SQLGameDAO gameMemory;

    @BeforeAll
    static void init() {
        userMemory = new SQLUserDAO();
        authMemory = new SQLAuthDAO();
        gameMemory = new SQLGameDAO();
    }

    @BeforeEach
    void clearMemory() {
        ClearService service = new ClearService();
        service.clear(userMemory, authMemory, gameMemory);
    }

    @Test
    @Order(1)
    public void createValidUser() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String nameResult = userMemory.getUser("big username");
            Assertions.assertEquals("big username", nameResult);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(2)
    public void createInvalidUser() {
        try {
            userMemory.createUser("big username", null, "big email");

        } catch (SQLException ex) {
            Assertions.assertEquals("Column 'password' cannot be null", ex.getMessage());
        }
    }

    @Test
    @Order(3)
    public void getValidUser() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String userResult = userMemory.getUser("big username");
            Assertions.assertEquals("big username", userResult);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(4)
    public void getInvalidUser() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String userResult = userMemory.getUser("real big username");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(5)
    public void getValidPassword() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String passResult = userMemory.getPassword("big username");
            Assertions.assertEquals("big password", passResult);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(6)
    public void getInvalidPassword() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String passResult = userMemory.getPassword("real big username");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(7)
    public void clearUser() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            userMemory.clearUsers();
            String userResult = userMemory.getUser("big username");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(8)
    public void createValidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Assertions.assertNotNull(authToken);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(9)
    public void createAuthInvalidUser() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("real big username");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(10)
    public void getValidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            String authResult = authMemory.getAuth("big username");
            Assertions.assertEquals(authToken, authResult);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(11)
    public void getInvalidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            String authResult = authMemory.getAuth("real big username");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(12)
    public void verifyValidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            AuthData authResult = authMemory.verifyAuth(authToken);
            Assertions.assertEquals(authResult.authToken(), authToken);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(13)
    public void verifyInvalidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            AuthData authResult = authMemory.verifyAuth("this is probably not an authToken");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(14)
    public void deleteValidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            authMemory.deleteAuth(authToken);
            AuthData authResult = authMemory.verifyAuth(authToken);
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(15)
    public void deleteInvalidAuth() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            authMemory.deleteAuth("This is probably not an authToken");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(16)
    public void clearAuths() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            authMemory.clearAuths();
            AuthData authResult = authMemory.verifyAuth(authToken);
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(17)
    public void createValidGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            Assertions.assertNotNull(gameID);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(18)
    public void createInvalidGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame(" real big username");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(19)
    public void getValidGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            GameData gameResult = gameMemory.getGame(gameID);
            Assertions.assertEquals(gameResult.getGameID(), gameID);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(20)
    public void getInvalidGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            GameData gameResult = gameMemory.getGame(654654384);
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(21)
    public void joinValidGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            GameData gameResult = gameMemory.getGame(gameID);
            boolean joinedGame = gameMemory.setTeamUser(gameResult, "big username", "BLACK");
            Assertions.assertTrue(joinedGame);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(22)
    public void joinInvalidGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            GameData gameResult = gameMemory.getGame(gameID);
            boolean joinedGame = gameMemory.setTeamUser(gameResult, "real big username", "BLACK");
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(23)
    public void validListGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            ArrayList<GameData> gamesArray = gameMemory.listGames();
            Assertions.assertNotNull(gamesArray);
        } catch (SQLException ex) {
            return;
        }
    }

    @Test
    @Order(24)
    public void invalidListGame() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            ArrayList<GameData> gamesArray = gameMemory.listGames();
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

    @Test
    @Order(25)
    public void clearGames() {
        try {
            userMemory.createUser("big username", "big password", "big email");
            String authToken = authMemory.createAuth("big username");
            Integer gameID = gameMemory.createGame("big username");
            gameMemory.clearGames();
            GameData gameResult = gameMemory.getGame(gameID);
        } catch (SQLException ex) {
            Assertions.assertEquals("Illegal operation on empty result set.", ex.getMessage());
        }
    }

}
