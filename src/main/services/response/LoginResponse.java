package services.response;

/**
Response class for the Login service
 */
public class LoginResponse {
    /**
    message for any errors that occur
     */
    private String message;
    /**
    authToken created upon successful login
     */
    private String authToken;
    /**
    user provided string to identify user
     */
    private String username;

    /**
    LoginResponse class constructor
     */
    public LoginResponse() {}

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
