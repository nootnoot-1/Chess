package handlers;


import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.RegisterService;
import services.request.RegisterRequest;
import services.response.RegisterResponse;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class RegisterHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        RegisterService registerService = new RegisterService();
        Gson gson = new Gson();

        //registerRequest = gson converted HTTP request
        RegisterRequest registerRequest = gson.fromJson((request.body()), RegisterRequest.class);

        //get registerResponse from registerService
        RegisterResponse registerResponse = registerService.register(registerRequest);

        //set status
        response.status(200);
        if (Objects.equals(registerResponse.getMessage(), "Error: bad request")) {
            response.status(400);
        }
        if (Objects.equals(registerResponse.getMessage(), "Error: already taken")) {
            response.status(403);
        }
        if (Objects.equals(registerResponse.getMessage(), "Error: description")) {
            response.status(500);
        }

        return gson.toJson(registerResponse);
    }
}
