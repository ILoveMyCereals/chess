package dataaccess;

import model.AuthData;
import java.sql.Connection;
import java.sql.SQLException;

public interface AuthDAO {

    String createAuth(Connection conn, String username) throws SQLException;

    String verifyAuth(Connection conn, String authToken) throws SQLException;

    void deleteAuth(Connection conn, String authToken) throws SQLException;
}
