package services;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import services.request.ClearApplicationRequest;
import services.response.ClearApplicationResponse;

/**
Service for HTTP request to clear the application
 */
public class ClearApplicationService {

    /**
    clears the application
    @param ClearApplicationRequest r an object containing all request data
    @return ClearApplicationResponse an object containing all response data
     */
    public ClearApplicationResponse clearApplication() throws DataAccessException {
        ClearApplicationResponse clearApplicationResponse = new ClearApplicationResponse();

        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();

        userDAO.Clear();
        authDAO.Clear();
        gameDAO.Clear();

        return clearApplicationResponse;
    }
}
