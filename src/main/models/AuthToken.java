package models;


import dataAccess.AuthDAO;

import java.util.Objects;
import java.util.UUID;

/**
AuthToken class for server, contains all information about an AuthToken that the server may need to know
 */
public class AuthToken {
    /**
    Program made field for an authorized username
     */
    private String authToken;
    /**
    String identifier for specific user, made by user
     */
    private String username;

    /**
    Constructor for AuthToken
     */
    public AuthToken() {
        authToken = UUID.randomUUID().toString();
    }

    public AuthToken(String authToken, String username) {
        this.authToken = authToken;
        this.username = username;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof AuthToken temp)) return false;
        boolean flag = true;
        if (!Objects.equals(temp.getUsername(), username)) {
            flag = false;
        }
        if (!Objects.equals(temp.getAuthToken(), authToken)) {
            flag = false;
        }
        return flag;
    }
}
