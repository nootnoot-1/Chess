package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.CreateGameService;
import request.CreateGameRequest;
import response.CreateGameResponse;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class CreateGameHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        CreateGameService createGameService = new CreateGameService();
        Gson gson = new Gson();

        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);
        String authToken = request.headers("authorization");

        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest, authToken);

        response.status(200);
        if (Objects.equals(createGameResponse.getMessage(), "Error: bad request")) {
            response.status(400);
            String jstr = gson.toJson(createGameResponse);
            return jstr.replace(",\"gameID\":0", "");
        }
        if (Objects.equals(createGameResponse.getMessage(), "Error: unauthorized")) {
            response.status(401);
            String jstr = gson.toJson(createGameResponse);
            return jstr.replace(",\"gameID\":0", "");
        }
        if (Objects.equals(createGameResponse.getMessage(), "Error: game name already taken")) {
            response.status(500);
            String jstr = gson.toJson(createGameResponse);
            return jstr.replace(",\"gameID\":0", "");
        }

        return gson.toJson(createGameResponse);
    }

}
