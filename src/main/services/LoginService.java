package services;

import com.mysql.cj.log.Log;
import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import services.request.LoginRequest;
import services.response.LoginResponse;

import java.util.Objects;

/**
Service for HTTP request to log in
 */
public class LoginService {

    /**
    logs the user into the server
    @param LoginRequest r an object containing all request data
    @return LoginResponse an object containing all response data
     */
    public LoginResponse login(LoginRequest r) {
        LoginResponse loginResponse = new LoginResponse();
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();

        User user = userDAO.Find(r.getUsername());

        if (user == null || !Objects.equals(user.getPassword(), r.getPassword())) {
            loginResponse.setMessage("Error: unauthorized");
            return loginResponse;
        }

        for (AuthToken it : authDAO.FindAll()) {
            if (Objects.equals(it.getUsername(), r.getUsername())) {
                loginResponse.setMessage("Error: already logged in");
                return loginResponse;
            }
        }

        AuthToken authToken = new AuthToken();
        authDAO.Insert(authToken);

        loginResponse.setAuthToken(authToken.getAuthToken());

        return loginResponse;
    }
}
