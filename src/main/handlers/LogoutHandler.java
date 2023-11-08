package handlers;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import dataAccess.DataAccessException;
import services.LogoutService;
import services.request.LogoutRequest;
import services.response.LogoutResponse;
import spark.Request;
import spark.Response;

import java.io.Reader;
import java.util.Objects;

public class LogoutHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        LogoutService logoutService = new LogoutService();
        Gson gson = new Gson();

        LogoutResponse logoutResponse = logoutService.logout(request.headers("authorization"));

        response.status(200);
        if (Objects.equals(logoutResponse.getMessage(), "Error: unauthorized")) {
            response.status(401);
        }
        if (Objects.equals(logoutResponse.getMessage(), "Error: description")) {
            response.status(500);
        }

        return gson.toJson(logoutResponse);
    }
}
