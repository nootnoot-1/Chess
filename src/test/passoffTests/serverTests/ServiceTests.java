package passoffTests.serverTests;

import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.LogoutService;
import services.RegisterService;
import services.request.LogoutRequest;
import services.request.RegisterRequest;
import services.response.RegisterResponse;

import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTests {
    @Test
    public void simpleAssetionTest() {
        assertEquals(200, 100+100);
        assertTrue(100 == 2 * 50);
        assertNotNull(new Object(), "Response did not return authentication String");
    }

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
    public void RegisterPositiveTest() {
        UserDAO userDAO = new UserDAO();
        User user = userDAO.Find("collinlowree");

        assertEquals("collinlowree", user.getUsername());
        assertEquals("mypassword123", user.getPassword());
        assertEquals("collin@gmail.com", user.getEmail());

        AuthDAO authDAO = new AuthDAO();

        assertFalse(authDAO.FindAll().isEmpty());
    }

    @Test
    public void RegisterNegativeTest() {
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
    public void LogoutPositiveTest() {
        AuthDAO authDAO = new AuthDAO();
        LogoutService logoutService = new LogoutService();

        logoutService.logout(authDAO.FindU("collinlowree").getAuthToken());

        assertTrue(authDAO.FindAll().isEmpty());
    }

    @Test
    public void LogoutNegativeTest() {
        AuthDAO authDAO = new AuthDAO();
        LogoutService logoutService = new LogoutService();

        logoutService.logout("incorrect authToken");

        assertFalse(authDAO.FindAll().isEmpty());
    }


}
