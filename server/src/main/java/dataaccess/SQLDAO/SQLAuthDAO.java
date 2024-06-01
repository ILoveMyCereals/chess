package dataaccess.SQLDAO;

import dataaccess.AuthDAO;
import model.AuthData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    public String createAuth(Connection conn, String givenName) throws SQLException {
        String newAuth = UUID.randomUUID().toString();
        try (var preparedStatement = conn.prepareStatement("INSERT INTO chess (auth) VALUES(?, ?)")) {
            preparedStatement.setString(1, givenName);
            preparedStatement.setString(2, newAuth);
            preparedStatement.executeUpdate();
        }
        return newAuth;
    }

    public String verifyAuth(Connection conn, String givenToken) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("SELECT authToken, FROM auth, WHERE givenToken=?")) {
            preparedStatement.setString(1, givenToken);
            var queryResult = preparedStatement.executeQuery();
            var passResult = queryResult.getString("authToken");
            return passResult;
        }
    }

    public void deleteAuth(Connection conn, String authToken) throws SQLException {
        return;
    }

    public void clearAuths(Connection conn) throws SQLException {
        return;
    }
}
