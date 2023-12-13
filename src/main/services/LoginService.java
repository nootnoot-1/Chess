package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import request.LoginRequest;
import response.LoginResponse;

import java.util.Objects;

/**
Service for HTTP request to log in
 */
public class LoginService {

    /**
    logs the user into the server
    @return LoginResponse an object containing all response data
     */
    public LoginResponse login(LoginRequest r) {
        LoginResponse loginResponse = new LoginResponse();
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();

        try {
            User user = userDAO.Find(r.getUsername());

            if (user == null || !Objects.equals(user.getPassword(), r.getPassword())) {
                loginResponse.setMessage("Error: unauthorized");
                return loginResponse;
            }
        } catch (DataAccessException e) {
            loginResponse.setMessage(e.getMessage());
            return loginResponse;
        }

        AuthToken authToken = new AuthToken();
        authToken.setUsername(r.getUsername());

        try {
            authDAO.Insert(authToken);
        } catch (DataAccessException e) {
            loginResponse.setMessage(e.getMessage());
            return loginResponse;
        }

        loginResponse.setAuthToken(authToken.getAuthToken());
        loginResponse.setUsername(r.getUsername());

        return loginResponse;
    }
}
