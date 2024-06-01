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

    public String getPassword(Connection conn, String givenName) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("SELECT password, FROM user, WHERE username = ?")) {
            preparedStatement.setString(1, givenName);
            var queryResult = preparedStatement.executeQuery();
            var passResult = queryResult.getString("password");
            return passResult;
        }
    }

    public void createUser(Connection conn, String givenName, String password, String email) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("INSERT INTO chess (user) VALUES(?, ?, ?)")) {
            preparedStatement.setString(1, givenName);
            preparedStatement.setString(2, password);
            preparedStatement. setString(3, email);
            preparedStatement.executeUpdate();
        }
    }

    public void clearUsers(Connection conn) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("DROP TABLE user")) {
            preparedStatement.executeUpdate();
        }
    }
}
