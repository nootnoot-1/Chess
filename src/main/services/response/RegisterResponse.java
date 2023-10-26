package services.response;

/**
Response class for the Register service
 */
public class RegisterResponse {
    /**
    message for any errors that occur
     */
    private String message;
    /**
    user provided string to identify user
     */
    private String username;

    private String authToken;


    /**
    RegisterResponse class constructor
     */
    public RegisterResponse() {}

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
