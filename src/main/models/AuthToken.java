package models;


/*
AuthToken class for server, contains all information about an AuthToken that the server may need to know
 */
public class AuthToken {
    /*
    Program made field for an authorized username
     */
    private String authToken;
    /*
    String identifier for specific user, made by user
     */
    private String username;

    /*
    Constructor for AuthToken
     */
    public AuthToken() {}

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
