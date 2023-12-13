package request;

/**
Request class for the ListGames service
 */
public class ListGamesRequest {

    /**
    authToken to verify user
     */
    private String authToken;

    /**
    ListGamesRequest class constructor
     */
    public ListGamesRequest() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
