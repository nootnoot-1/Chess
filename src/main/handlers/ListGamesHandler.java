package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.ListGamesService;
import services.request.ListGamesRequest;
import services.response.ListGamesResponse;
import spark.Request;
import spark.Response;

import java.util.HashSet;
import java.util.Objects;

public class ListGamesHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        ListGamesService listGamesService = new ListGamesService();
        Gson gson = new Gson();

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
