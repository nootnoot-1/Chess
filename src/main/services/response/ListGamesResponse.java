package services.response;

import models.Game;

import java.util.Collection;

/*
Response class for the ListGames service
 */
public class ListGamesResponse {
    /*
    authToken to verify user
     */
    private String authToken;
    /*
    collection of games that are to be listed
     */
    private Collection<Game> games;

    /*
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
        return games;
    }

    public void setGames(Collection<Game> games) {
        this.games = games;
    }
}
