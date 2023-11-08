package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import models.Game;
import services.request.ListGamesRequest;
import services.response.ListGamesResponse;

/**
Service for HTTP request to list all games
 */
public class ListGamesService {

    /**
    lists all games
    @param ListGamesRequest r an object containing all request data
    @return ListGamesResponse an object containing all response data
     */
    public ListGamesResponse listGames(String authToken) throws DataAccessException {
        ListGamesResponse listGamesResponse = new ListGamesResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        if (authDAO.Find(authToken) == null) {
            listGamesResponse.setMessage("Error: unauthorized");
            return listGamesResponse;
        }

        listGamesResponse.setGames(gameDAO.FindAll());

        return listGamesResponse;
    }
}
