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
    @return ListGamesResponse an object containing all response data
     */
    public ListGamesResponse listGames(String authToken) {
        ListGamesResponse listGamesResponse = new ListGamesResponse();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            authDAO.Find(authToken);
        } catch (DataAccessException e) {
            listGamesResponse.setMessage("Error: unauthorized");
            return listGamesResponse;
        }

        try {
            listGamesResponse.setGames(gameDAO.FindAll());
        } catch (DataAccessException e) {
            listGamesResponse.setMessage(e.getMessage());
            return listGamesResponse;
        }

        return listGamesResponse;
    }
}
