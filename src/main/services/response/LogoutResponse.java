package services.response;

/*
Response class for the Logout service
 */
public class LogoutResponse {
    /*
    message for any errors that occur
     */
    private String message;
    /*
    authToken to verify user
     */
    private String authToken;

    /*
    Logout class constructor
     */
    public LogoutResponse() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
