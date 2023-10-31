package handlers;

import com.google.gson.Gson;
import services.JoinGameService;
import services.request.JoinGameRequest;
import services.response.JoinGameResponse;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class JoinGameHandler {

    public String handleRequest(Request request, Response response) {
        JoinGameService joinGameService = new JoinGameService();
        Gson gson = new Gson();

        JoinGameRequest joinGameRequest = gson.fromJson((request.body()), JoinGameRequest.class);
        String authToken = request.headers("authorization");

        JoinGameResponse joinGameResponse = joinGameService.joinGame(joinGameRequest, authToken);

        response.status(200);
        if (Objects.equals(joinGameResponse.getMessage(), "Error: unauthorized")) {
            response.status(401);
        }
        if (Objects.equals(joinGameResponse.getMessage(), "Error: bad request")) {
            response.status(400);
        }
        if (Objects.equals(joinGameResponse.getMessage(), "Error: already taken")) {
            response.status(403);
        }
        if (Objects.equals(joinGameResponse.getMessage(), "Error: description")) {
            response.status(500);
        }

        return gson.toJson(joinGameResponse);
    }

}
