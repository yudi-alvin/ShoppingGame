package menu;

import controller.*;
import java.text.*;
import java.util.*;
import java.lang.*;
/**
* <p>This is the login menu class that interact with the user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class LoginMenu{
    private LoginCtrl loginCtrl;

    /**
     * Create a default LoginMenu object
     */
    public LoginMenu() { 
        loginCtrl = new LoginCtrl();
    }

    /**
     * Display the interface when first login
     */
    public void display() { 
        String greeting = loginCtrl.getTime();
        System.out.println("== Cooking Story :: Welcome ==");
        System.out.println(greeting + ", anonymous!");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice > ");
    }
    /**
     * Read user's input
     */
    public void readOption() {
        Scanner sc = new Scanner(System.in);
        String choice;
        
        do {
            display();
            choice = sc.nextLine();
            if (choice.length() == 0) {
                choice = "4";
            }

            switch (choice) {
                case "1" :
                    register();
                    break;      

                case "2" :
                    login();
                    break;

                case "3" :
                    System.out.println("Bye bye!");
                    break;

                default :
                    System.out.println("Enter a choice between 1 to 3");
                    System.out.println();
                    break;
            }
            System.out.println();

        } while (! choice.equals("3"));
    }
    
    /**
     * Read user's input if login is chosen
     */
    public void login() {
        // exitApp && username
        Scanner sc = new Scanner(System.in);
        System.out.println("\n== The Cooking Story :: Login ==");

        String username;
        do {
            System.out.print("Enter your username > ");
            username = sc.nextLine();

            if (username.length() < 3) {
                System.err.println("Username must be at least 3 characters long.");
            }
        } while (username.length() < 3);

        System.out.print("Enter your password > ");
        String password = sc.nextLine();

        if (loginCtrl.loginMember(username, password)) {
            MainMenu menu = new MainMenu(username);
            menu.readOption();
        } else {
            System.out.println("Incorrect login details, returning to login menu.");
        }
    }

    /**
     * Register option, to prompt user for registration details
     */
    public void register() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n== The Cooking Story :: Registration ==");
        
        String username = "";

        do {
            System.out.print("Enter your username or (B)ack > ");
            username = sc.nextLine();

            if (!username.equals("B") && username.length() < 3) {
                System.out.println("Please enter a username that is at least 3 characters long.");
            }
        } while (!username.equals("B") && ! (username.length() >= 3) || !loginCtrl.checkUniqueUsername(username));

        if (username.equals("B")) {
            System.out.println("Back to login menu...\n");
        }
        else {
            System.out.print("Enter your full name > ");
            String fullName = sc.nextLine();
    
            String password;
            String checkPassword = " ";
            do {
                System.out.print("Enter your password > ");
                password = sc.nextLine();
                
                if (password.length() == 0) {
                    System.out.println("Password field cannot be empty.");
                } else {
                    System.out.print("Confirm your password > ");
                    checkPassword = sc.nextLine();

                    if (!password.equals(checkPassword)) {
                        System.out.println("Password mismatch, please try again.\n");
                    }
                }
             } while (!password.equals(checkPassword));
    
            loginCtrl.registerMember(username, fullName, password);
            System.out.println("Member " + username + " successfully registered.");
            System.out.println();
        }
    }
}