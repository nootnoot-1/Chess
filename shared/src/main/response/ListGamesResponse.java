package response;

import models.Game;

import java.util.Collection;

/**
Response class for the ListGames service
 */
public class ListGamesResponse {
    /**
    authToken to verify user
     */
    private String authToken;
    /**
    collection of games that are to be listed
     */
    private Collection<Game> games;

    private String message;

    /**
    ListGamesResponse class constructor
     */
    public ListGamesResponse() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Collection<Game> getGames() {
        if (games.isEmpty()) {
            return null;
        }
        return games;
    }

    public void setGames(Collection<Game> games) {
        this.games = games;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
