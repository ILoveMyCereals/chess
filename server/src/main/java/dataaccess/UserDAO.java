package dataaccess;

public interface UserDAO {



    String getUser(String username);

    String getPassword(String username);

    void createUser(String username, String password, String email);
}
