import dataAccess.AuthDAO;
import dataAccess.GameDAO;
import dataAccess.UserDAO;
import handlers.ClearApplicationHandler;
import handlers.LoginHandler;
import handlers.LogoutHandler;
import handlers.RegisterHandler;
import spark.Request;
import spark.Response;
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

    }
}

//where do I declare my DAO classes? how does post interact with the database/is it all I need? MAKE STATIC
//working the website? Register service message syntax? how to get correct authToken?
//what is a description error?
//response syntax (see return and set status for RegisterHandler)
//can two users have the same email?
//is the authToken just a way of telling if a user is logged in or not?
