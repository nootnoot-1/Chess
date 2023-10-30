package services;

import dataAccess.AuthDAO;
import dataAccess.UserDAO;
import services.response.LogoutResponse;

/**
Service for HTTP request to log out
 */
public class LogoutService {

    /**
    logs user out of the service
    @param LogoutRequest r an object containing all request data
    @return LogoutResponse an object containing all response data
     */
    public LogoutResponse logout(String authToken) {
        LogoutResponse logoutResponse = new LogoutResponse();
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();

        if (authDAO.Find(authToken) == null) {
            logoutResponse.setMessage("Error: unauthorized");
            return logoutResponse;
        }

        authDAO.Remove(authToken);

        logoutResponse.setAuthToken(authToken);

        return logoutResponse;
    }
}
