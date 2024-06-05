package dataaccess;

import model.AuthData;
import java.sql.Connection;
import java.sql.SQLException;

public interface AuthDAO {

    String createAuth(String username) throws SQLException;

    AuthData verifyAuth(String authToken) throws SQLException;

    void deleteAuth(String authToken) throws SQLException;
}
