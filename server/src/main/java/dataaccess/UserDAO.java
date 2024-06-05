package dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDAO {



    String getUser(String username) throws SQLException;

    String getPassword(String username) throws SQLException;

    void createUser(String username, String password, String email) throws SQLException;
}
