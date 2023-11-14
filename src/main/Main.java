// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import chess.*;
import dataAccess.AuthDAO;
import dataAccess.DataAccessException;
import dataAccess.Database;
import models.AuthToken;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {
        AuthDAO authDAO = new AuthDAO();
        AuthToken authToken = new AuthToken();
        authToken.setUsername("Plentitius");

        try {
            authDAO.Insert(authToken);
            authDAO.FindAll();
            authDAO.Clear();
            authDAO.FindAll();
        } catch (DataAccessException e) {
            System.out.println("SQL ERROR CAUGHT IN MAIN");
        }
    }

}