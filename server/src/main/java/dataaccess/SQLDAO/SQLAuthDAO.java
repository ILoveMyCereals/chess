package dataaccess.SQLDAO;

import dataaccess.AuthDAO;
import model.AuthData;

public class SQLAuthDAO implements AuthDAO {

    public String createAuth(String username) {
        return null;
    }

    public AuthData verifyAuth(String authToken) {
        return null;
    }

    public void deleteAuth(String authToken) {
        return;
    }

    public void clearAuths() {
        return;
    }
}
