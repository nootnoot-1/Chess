package services.request;

/**
Request class for the Logout service
 */
public class LogoutRequest {
    /**
    authToken to verify user
     */
    private String authToken;

    /**
    Logout class constructor
     */
    public LogoutRequest() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
