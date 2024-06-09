package ui;

import Requests.LoginRequest;
import Requests.RegisterRequest;
import net.ServerFacade;

import java.util.Scanner;

public class Prelogin {

    private String option = "0";
    private ServerFacade serverFacade = new ServerFacade();

    public void main(String[] args) {
        while (option.equals("0")) {
            System.out.print("""
                    Welcome to Nathan "the Trendsetter" Smith's Wonderful World of Chess!
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
                    serverFacade.sendLoginRequest(req);
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
                    serverFacade.sendRegisterRequest(req);
                } catch (Exception ex) {
                    System.out.print(ex.getMessage());
                }

            } else if (option.equals("3")) {
                return;
            } else if (option.equals("4")) {
                System.exit(0);
            } else {
                System.out.println("Invalid option");
                option = "0";
            }

        }
    }
}
