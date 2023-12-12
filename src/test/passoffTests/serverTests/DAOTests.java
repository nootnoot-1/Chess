package passoffTests.serverTests;
import chess.BoardImpl;
import chess.ChessGame;
import chess.GameImpl;
import chess.PositionImpl;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import models.AuthToken;
import models.Game;
import models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.fail;

public class DAOTests {
    private GameDAO gameDAO = new GameDAO();
    private UserDAO userDAO = new UserDAO();
    private AuthDAO authDAO = new AuthDAO();
    private Game game1 = new Game("game1");
    private Game game2 = new Game("game2");
    private Game game3 = new Game("game3");
    private User user1 = new User("user1","password1","email1");
    private User user2 = new User("user2","password2","email2");
    private User user3 = new User("user3","password3","email3");
    private AuthToken authToken1 = new AuthToken("authToken1","user1");
    private AuthToken authToken2 = new AuthToken("authToken2","user2");
    private AuthToken authToken3 = new AuthToken("authToken3","user3");

    @BeforeEach
    public void setup() {
        try {
            gameDAO.Clear();
            Assertions.assertEquals(0,gameDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
        try {
            userDAO.Clear();
            Assertions.assertEquals(0,userDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
        try {
            authDAO.Clear();
            Assertions.assertEquals(0,authDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
        Game.setGameIDIT(0);
        try {
            gameDAO.Insert(game1);
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
        try {
            userDAO.Insert(user1);
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
        try {
            authDAO.Insert(authToken1);
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void InsertGameP() {
        try {
            Assertions.assertEquals(game1, gameDAO.Find(game1.getGameID()));
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void InsertUserP() {
        try {
            Assertions.assertEquals(user1, userDAO.Find(user1.getUsername()));
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void InsertAuthTokenP() {
        try {
            Assertions.assertEquals(authToken1, authDAO.Find(authToken1.getAuthToken()));
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void InsertGameN() {
        try {
            gameDAO.Insert(game1);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("gamename is taken", e.getMessage());
        }
    }
    @Test
    public void InsertUserN() {
        try {
            userDAO.Insert(user1);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("username is taken", e.getMessage());
        }
    }
    @Test
    public void InsertAuthTokenN() {
        try {
            authDAO.Insert(authToken1);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("authToken is taken", e.getMessage());
        }
    }
    @Test
    public void FindGameP() {
        try {
            Assertions.assertEquals(game1, gameDAO.Find(game1.getGameID()));
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void FindUserP() {
        try {
            Assertions.assertEquals(user1, userDAO.Find(user1.getUsername()));
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void FindAuthTokenP() {
        try {
            Assertions.assertEquals(authToken1, authDAO.Find(authToken1.getAuthToken()));
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void FindGameN() {
        try {
            gameDAO.Find(0);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("no game with that gameID", e.getMessage());
        }
    }
    @Test
    public void FindUserN() {
        try {
            userDAO.Find("NonUser");
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("ERROR: no user registered with that username", e.getMessage());
        }
    }
    @Test
    public void FindAuthN() {
        try {
            authDAO.Find("NonAuth");
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("AUTHTOKEN FIND ERROR", e.getMessage());
        }
    }
    @Test
    public void clearGame() {
        try {
            gameDAO.Clear();
            Assertions.assertEquals(0,gameDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void clearUser() {
        try {
            userDAO.Clear();
            Assertions.assertEquals(0,userDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void clearAuth() {
        try {
            authDAO.Clear();
            Assertions.assertEquals(0,authDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void FindAllGame() {
        try {
            gameDAO.Insert(game2);
            gameDAO.Insert(game3);
            Collection<Game> games = gameDAO.FindAll();
            Assertions.assertEquals(3,games.size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void FindAllUser() {
        try {
            userDAO.Insert(user2);
            userDAO.Insert(user3);
            Collection<User> users = userDAO.FindAll();
            Assertions.assertEquals(3,users.size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void FindAllAuth() {
        try {
            authDAO.Insert(authToken2);
            authDAO.Insert(authToken3);
            Collection<AuthToken> auths = authDAO.FindAll();
            Assertions.assertEquals(3,auths.size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void RemoveGameP() {
        try {
            gameDAO.Remove(game1.getGameID());
            Assertions.assertEquals(0,gameDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void RemoveUserP() {
        try {
            userDAO.Remove(user1);
            Assertions.assertEquals(0,userDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void RemoveAuthP() {
        try {
            authDAO.Remove(authToken1.getAuthToken());
            Assertions.assertEquals(0,authDAO.FindAll().size());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void RemoveGameN() {
        try {
            gameDAO.Remove(0);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("no game with that gameID",e.getMessage());
        }
    }
    @Test
    public void RemoveUserN() {
        try {
            userDAO.Remove(user3);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("ERROR: no user registered with that username",e.getMessage());
        }
    }
    @Test
    public void RemoveAuthN() {
        try {
            authDAO.Remove(authToken3.getAuthToken());
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("AUTHTOKEN FIND ERROR",e.getMessage());
        }
    }
    @Test
    public void ClaimSpotP() {
        try {
            gameDAO.ClaimSpot(user1.getUsername(), game1.getGameID(), ChessGame.TeamColor.WHITE);
            Assertions.assertEquals(user1.getUsername(), gameDAO.Find(game1.getGameID()).getWhiteUsername());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void ClaimSpotN() {
        try {
            gameDAO.ClaimSpot(user1.getUsername(), game2.getGameID(), ChessGame.TeamColor.WHITE);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("no game with that gameID",e.getMessage());
        }
    }
    @Test
    public void UpdateGameP() {
        try {
            BoardImpl board = new BoardImpl();
            PositionImpl position = new PositionImpl(2,2);
            board.removePiece(position);
            GameImpl gameimpl = new GameImpl();
            gameimpl.setBoard(board);
            game2.setGame(gameimpl);
            gameDAO.UpdateGame(game1.getGameID(),game2);
            Assertions.assertEquals(game2.getGame().serialize(),gameDAO.Find(game1.getGameID()).getGame().serialize());
        } catch (DataAccessException e) {
            fail(e.getMessage());
        }
    }
    @Test
    public void UpdateGameN() {
        try {
            gameDAO.UpdateGame(game2.getGameID(),game2);
            fail("should have thrown an error");
        } catch (DataAccessException e) {
            Assertions.assertEquals("no game with that gameID",e.getMessage());
        }
    }
}
