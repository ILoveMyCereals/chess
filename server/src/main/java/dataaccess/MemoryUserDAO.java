package dataaccess;

import model.UserData;

import java.util.ArrayList;

public class MemoryUserDAO implements UserDAO {

    public MemoryUserDAO(ArrayList<UserData> users) {
        this.users = users;
    }

    private ArrayList<UserData> users;

    public String getUser(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return username;
            }
        }
        return null;
    }

    public String getPassword(String username) {
        for (UserData user : users) {
            if (user.username().equals(username)) {
                return user.password();
            }
        }
        return null;
    }

    public void createUser(String username, String password, String email) {
        UserData newUser = new UserData(username, password, email);
        users.add(newUser);
    }

    public void clearUsers() {
        users.clear();
        }
    }
