import handlers.*;
import spark.Spark;

/**
Server class, takes HTTP input and relays it to the correct handler
 */
public class Server {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        Spark.port(8080);

        Spark.externalStaticFileLocation("web");

        Spark.delete("/db", (request, response) -> (new ClearApplicationHandler()).handleRequest(request, response));
        Spark.post("/user", (request, response) -> (new RegisterHandler()).handleRequest(request, response));
        Spark.post("/session", (request, response) -> (new LoginHandler()).handleRequest(request,response));
        Spark.delete("/session", (request, response) -> (new LogoutHandler()).handleRequest(request,response));
        Spark.post("/game", (request, response) -> (new CreateGameHandler()).handleRequest(request, response));
        Spark.put("/game", (request, response) -> (new JoinGameHandler()).handleRequest(request, response));
        Spark.get("/game", (request, response) -> (new ListGamesHandler()).handleRequest(request, response));

    }
}

