package services.request;

/*
Request class for the Login service
 */
public class LoginRequest {
    /*
    user provided string to identify user
     */
    private String username;
    /*
    user provided string to verify user
     */
    private String password;

    /*
    LoginRequest class constructor
     */
    public LoginRequest() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
