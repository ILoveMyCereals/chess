package ui;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import Results.LoginResult;
import Results.RegisterResult;
import net.ServerFacade;

import java.util.Scanner;

public class Prelogin {

    private String option = "0";
    private ServerFacade serverFacade = new ServerFacade(8080);
    private String authToken;

    public void main(String[] args) {
        System.out.println("Welcome to Nathan \"the Trendsetter\" Smith's Wonderful World of Chess!");
        while (option.equals("0")) {
            System.out.print("""
                    Please select an option and input the corresponding number to continue:
                                    
                    1. Login
                    2. Register
                    3. Help
                    4. Quit
                    """);

            Scanner newScan = new Scanner(System.in);
            option = newScan.nextLine();

            if (option.equals("1")) {
                System.out.print("Please enter your username");
                String username = newScan.nextLine();

                System.out.print("Please enter your password");
                String password = newScan.nextLine();

                LoginRequest req = new LoginRequest(username, password);

                try {
                    LoginResult res = serverFacade.sendLoginRequest(req);
                    String authToken = res.authToken();
                    System.out.println("You are logged in as " + res.username());
                    option = "0"; // RUN THE POSTLOGIN UI
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                }

            } else if (option.equals("2")) {
                System.out.print("""
                        Thank you for choosing to register for a chess account!
                        Please choose a unique username.
                        """);
                String username = newScan.nextLine();

                System.out.print("Please choose a password");
                String password = newScan.nextLine();

                System.out.print("Please input your email");
                String email = newScan.nextLine();

                RegisterRequest req = new RegisterRequest(username, password, email);

                try {
                    RegisterResult res = serverFacade.sendRegisterRequest(req);
                    authToken = res.authToken();
                    System.out.println("You have registered with the username" + res.username());
                    option = "0"; //RUN THE POSTLOGIN UI

                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                }

            } else if (option.equals("3")) {
                System.out.println("""
                        1. Login -- If you're a registered user, input your username and password to access your account
                        2. Register -- If you're not registered, input a username, password and email to create an account
                        3. Help -- Get additional information about each of the options
                        4. Quit -- Leave the program
                        """);
            } else if (option.equals("4")) {
                System.exit(0);
            } else {
                System.out.println("Invalid option");
                option = "0";
            }

        }
    }
}
