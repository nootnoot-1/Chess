package handlers;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import services.ClearApplicationService;
import response.ClearApplicationResponse;
import spark.Request;
import spark.Response;

public class ClearApplicationHandler {

    public String handleRequest(Request request, Response response) throws DataAccessException {
        ClearApplicationService clearApplicationService = new ClearApplicationService();
        Gson gson = new Gson();

        ClearApplicationResponse clearApplicationResponse = clearApplicationService.clearApplication();

        response.status(200);
        return gson.toJson(clearApplicationResponse);
    }

}
