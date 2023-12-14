package handlers;

import adapters.GameImplAdapter;
import chess.GameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import services.JoinGameService;
import request.JoinGameRequest;
import response.JoinGameResponse;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class JoinGameHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        JoinGameService joinGameService = new JoinGameService();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
        Gson gson = gsonbuilder.create();

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
