package net;

import requests.*;
import results.*;
import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;

public class ServerFacade {
    //This class will have a different public method for each of my seven endpoints
    //The endpoint I want to call in the server is specified by the path "/user", /session", etc and the request type (get, post, etc.)
    //these combinations of paths and request types can be found in the endpoints section of my server class
    //This class will take request type objects and will use them to generate HTTP requests, which it will send to the server (using code like in the Web Client example)
    //When a user logs in, this class will have to hold onto the authToken generated, because it will be needed for some of the HTTP requests

    private String URISTUB;

    public ServerFacade(Integer port) {
        URISTUB = "http://localhost:" + Integer.toString(port);
    }

    public RegisterResult sendRegisterRequest(RegisterRequest req) throws Exception {
        URI uri = new URI(URISTUB + "/user");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setDoOutput(true);
        http.setRequestMethod("POST");
        try (var outputStream = http.getOutputStream()) {
            var json = new Gson().toJson(req);
            outputStream.write(json.getBytes());
        }

        http.connect();
        var status = http.getResponseCode();

        if (status == 400) {
            throw new Exception("Error: bad request");
        } else if (status == 403) {
            throw new Exception("Error: already taken");
        }

        try(InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            RegisterResult res = new Gson().fromJson(inputStreamReader, RegisterResult.class);
            return res;
            //http.getResponseCode();
        }
    }

    public LoginResult sendLoginRequest(LoginRequest req) throws Exception {
        URI uri = new URI(URISTUB + "/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setDoOutput(true);
        http.setRequestMethod("POST");
        try (var outputStream = http.getOutputStream()) {
            var json = new Gson().toJson(req);
            outputStream.write(json.getBytes());
        }

        http.connect();
        var status = http.getResponseCode();

        if (status == 401) {
            throw new Exception("Error: unauthorized");
        }

        try(InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            LoginResult res = new Gson().fromJson(inputStreamReader, LoginResult.class);
            return res;
        }
    }

    public LogoutResult sendLogoutRequest(LogoutRequest req, String authToken) throws Exception {
        URI uri = new URI(URISTUB + "/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setDoOutput(true);
        http.setRequestMethod("DELETE");
        http.addRequestProperty("authorization", authToken);

        http.connect();
        var status = http.getResponseCode();

        if (status == 401) {
            throw new Exception("Error: unauthorized");
        }

        try(InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            LogoutResult res = new Gson().fromJson(inputStreamReader, LogoutResult.class);
            return res;
        }
    }

    public CreateGameResult sendCreateGameRequest(CreateGameRequest req, String authToken) throws Exception {
        URI uri = new URI(URISTUB + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setDoOutput(true);
        http.setRequestMethod("POST");
        http.addRequestProperty("authorization", authToken);
        try (var outputStream = http.getOutputStream()) {
            var json = new Gson().toJson(req);
            outputStream.write(json.getBytes());
        }
        //How do I set the request body?

        http.connect();
        var status = http.getResponseCode();

        if (status == 400) {
            throw new Exception("Error: bad request");
        } else if (status == 401) {
            throw new Exception("Error: unauthorized");
        }

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            CreateGameResult res = new Gson().fromJson(inputStreamReader, CreateGameResult.class);
            return res;
        }
    }

    public JoinGameResult sendJoinGameRequest(JoinGameRequest req, String authToken) throws Exception {
        URI uri = new URI(URISTUB + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setDoOutput(true);
        http.setRequestMethod("PUT");
        http.addRequestProperty("authorization", authToken);
        try (var outputStream = http.getOutputStream()) {
            var json = new Gson().toJson(req);
            outputStream.write(json.getBytes());
        }

        http.connect();
        var status = http.getResponseCode();

        if (status == 400) {
            throw new Exception("Error: bad request");
        } else if (status == 401) {
            throw new Exception("Error: unauthorized");
        } else if (status == 403) {
            throw new Exception("Error: already taken");
        }

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            JoinGameResult res = new Gson().fromJson(inputStreamReader, JoinGameResult.class);
            return res;
        }
    }

    public ListGamesResult sendListGamesRequest(ListGamesRequest req, String authToken) throws Exception {
        URI uri = new URI(URISTUB + "/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setDoOutput(true);
        http.setRequestMethod("GET");
        http.addRequestProperty("authorization", authToken);
        //How do I set the request body?

        http.connect();
        var status = http.getResponseCode();

        if (status == 401) {
            throw new Exception("Error: unauthorized");
        }

        try (InputStream respBody = http.getInputStream()) {
            InputStreamReader inputStreamReader = new InputStreamReader(respBody);
            ListGamesResult res = new Gson().fromJson(inputStreamReader, ListGamesResult.class);
            return res;
        }
    }

    public void sendClearRequest() throws Exception {
        URI uri = new URI(URISTUB + "/db");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");

        http.connect();
    }
}
