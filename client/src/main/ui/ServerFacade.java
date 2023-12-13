package ui;

import com.google.gson.Gson;
import models.Game;
import request.*;
import response.*;

import java.net.*;
import java.io.*;
import java.util.Collection;
import java.util.Objects;

public class ServerFacade {
    private final String serverUrl;
    public ServerFacade(String url) {
        serverUrl = url;
    }

    public RegisterResponse register(RegisterRequest request) throws ResponseException {
        return this.makeRequest("POST", "/user", request, RegisterResponse.class, null);
    }

    public LoginResponse login(LoginRequest request) throws ResponseException {
        return this.makeRequest("POST", "/session", request, LoginResponse.class, null);
    }

    public void logout(String authToken) throws ResponseException {
        this.makeRequest("DELETE", "/session", null, null, authToken);
    }

    public CreateGameResponse createGame(CreateGameRequest request) throws ResponseException {
        return this.makeRequest("POST", "/game", request, CreateGameResponse.class, request.getAuthToken());
    }

    public ListGamesResponse listGames(ListGamesRequest request) throws ResponseException {
        return this.makeRequest("GET", "/game", request, ListGamesResponse.class , request.getAuthToken());
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass, String authToken) throws ResponseException {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            if (!Objects.equals(method, "GET")) {
                http.setDoOutput(true);
            }
            //http.setDoOutput(true);

            if (authToken!= null) {
                http.addRequestProperty("authorization", authToken);
            }
            if (!Objects.equals(method, "GET")) {
                writeBody(request, http);
            }
            //writeBody(request, http);
            http.connect();
            throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception e) {
            throw new ResponseException(500, e.getMessage());
        }
    }
    private static void writeBody(Object request, HttpURLConnection http) throws IOException {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            }
        }
    }
    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null && responseClass != ListGamesResponse.class) {
                    response = new Gson().fromJson(reader, responseClass);
                }
                int t;
                while((t=reader.read())!= -1)
                {
                    // convert the integer true to character
                    char r = (char)t;
                    System.out.println("Character : "+r);
                }
            }
        }
        return response;
    }
    private void throwIfNotSuccessful(HttpURLConnection http) throws IOException, ResponseException {
        var status = http.getResponseCode();
        if (!isSuccessful(status)) {
            throw new ResponseException(status, "failure: " + status);
        }
    }
    private boolean isSuccessful(int status) {
        return status / 100 == 2;
    }

    public class ResponseException extends Exception {
        final private int statusCode;

        public ResponseException(int statusCode, String message) {
            super(message);
            this.statusCode = statusCode;
        }

        public int StatusCode() {
            return statusCode;
        }
    }

}
