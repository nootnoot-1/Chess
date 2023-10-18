package services.response;

/*
Response class for the Register service
 */
public class RegisterResponse {
    /*
    message for any errors that occur
     */
    private String message;
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
    RegisterResponse class constructor
     */
    public RegisterResponse() {}

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
