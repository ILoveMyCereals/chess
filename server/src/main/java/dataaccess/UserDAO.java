package dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDAO {



    String getUser(Connection conn, String username) throws SQLException;

    String getPassword(Connection conn, String username) throws SQLException;

    void createUser(Connection conn, String username, String password, String email) throws SQLException;
}
