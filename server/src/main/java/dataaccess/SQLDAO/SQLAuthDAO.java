package dataaccess.SQLDAO;

import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import model.AuthData;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

public class SQLAuthDAO implements AuthDAO {

    public String getAuth(String givenName) throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT authToken FROM Auth WHERE username = ?")) {
                preparedStatement.setString(1, givenName);
                var queryResult = preparedStatement.executeQuery();
                queryResult.next();
                var authResult = queryResult.getString("authToken");
                return authResult;
            }
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public String createAuth(String givenName) throws SQLException {
        String newAuth = UUID.randomUUID().toString();
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO Auth (username, authToken) VALUES(?, ?)")) {
                preparedStatement.setString(1, givenName);
                preparedStatement.setString(2, newAuth);
                preparedStatement.executeUpdate();
            }
            return newAuth;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public AuthData verifyAuth(String givenToken) throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT authToken, username FROM Auth WHERE authToken=?")) {
                preparedStatement.setString(1, givenToken);
                var queryResult = preparedStatement.executeQuery();
                queryResult.next();
                var authResult = queryResult.getString("authToken");
                var userResult = queryResult.getString("username");
                AuthData passResult = new AuthData(authResult, userResult);
                return passResult;
            }
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public void deleteAuth(String givenToken) throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("DELETE FROM Auth WHERE authToken=?")) {
                preparedStatement.setString(1, givenToken);
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException ex) {
            return;
        }
    }

    public void clearAuths() throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE TABLE Auth")) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException ex) {
            return;
        }
    }
}
