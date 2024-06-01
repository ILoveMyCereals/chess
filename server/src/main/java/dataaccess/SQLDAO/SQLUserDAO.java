package dataaccess.SQLDAO;

import dataaccess.UserDAO;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    public String getUser(Connection conn, String givenName) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("SELECT username, FROM user, WHERE username=?")) {
            preparedStatement.setString(1, givenName);
            var queryResult = preparedStatement.executeQuery();
            var nameResult = queryResult.getString("username");
            return nameResult;
        }
    }

    public String getPassword(Connection conn, String username) throws SQLException {
        return null;
    }

    public void createUser(Connection conn, String username, String password, String email) throws SQLException {
        return;
    }

    public void clearUsers(Connection conn) {
        return;
    }
}
