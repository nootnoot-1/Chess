package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import services.request.CreateGameRequest;
import services.response.CreateGameResponse;
import services.response.LogoutResponse;

/**
Service for HTTP request to create a game
 */
public class CreateGameService {

    /**
    creates a game
    @param CreateGameRequest r an object containing all request data
    @return CreateGameResponse an object containing all response data
     */
    public CreateGameResponse createGame(CreateGameRequest r, String authToken) throws DataAccessException {
        CreateGameResponse createGameResponse = new CreateGameResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        if (authDAO.Find(authToken) == null) {
            createGameResponse.setMessage("Error: unauthorized");
            return createGameResponse;
        }
        if (r.getGameName() == null) {
            createGameResponse.setMessage("Error: bad request");
            return createGameResponse;
        }
        if (gameDAO.FindGN(r.getGameName()) != null) {
            createGameResponse.setMessage("Error: game name already taken");
            return createGameResponse;
        }

        Game game = new Game(r.getGameName());
        gameDAO.Insert(game);

        createGameResponse.setGameID(game.getGameID());

        return createGameResponse;
    }
}
