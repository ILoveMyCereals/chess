package dataaccess.sqldao;

import dataaccess.DataAccessException;
import dataaccess.DatabaseManager;
import dataaccess.UserDAO;

import java.sql.SQLException;

public class SQLUserDAO implements UserDAO {

    public String getUser(String givenName) throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT username FROM Users WHERE username=?")) {
                preparedStatement.setString(1, givenName);
                var queryResult = preparedStatement.executeQuery();
                queryResult.next();
                var nameResult = queryResult.getString("username");
                return nameResult;
            }
        } catch (DataAccessException ex) {
            throw new SQLException(ex.getMessage());
        }
    }

    public String getPassword(String givenName) throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("SELECT password FROM Users WHERE username=?")) {
                preparedStatement.setString(1, givenName);
                var queryResult = preparedStatement.executeQuery();
                queryResult.next();
                var passResult = queryResult.getString("password");
                return passResult;
            }
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public void createUser(String givenName, String password, String email) throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO Users (username, password, email) VALUES(?, ?, ?)")) {
                preparedStatement.setString(1, givenName);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException ex) {
            return;
        }
    }

    public void clearUsers() throws SQLException {
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("TRUNCATE TABLE Users")) {
                preparedStatement.executeUpdate();
            }
        } catch (DataAccessException ex) {
            return;
        }
    }
}
