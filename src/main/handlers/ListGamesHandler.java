package handlers;

import adapters.GameImplAdapter;
import chess.GameImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataAccess.DataAccessException;
import services.ListGamesService;
import response.ListGamesResponse;
import spark.Request;
import spark.Response;

import java.util.Objects;

public class ListGamesHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        ListGamesService listGamesService = new ListGamesService();
        GsonBuilder gsonbuilder = new GsonBuilder();
        gsonbuilder.registerTypeAdapter(GameImpl.class, new GameImplAdapter());
        Gson gson = gsonbuilder.create();

        ListGamesResponse listGamesResponse = listGamesService.listGames(request.headers("authorization"));

        response.status(200);
        if (Objects.equals(listGamesResponse.getMessage(), "Error: unauthorized")) {
            response.status(401);
        }
        if (Objects.equals(listGamesResponse.getMessage(), "Error: description")) {
            response.status(500);
        }

        return gson.toJson(listGamesResponse);
    }

}
