package passoffTests.serverTests;

import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;
import services.request.*;
import services.response.*;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {

    @BeforeEach
    public void cleanup() {
        UserDAO userDAO = new UserDAO();
        AuthDAO authDAO = new AuthDAO();
        GameDAO gameDAO = new GameDAO();

        userDAO.Clear();
        authDAO.Clear();
        gameDAO.Clear();

        RegisterRequest request = new RegisterRequest();
        request.setUsername("collinlowree");
        request.setPassword("mypassword123");
        request.setEmail("collin@gmail.com");

        RegisterService registerService = new RegisterService();
        registerService.register(request);
    }

    @Test
    public void RegisterPositive() {
        UserDAO userDAO = new UserDAO();
        User user = null;
        user = userDAO.Find("collinlowree");

        assertEquals("collinlowree", user.getUsername());
        assertEquals("mypassword123", user.getPassword());
        assertEquals("collin@gmail.com", user.getEmail());

        AuthDAO authDAO = new AuthDAO();

        assertFalse(authDAO.FindAll().isEmpty());
    }
    @Test
    public void RegisterNegative() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("collinlowree");
        request.setPassword("mypassword123");
        request.setEmail("collin@gmail.com");

        RegisterService registerService = new RegisterService();
        RegisterResponse registerResponse = registerService.register(request);

        assertEquals( "Error: already taken", registerResponse.getMessage());

        RegisterRequest request2 = new RegisterRequest();
        request2.setUsername("collinlowree");
        request2.setPassword("mypassword123");

        RegisterResponse register2Response = registerService.register(request2);

        assertEquals( "Error: bad request", register2Response.getMessage());
    }

    @Test
    public void LogoutPositive() {
        AuthDAO authDAO = new AuthDAO();
        LogoutService logoutService = new LogoutService();

        logoutService.logout(authDAO.FindU("collinlowree").getAuthToken());

        assertTrue(authDAO.FindAll().isEmpty());
    }
    @Test
    public void LogoutNegative() {
        AuthDAO authDAO = new AuthDAO();
        LogoutService logoutService = new LogoutService();

        logoutService.logout("incorrect authToken");

        assertFalse(authDAO.FindAll().isEmpty());
    }

    @Test
    public void LoginPositive() {
        AuthDAO authDAO = new AuthDAO();
        LogoutService logoutService = new LogoutService();
        LoginService loginService = new LoginService();

        logoutService.logout(authDAO.FindU("collinlowree").getAuthToken());

        LoginRequest request = new LoginRequest();
        request.setUsername("collinlowree");
        request.setPassword("mypassword123");

        LoginResponse loginResponse = loginService.login(request);

        assertEquals(loginResponse.getAuthToken(), authDAO.FindU("collinlowree").getAuthToken());
    }
    @Test
    public void LoginNegative() {
        AuthDAO authDAO = new AuthDAO();
        LogoutService logoutService = new LogoutService();
        LoginService loginService = new LoginService();

        logoutService.logout(authDAO.FindU("collinlowree").getAuthToken());

        LoginRequest request = new LoginRequest();
        request.setUsername("collinlowre");
        request.setPassword("mypassword123");

        LoginResponse loginResponse = loginService.login(request);

        assertEquals("Error: unauthorized", loginResponse.getMessage());
    }

    @Test
    public void CreateGamePositive() {
        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        request.setGameName("Room One");

        service.createGame(request, authDAO.FindU("collinlowree").getAuthToken());

        assertNotNull(gameDAO.FindAll());
    }
    @Test
    public void CreateGameNegative() {
        CreateGameRequest request = new CreateGameRequest();
        CreateGameService service = new CreateGameService();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        CreateGameResponse response = service.createGame(request, authDAO.FindU("collinlowree").getAuthToken());
        assertEquals("Error: bad request", response.getMessage());

        request.setGameName("Room One");
        CreateGameResponse response2 = service.createGame(request, "bad authToken");
        assertEquals("Error: unauthorized", response2.getMessage());

        assertNotNull(gameDAO.FindAll());
    }

    @Test
    public void ListGamesPositive() {
        ListGamesService service = new ListGamesService();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        ListGamesResponse response = service.listGames(authDAO.FindU("collinlowree").getAuthToken());

        assertNull(response.getGames());

        CreateGameRequest request2 = new CreateGameRequest();
        CreateGameRequest request3 = new CreateGameRequest();
        CreateGameService service2 = new CreateGameService();
        request2.setGameName("Room Two");
        request3.setGameName("Room Three");

        service2.createGame(request2, authDAO.FindU("collinlowree").getAuthToken());
        service2.createGame(request3, authDAO.FindU("collinlowree").getAuthToken());
        service.listGames(authDAO.FindU("collinlowree").getAuthToken());


        assertEquals(2, gameDAO.FindAll().size());
    }
    @Test
    public void ListGamesNegative() {
        ListGamesService service = new ListGamesService();

        ListGamesResponse response = service.listGames("bad authToken");

        assertEquals("Error: unauthorized", response.getMessage());
    }

    @Test
    public void JoinGamePositive() {
        JoinGameRequest request = new JoinGameRequest();
        JoinGameService service = new JoinGameService();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();

        CreateGameRequest request2 = new CreateGameRequest();
        CreateGameService service2 = new CreateGameService();
        request2.setGameName("Room One");
        service2.createGame(request2, authDAO.FindU("collinlowree").getAuthToken());

        request.setGameID(1000);
        request.setPlayerColor("BLACK");

        service.joinGame(request, authDAO.FindU("collinlowree").getAuthToken());

        assertEquals("collinlowree", gameDAO.FindGN("Room One").getBlackUsername());
    }
    @Test
    public void JoinGameNegative() {
        JoinGameRequest request = new JoinGameRequest();
        JoinGameService service = new JoinGameService();
        AuthDAO authDAO = new AuthDAO();

        CreateGameRequest request2 = new CreateGameRequest();
        CreateGameService service2 = new CreateGameService();
        request2.setGameName("Room One");
        service2.createGame(request2, authDAO.FindU("collinlowree").getAuthToken());

        request.setGameID(911);
        request.setPlayerColor("BLACK");
        JoinGameResponse response = service.joinGame(request, authDAO.FindU("collinlowree").getAuthToken());
        assertEquals("Error: bad request", response.getMessage());

        request.setGameID(1000);
        request.setPlayerColor("BLACK");
        service.joinGame(request, authDAO.FindU("collinlowree").getAuthToken());
        JoinGameResponse response2 = service.joinGame(request, authDAO.FindU("collinlowree").getAuthToken());
        assertEquals("Error: already taken", response2.getMessage());

        request.setPlayerColor("WHITE");
        JoinGameResponse response3 = service.joinGame(request, "bad authToken");
        assertEquals("Error: unauthorized", response3.getMessage());
    }

    @Test
    public void ClearApplication() {
        ClearApplicationService service = new ClearApplicationService();
        GameDAO gameDAO = new GameDAO();
        AuthDAO authDAO = new AuthDAO();
        UserDAO userDAO = new UserDAO();

        CreateGameRequest request2 = new CreateGameRequest();
        CreateGameService service2 = new CreateGameService();
        request2.setGameName("Room One");
        service2.createGame(request2, authDAO.FindU("collinlowree").getAuthToken());

        service.clearApplication();

        assertEquals(0, gameDAO.FindAll().size());
        assertEquals(0, authDAO.FindAll().size());
        assertEquals(0, userDAO.FindAll().size());
    }
}
