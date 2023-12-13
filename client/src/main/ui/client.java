package ui;

import request.LoginRequest;
import request.RegisterRequest;

import java.util.*;

public class client {
    public static void main(String[] args) {
        System.out.println("WELCOME TO CHESS");
        ServerFacade server = new ServerFacade("http://localhost:8080");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[LOGGED_OUT] >>> ");
            String temp = scanner.nextLine();
            String[] input = parseInput(temp);
            if (Objects.equals(input[0], "quit") && input.length == 1) {
                break;
            } else if (Objects.equals(input[0], "help") && input.length == 1) {
                System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
                System.out.println("login <USERNAME> <PASSWORD> - to play chess");
                System.out.println("quit - playing chess");
                System.out.println("help - with possible commands");
            } else if (Objects.equals(input[0], "register") && input.length == 4) {
                RegisterRequest request = new RegisterRequest();
                request.setUsername(input[1]);
                request.setPassword(input[2]);
                request.setEmail(input[3]);
                try {
                    server.register(request);
                    System.out.println("user registered and logged in");
                } catch (ServerFacade.ResponseException e) {
                    System.out.printf(e.getMessage());
                }
            } else if (Objects.equals(input[0], "login") && input.length == 3) {
                LoginRequest request = new LoginRequest();
                request.setUsername(input[1]);
                request.setPassword(input[2]);
                try {
                    server.login(request);
                } catch (ServerFacade.ResponseException e) {
                    System.out.printf(e.getMessage());
                }
                System.out.println("user logged in");
            }
        }
    }
    private void loggedoutClient() {

    }
    private static String[] parseInput(String input) {
        String[] words = input.split("\\s+");
        return words;
    }
}
