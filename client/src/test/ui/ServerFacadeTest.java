package ui;

import models.Game;
import models.User;
import org.junit.jupiter.api.*;
import request.*;
import response.*;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ServerFacadeTest {
    private static final int HTTP_OK = 200;
    private static User existingUser;
    private static User newUser;
    private static ServerFacade server;
    private static String authToken;
    private static Game game;

    @BeforeAll
    static void setUpBA() {
        existingUser = new User();
        existingUser.setUsername("Joseph");
        existingUser.setPassword("Smith");
        existingUser.setEmail("urim@thummim.net");

        newUser = new User();
        newUser.setUsername("testUsername");
        newUser.setPassword("testPassword");
        newUser.setEmail("testEmail");

        game = new Game("BALLIN");

        server = new ServerFacade("http://localhost:8080");
    }

    @Test
    @Order(1)
    void registerP() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername(existingUser.getUsername());
        request.setPassword(existingUser.getPassword());
        request.setEmail(existingUser.getEmail());

        try {
            RegisterResponse response = server.register(request);
            authToken = response.getAuthToken();
            assertNull(response.getMessage());
        } catch (ServerFacade.ResponseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(2)
    void registerN() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername(existingUser.getUsername());
        request.setPassword(existingUser.getPassword());
        //request.setEmail(existingUser.getEmail());

        try {
            server.register(request).getAuthToken();
            fail("should have thrown an error");
        } catch (ServerFacade.ResponseException e) {
            Assertions.assertEquals("failure: 400", e.getMessage());
        }
    }

    @Test
    @Order(3)
    void logoutN() {
        try {
            server.logout("incorrect");
            fail("should have thrown an error");
        } catch (ServerFacade.ResponseException e) {
            Assertions.assertEquals("failure: 401", e.getMessage());
        }
    }

    @Test
    @Order(4)
    void logoutP() {
        try {
            server.logout(authToken);
            authToken = null;
        } catch (ServerFacade.ResponseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(5)
    void loginP() {
        LoginRequest request = new LoginRequest();
        request.setUsername(existingUser.getUsername());
        request.setPassword(existingUser.getPassword());
        try {
            LoginResponse response = server.login(request);
            authToken = response.getAuthToken();
            assertNull(response.getMessage());
        } catch (ServerFacade.ResponseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(6)
    void loginN() {
        LoginRequest request = new LoginRequest();
        request.setUsername(newUser.getUsername());
        request.setPassword(newUser.getPassword());
        try {
            authToken = server.login(request).getAuthToken();
            fail("should have thrown an error");
        } catch (ServerFacade.ResponseException e) {
            Assertions.assertEquals("failure: 401",e.getMessage());
        }
    }

    @Test
    @Order(7)
    void createGameP() {
        CreateGameRequest request = new CreateGameRequest();
        request.setGameName(game.getGameName());
        request.setAuthToken(authToken);
        try {
            CreateGameResponse response = server.createGame(request);
            assertNull(response.getMessage());
        } catch (ServerFacade.ResponseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(8)
    void createGameN() {
        CreateGameRequest request = new CreateGameRequest();
        request.setGameName("incorrect");
        request.setAuthToken("incorrect");
        try {
            server.createGame(request);
            fail("should have thrown an error");
        } catch (ServerFacade.ResponseException e) {
            Assertions.assertEquals("failure: 401",e.getMessage());
        }
    }

    @Test
    @Order(9)
    void listGamesP() {
        try {
            ListGamesRequest request = new ListGamesRequest();
            request.setAuthToken(authToken);
            ListGamesResponse response = server.listGames(request);
            Collection<Game> games = response.getGames();
            if (games != null) {
                Assertions.assertEquals(1,games.size());
            } else {
                fail("should be one game in games, not none");
            }
        } catch (ServerFacade.ResponseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(10)
    void listGamesN() {
        try {
            ListGamesRequest request = new ListGamesRequest();
            request.setAuthToken("incorrect");
            ListGamesResponse response = server.listGames(request);
            fail("should have thrown an error");
        } catch (ServerFacade.ResponseException e) {
            Assertions.assertEquals("failure: 401",e.getMessage());
        }
    }

    @Test
    @Order(11)
    void joinGameP() {
        JoinGameRequest request = new JoinGameRequest();
        request.setGameID(game.getGameID());
        request.setPlayerColor("WHITE");
        try {
            JoinGameResponse response = server.joinGame(request, authToken);
            assertNull(response.getMessage());
        } catch (ServerFacade.ResponseException e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Order(12)
    void joinGameN() {
        JoinGameRequest request = new JoinGameRequest();
        request.setGameID(0);
        request.setPlayerColor("WHITE");
        try {
            JoinGameResponse response = server.joinGame(request, authToken);
            fail("should have thrown an error");
        } catch (ServerFacade.ResponseException e) {
            Assertions.assertEquals("failure: 400",e.getMessage());
        }
    }
}