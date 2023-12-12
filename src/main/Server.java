import dataAccess.DataAccessException;
import dataAccess.UserDAO;
import handlers.*;
import models.Game;
import models.User;
import spark.Spark;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
Server class, takes HTTP input and relays it to the correct handler
 */
public class Server {

    public static void main(String[] args) {
        try {
            run();
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void run() throws DataAccessException {
        Spark.port(8080);

        Spark.externalStaticFileLocation("web");

        Spark.delete("/db", (request, response) -> (new ClearApplicationHandler()).handleRequest(request, response));
        Spark.post("/user", (request, response) -> (new RegisterHandler()).handleRequest(request, response));
        Spark.post("/session", (request, response) -> (new LoginHandler()).handleRequest(request,response));
        Spark.delete("/session", (request, response) -> (new LogoutHandler()).handleRequest(request,response));
        Spark.post("/game", (request, response) -> (new CreateGameHandler()).handleRequest(request, response));
        Spark.put("/game", (request, response) -> (new JoinGameHandler()).handleRequest(request, response));
        Spark.get("/game", (request, response) -> (new ListGamesHandler()).handleRequest(request, response));

        //Initialize SQL
        try (var conn = getConnection()) {
            //GET RID OF THIS CLEAR LATER
            var clear = conn.prepareStatement("DROP DATABASE chess");
            clear.executeUpdate();
            Game.setGameIDIT(0);

            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
            createDbStatement.executeUpdate();

            conn.setCatalog("chess");

            var createAUTHTable = """
                    CREATE TABLE IF NOT EXISTS AUTH (
                    id INT NOT NULL AUTO_INCREMENT,
                    authToken VARCHAR(255) NOT NULL,
                    username VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                    )""";

            try (var createTableStatement = conn.prepareStatement(createAUTHTable)) {
                createTableStatement.executeUpdate();
            }

            var createUSERTable = """
                    CREATE TABLE IF NOT EXISTS USER (
                    id INT NOT NULL AUTO_INCREMENT,
                    username VARCHAR(255) NOT NULL,
                    password VARCHAR(255) NOT NULL,
                    email VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                    )""";

            try (var createTableStatement = conn.prepareStatement(createUSERTable)) {
                createTableStatement.executeUpdate();
            }

            var createGAMETable = """
                    CREATE TABLE IF NOT EXISTS GAME (
                    ID INT NOT NULL AUTO_INCREMENT,
                    gameID INT NOT NULL,
                    gamename VARCHAR(255) NOT NULL,
                    whiteusername VARCHAR(255),
                    blackusername VARCHAR(255),
                    gamestring VARCHAR(255) NOT NULL,
                    PRIMARY KEY (ID)
                    )""";

            try (var createTableStatement = conn.prepareStatement(createGAMETable)) {
                createTableStatement.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }

//        UserDAO userDAO = new UserDAO();
//        User user = new User();
//        user.setUsername("Collin");
//        user.setPassword("12534");
//        user.setEmail("collinden19@gmail.com");
//        userDAO.Insert(user);
//        user.setUsername("Carson");
//        user.setPassword("43521");
//        user.setEmail("carson@gmail.com");
//        userDAO.Insert(user);
//        user.setUsername("Jason");
//        user.setPassword("woohoo");
//        user.setEmail("jason@gmail.com");
//        userDAO.Insert(user);
//        userDAO.Remove(user);
//        int num = 2;

    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "imightbelate1");
    }

}

