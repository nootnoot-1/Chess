package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.LoginService;
import services.request.LoginRequest;
import services.response.LoginResponse;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class LoginHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        LoginService loginService = new LoginService();
        Gson gson = new Gson();

        LoginRequest loginRequest = gson.fromJson(request.body(), LoginRequest.class);

        LoginResponse loginResponse = loginService.login(loginRequest);

        response.status(200);
        if (Objects.equals(loginResponse.getMessage(), "Error: unauthorized")) {
            response.status(401);
        }
//        if (Objects.equals(loginResponse.getMessage(), "Error: already logged in")) {
//            response.status(450);
//        }

        return gson.toJson(loginResponse);
    }

}
