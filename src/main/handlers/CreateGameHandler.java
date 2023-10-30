package handlers;

import com.google.gson.Gson;
import services.CreateGameService;
import services.request.CreateGameRequest;
import services.response.CreateGameResponse;
import spark.Request;
import spark.Response;

public class CreateGameHandler {

    public String handleRequest(Request request, Response response) {
        CreateGameService createGameService = new CreateGameService();
        Gson gson = new Gson();

        CreateGameRequest createGameRequest = gson.fromJson(request.body(), CreateGameRequest.class);

        CreateGameResponse createGameResponse = createGameService.createGame(createGameRequest);



        return null;
    }

}
