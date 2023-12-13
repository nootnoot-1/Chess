package request;

/**
Request class for the Create Game service
 */
public class CreateGameRequest {
    /**
    String to name the game
     */
    private String gameName;
    /**
    authToken to verify user
     */
    private String authToken;

    /**
    Constructor for the CreateGameRequest class
     */
    public CreateGameRequest() {}

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
