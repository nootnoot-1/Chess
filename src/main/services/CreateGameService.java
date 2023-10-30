package services;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import services.request.CreateGameRequest;
import services.response.CreateGameResponse;

/**
Service for HTTP request to create a game
 */
public class CreateGameService {

    /**
    creates a game
    @param CreateGameRequest r an object containing all request data
    @return CreateGameResponse an object containing all response data
     */
    public CreateGameResponse createGame(CreateGameRequest r) {
        CreateGameResponse createGameResponse = new CreateGameResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        if (r.getGameName() == null) {
            createGameResponse.setMessage("Error: bad request");
            return createGameResponse;
        }
        if (authDAO.Find(r.getAuthToken()) == null) {
            createGameResponse.setMessage("Error: unathorized");
            return createGameResponse;
        }



        return null;
    }
}
