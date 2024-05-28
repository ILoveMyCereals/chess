package dataaccess;

import model.AuthData;
import java.util.ArrayList;
import java.util.UUID;

public class MemoryAuthDAO implements AuthDAO {

    public MemoryAuthDAO(ArrayList<AuthData> authData) {
        this.authData = authData;
    }

    private ArrayList<AuthData> authData = new ArrayList<AuthData>();

    public String createAuth(String username) {
        String newAuth = UUID.randomUUID().toString();
        authData.add(new AuthData(newAuth, username));
        return newAuth;
    }

    public AuthData verifyAuth(String authToken) {
        for (AuthData auth : authData) {
            if (auth.authToken().equals(authToken)) {
                return auth;
            }
        }
        return null;
    }

    public void deleteAuth(String authToken) {
        for (AuthData auth: authData) {
            if (auth.authToken().equals(authToken)) {
                authData.remove(auth);
            }
        }
    }
}
