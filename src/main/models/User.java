package models;

/*
Model class for Users, contains all necessary information that the server will need to know about a specific user
 */
public class User {
    /*
    String identifier for specific user, made by user
    */
    private String username;
    /*
    String storage of users password
     */
    private String password;
    /*
    String storage of users email
     */
    private String email;

    /*
    User class constructor
     */
    public User() {}

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
