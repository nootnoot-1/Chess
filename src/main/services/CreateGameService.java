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
    @return CreateGameResponse an object containing all response data
     */
    public CreateGameResponse createGame(CreateGameRequest r, String authToken) {
        CreateGameResponse createGameResponse = new CreateGameResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            authDAO.Find(authToken);
        } catch (DataAccessException e) {
            createGameResponse.setMessage("Error: unauthorized");
            return createGameResponse;
        }
        if (r.getGameName() == null) {
            createGameResponse.setMessage("Error: bad request");
            return createGameResponse;
        }
        try {
            gameDAO.FindGN(r.getGameName());
            createGameResponse.setMessage("Error: game name already taken");
            return createGameResponse;
        } catch (DataAccessException ignored) {}


        try {
            Game game = new Game(r.getGameName());
            gameDAO.Insert(game);
            createGameResponse.setGameID(game.getGameID());
        } catch (DataAccessException e) {
            createGameResponse.setMessage(e.getMessage());
            return createGameResponse;
        }

        return createGameResponse;
    }
}
