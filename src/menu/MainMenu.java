package menu;

import controller.*;
import entity.*;
import dao.*;
import utility.*;
import java.util.*;

/**
* <p>This is the main menu class that interact with the user</p>
* @author  BB - BB Coder, BB No Sleep
* @version 1.0
* @since   2018-04
*/

public class MainMenu {
    private Member member;
    
    /**
     * Create a MainMenu object with specific username
     * @param username the username
     */
    public MainMenu(String username) {
        MemberDAO memberDAO = new MemberDAO();
        RecipeDAO recipeDAO = new RecipeDAO();
        StoveDAO stoveDAO = new StoveDAO();
        member = memberDAO.getMember(username);

        MemberUtility.setMember(member);
        MemberUtility.setInventory(memberDAO.getMemberInventory(username));
        MemberUtility.setMemberRecipe(recipeDAO.retrieveMemberRecipe(username));
        MemberUtility.setStoves(stoveDAO.getStoves(username));
    }

    /**
     * Display the options after the user login successfully
     */
    public void display() {
        System.out.println("\n== Cooking Story :: Main Menu ==");
        System.out.println("Welcome, " + member.getTitle() + " "+ member.getFullName() + "! \n");
        System.out.println("1. Go Shopping");
        System.out.println("2. My Restaurant");
        System.out.println("3. My Training Center ");
        System.out.println("4. Visit Friend ");
        System.out.println("5. Logout ");
        System.out.print("Enter your choice > ");
    }

    /**
     * Read the user's input 
     */
    public void readOption() {
        Scanner sc = new Scanner(System.in);
        String choice;
        
        do {
            display();
            choice = sc.nextLine();
            if (choice.length() == 0) {
                choice = "6";
            }
            switch (choice) {
                case "1" :
                    goShopping();
                    break;
                case "2" :
                    myRestaurant();
                    break;
                case "3" :
                    trainingCenter();
                    break;
                case "4" :
                    visitFriend();
                    break;
                case "5" :
                    System.out.println();
                    System.out.println("Log out successful.");
                    break;
                default :
                    System.out.println("Enter a choice between 1 to 5.");
                    break;
                }
        } while (! choice.equals("5"));
    }

    /**
     * Go to the shopping menu
     */
    public void goShopping() {
        ShoppingMenu shopMenu = new ShoppingMenu(); 
        shopMenu.readOption();
    }

    /**
     * Go to the my restaurant menu
     */
    public void myRestaurant() {
        RestaurantMenu restMenu = new RestaurantMenu();
        restMenu.readOption();
    }

    /**
     * Go to the training centre menu
     */
    public void trainingCenter() {
        TrainingMenu trainingMenu = new TrainingMenu();
        trainingMenu.readOption();
    }
    /**
     * Go to the visitFriend menu
     */
    public void visitFriend() {
        VisitMenu visitMenu = new VisitMenu();
        visitMenu.readOption();

    }
}