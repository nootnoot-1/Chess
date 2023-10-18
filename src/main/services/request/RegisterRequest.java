package services.request;

/*
Request class for the Register service
 */
public class RegisterRequest {
    /*
    user provided string to identify user
     */
    private String username;
    /*
    user provided string to verify user
     */
    private String password;
    /*
    user provided email
     */
    private String email;

    /*
    RegisterRequest class constructor
     */
    public RegisterRequest() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
