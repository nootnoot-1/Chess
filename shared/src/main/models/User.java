package models;

import java.util.Objects;

/**
Model class for Users, contains all necessary information that the server will need to know about a specific user
 */
public class User {
    /**
    String identifier for specific user, made by user
    */
    private String username;
    /**
    String storage of users password
     */
    private String password;
    /**
    String storage of users email
     */
    private String email;

    /**
    User class constructor
     */
    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User temp)) return false;
        boolean flag = true;
        if (!Objects.equals(temp.getUsername(), username)) {
            flag = false;
        }
        if (!Objects.equals(temp.getPassword(), password)) {
            flag = false;
        }
        if (!Objects.equals(temp.getEmail(), email)) {
            flag = false;
        }
        return flag;
    }
}
