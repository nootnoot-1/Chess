// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
import chess.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class Main {
    public static void main(String[] args) {


        try {
            makeSQLCalls();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "imightbelate1");
    }

//    static void makeSQLCalls() throws SQLException {
//        try (var conn = getConnection()) {
//            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS chess");
//            createDbStatement.executeUpdate();
//
//            conn.setCatalog("chess");
//
//            var createAUTHTable = """
//                    CREATE TABLE IF NOT EXISTS AUTH (
//                    id INT NOT NULL AUTO_INCREMENT,
//                    authToken VARCHAR(255) NOT NULL,
//                    username VARCHAR(255) NOT NULL,
//                    PRIMARY KEY (id)
//                    )""";
//
//            try (var createTableStatement = conn.prepareStatement(createAUTHTable)) {
//                createTableStatement.executeUpdate();
//            }
//
//            var createUSERTable = """
//                    CREATE TABLE IF NOT EXISTS USER (
//                    id INT NOT NULL AUTO_INCREMENT,
//                    username VARCHAR(255) NOT NULL,
//                    password VARCHAR(255) NOT NULL,
//                    email VARCHAR(255) NOT NULL,
//                    PRIMARY KEY (id)
//                    )""";
//
//            try (var createTableStatement = conn.prepareStatement(createUSERTable)) {
//                createTableStatement.executeUpdate();
//            }
//
//            var createGAMETable = """
//                    CREATE TABLE IF NOT EXISTS GAME (
//                    gameID INT NOT NULL AUTO_INCREMENT,
//                    gamename VARCHAR(255) NOT NULL,
//                    whiteusername VARCHAR(255) NOT NULL,
//                    blackusername VARCHAR(255) NOT NULL,
//                    game VARCHAR(255) NOT NULL,
//                    PRIMARY KEY (gameID)
//                    )""";
//
//            try (var createTableStatement = conn.prepareStatement(createGAMETable)) {
//                createTableStatement.executeUpdate();
//            }
//
//        }
//    }
    static void makeSQLCalls() throws SQLException {
        try (var conn = getConnection()) {
            var createDbStatement = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS experiment");
            createDbStatement.executeUpdate();

            conn.setCatalog("experiment");

            var createPetTable = """
                    CREATE TABLE IF NOT EXISTS pet (
                    id INT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    type VARCHAR(255) NOT NULL,
                    PRIMARY KEY (id)
                    )""";

            try (var createTableStatement = conn.prepareStatement(createPetTable)) {
                createTableStatement.executeUpdate();
            }

            System.out.println(insertPet(conn, "Hershey", "Dog"));
        }
    }

    static int insertPet(Connection conn, String name, String type) throws SQLException {
        try (var preparedStatement = conn.prepareStatement("INSERT INTO pet (name, type) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,type);

            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            var ID = 0;
            if (resultSet.next()) {
                ID = resultSet.getInt(1);
            }

            return ID;
        }
    }

    static void insertPetSAFE(String name) throws SQLException {
        var conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pet_store", "root", "imightbelate1");

        if (name.matches("[a-zA-Z]+")) {
            var statement = "INSERT INTO pet (name) VALUES(?)";
            try (var preparedStatement = conn.prepareStatement(statement)) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();
            }
        }
    }

}